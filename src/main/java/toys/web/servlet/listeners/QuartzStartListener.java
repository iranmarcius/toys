package toys.web.servlet.listeners;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import toys.ToysConfig;
import toys.utils.LocaleToys;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.*;

/**
 * Listener para inicialização de tarefas do Quarts.
 *
 * @author Iran
 */
public class QuartzStartListener implements ServletContextListener {

    /**
     * Nome da classe da tarefa.
     */
    private static final String CFG_JOB_CLASS = "jobClass";

    /**
     * Agendamento da tarefa no formato cron do Quartz.
     */
    private static final String CFG_SCHEDULE = "schedule";

    /**
     * Tempo de átraso na execução da tarefa em segundos.
     */
    private static final String CFG_DELAY = "delay";

    protected final Logger logger = LogManager.getFormatterLogger(getClass());
    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Inicializando agendador de tarefas.");

        // Verifica se existem tarefas configuradas
        Properties quartzProps = ToysConfig.getInstance().getProperties("toys.quartz", false);
        if (quartzProps.isEmpty()) {
            logger.info("Nenhum agendamento de tarefa encontrado.");
            return;
        }

        // Cria um mapa agrupando os jobs e supas propriedades específicas
        logger.debug("Processando configuracoes de tarefas.");
        Map<String, Properties> jobsProps = new HashMap<>();
        for (Map.Entry<Object, Object> entry: quartzProps.entrySet()) {
            String propName = (String) entry.getKey();
            int i = propName.indexOf('.');
            String jobName = propName.substring(0, i);
            String cfgName = propName.substring(i + 1);
            jobsProps.computeIfAbsent(jobName, k -> new Properties()).put(cfgName, entry.getValue());
        }

        // Registra as informações do log
        logger.debug("Configuracoes das tarefas:");
        for (Map.Entry<String, Properties> jobEntry: jobsProps.entrySet()) {
            logger.debug("Job %s", jobEntry.getKey());
            for (Map.Entry<Object, Object> entry: jobEntry.getValue().entrySet())
                logger.debug("\t%s=%s", entry.getKey(), entry.getValue());
        }

        // Instancia e configura os jobs
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            for (Map.Entry<String, Properties> entry: jobsProps.entrySet())
                agendarTarefa(scheduler, entry.getKey(), entry.getValue(), sce.getServletContext());
            scheduler.start();
            logger.info("Agendador de tarefas iniciado com sucesso.");
        } catch (SchedulerException e) {
            logger.fatal("Erro geral agendando tarefas.", e);
        }

    }

    /**
     * Agenda uma tarefa no agendador.
     *
     * @param scheduler Referência para o agendador.
     * @param jobName   Nome da tarefa de onde serão lidas as configurações.
     * @param props     Configurações do job.
     * @param sc        Referência para o contexto da servlet que pode ser utilizado na obtenção de algumas informações.
     */
    @SuppressWarnings("unchecked")
    private void agendarTarefa(Scheduler scheduler, String jobName, Properties props, ServletContext sc) {
        logger.info("Agendando tarefa %s.", jobName);
        try {

            // Obtém as configurações do job
            String jobClass = null;
            String schedule = null;
            Integer delay = null;
            JobDataMap jobData = new JobDataMap();
            for (Map.Entry<Object, Object> entry: props.entrySet()) {
                String cfg = entry.getKey().toString();
                String valor = (String)entry.getValue();
                if (cfg.equals(CFG_JOB_CLASS)) {
                    jobClass = valor;
                    logger.info("\tclass=%s", jobClass);
                } else if (cfg.equals(CFG_DELAY)) {
                    if (StringUtils.isNotEmpty(valor)) {
                        delay = StringUtils.isNotBlank(valor) ? Integer.valueOf(valor) : 0;
                        if (delay > 0)
                            logger.info("\tatraso na execucao=%ds", delay);
                    }
                } else if (cfg.equals(CFG_SCHEDULE)) {
                    if (StringUtils.isNotBlank(valor)) {
                        schedule = valor;
                        logger.info("\tagenda=%s", schedule);
                    }
                } else {
                    if (StringUtils.isNotBlank(valor)) {
                        Object value = processarSubstituicoes(valor, sc);
                        jobData.put(cfg, value);
                        logger.info("\t%s=%s", cfg, value);
                    }
                }
            }

            // Configura as informações do job
            if (StringUtils.isBlank(jobClass)) {
                logger.error("Nome da classe nao foi especificado para o job %s.", jobName);
                return;
            }
            Class<? extends Job> jobClazz = (Class<Job>) Class.forName(jobClass);
            JobBuilder jb = JobBuilder.newJob().ofType(jobClazz);
            if (!jobData.isEmpty())
                jb.usingJobData(jobData);
            JobDetail jobDetail = jb.build();

            // Configura a trigger
            if (schedule == null && delay != null) {
                schedule = execucaoUnica(delay);
                delay = null;
                logger.info("\tagenda de execucao unica=%s", schedule);
            }
            Trigger trigger = null;
            if (schedule != null) {
                TriggerBuilder<CronTrigger> tb = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(schedule));
                if (delay != null)
                    tb.startAt(new Date(System.currentTimeMillis() + delay));
                trigger = tb.build();
            }

            // O agendamento será feito apenas se a trigger tiver um agendamento
            if (trigger != null)
                scheduler.scheduleJob(jobDetail, trigger);
            else
                logger.warn("Tarefa %s nao iniciada pois nao possui agenda definida.", jobName);

        } catch (ClassNotFoundException e) {
            logger.fatal("Erro instanciando a tarefa %s.", jobName, e);
        } catch (SchedulerException e) {
            logger.fatal("Ocorreu um erro no agendamento da tarefa %s.", jobName, e);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            logger.fatal("Ocorreu um erro no processamento dos parametros", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            try {
                scheduler.shutdown();
                logger.info("Quartz foi encerrado.");
            } catch (SchedulerException e) {
                logger.fatal("Erro finalizando Quartz.", e);
            }
        }
    }

    /**
     * Verifica se o valor é do tipo String e, caso seja, processa as substituições existentes, caso haja alguma.
     */
    private Object processarSubstituicoes(Object value, ServletContext sc) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {

        // Processa somente valores string
        if (!(value instanceof String))
            return value;

        StringBuilder sb = new StringBuilder((String) value);
        int i = -1;
        int j = -1;
        while ((i = sb.indexOf("${")) > -1 && (j = sb.indexOf("}")) > -1) {
            String expr = sb.substring(i, j + 1);
            String replace = null;
            if (expr.equals("${appRoot}")) {
                replace = sc.getRealPath(".");
            } else if (expr.startsWith("${const:")) {
                String constPath = expr.substring(8, expr.length() - 1);
                int k = constPath.lastIndexOf('.');
                String className = constPath.substring(0, k);
                String fieldName = constPath.substring(k + 1);
                Class<?> clazz = Class.forName(className);
                replace = clazz.getField(fieldName).get(clazz).toString();
            }

            if (replace != null)
                sb.replace(i, j + 1, replace);

        }

        return sb.toString();
    }

    /**
     * Cria um agendamento com execução única para disparar após decorridos os milissegundos especificados no offset.
     */
    private String execucaoUnica(Integer offset) {
        Calendar c = Calendar.getInstance(LocaleToys.BRAZIL);
        c.add(Calendar.SECOND, offset);
        return String.format("%d %d %d * * ?", c.get(Calendar.SECOND), c.get(Calendar.MINUTE), c.get(Calendar.HOUR_OF_DAY));
    }

}
