package toys.web.servlet.listeners;

import java.util.Calendar;
import java.util.Date;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import toys.utils.JNDIToys;
import toys.utils.LocaleToys;

/**
 * Listener para inicialização de tarefas do Quarts.
 * @author Iran
 */
public class QuartzStartListener implements ServletContextListener {

    /**
     * Caminho base para os agendamentos no JNDI.
     */
    private static final String JNDI_QUARTZ_PATH = "java:comp/env/quartz";

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

    protected final Logger logger = LogManager.getFormatterLogger();
    protected Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Inicializando agendador de tarefas.");

        // Verifica se existem tarefas configuradas
        NamingEnumeration<NameClassPair> names = null;
        try {
            names = JNDIToys.getInitialContext().list(JNDI_QUARTZ_PATH);
        } catch (NameNotFoundException e) {
            logger.info("Nenhuma tarefa agendada foi definida.");
        } catch (NamingException e) {
            logger.fatal("Erro consultando informacoes no JNDI. path=%s", JNDI_QUARTZ_PATH, e);
        }
        if (names == null || !names.hasMoreElements())
            return;

        // Instancia e configura os jobas com as informações do JNDI
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            while (names.hasMoreElements())
                agendarTarefa(scheduler, names.nextElement().getName(), sce.getServletContext());
            scheduler.start();
            logger.info("Agendador de tarefas iniciado com sucesso.");
        } catch (SchedulerException e) {
            logger.fatal("Erro geral agendando tarefas.", e);
        }

    }

    /**
     * Agenda uma tarefa no agendador.
     * @param scheduler Referência para o agendador.
     * @param jobName Nome da tarefa de onde serão lidas as configurações.
     * @param sc Referência para o contexto da servlet que pode ser utilizado na obtenção de algumas informações.
     * @throws NamingException
     */
    @SuppressWarnings("unchecked")
    private void agendarTarefa(Scheduler scheduler, String jobName, ServletContext sc) {
        logger.info("Agendando tarefa %s.", jobName);
        try {

            // Obtém as configurações do job
            String path = JNDI_QUARTZ_PATH + "/" + jobName;
            Context context = (Context)JNDIToys.getInitialContext().lookup(path);
            NamingEnumeration<NameClassPair> cfgs = JNDIToys.getInitialContext().list(path);
            String jobClass = null;
            String schedule = null;
            Integer delay = null;
            JobDataMap jobData = new JobDataMap();
            while (cfgs.hasMoreElements()) {
                String cfg = cfgs.nextElement().getName();
                if (cfg.equals(CFG_JOB_CLASS)) {
                    jobClass = (String)context.lookup(CFG_JOB_CLASS);
                    logger.info("\tclass=%s", jobClass);
                } else if (cfg.equals(CFG_DELAY)) {
                    delay = (Integer)context.lookup(CFG_DELAY);
                    logger.info("\tatraso na execucao=%ds", delay);
                } else if (cfg.equals(CFG_SCHEDULE)) {
                    schedule = (String)context.lookup(CFG_SCHEDULE);
                    if (StringUtils.isNotEmpty(schedule))
                        logger.info("\tagenda=%s", schedule);
                    else
                        schedule = null;
                } else {
                    Object value = processarSubstituicoes(context.lookup(cfg), sc);
                    jobData.put(cfg, value);
                    logger.info("\t%s=%s", cfg, value);
                }
            }

            // Configura as informações do job
            if (StringUtils.isBlank(jobClass)) {
                logger.error("Nome da classe nao foi especificado para o job %s.", jobName);
                return;
            }
            Class<? extends Job> jobClazz = (Class<Job>)Class.forName(jobClass);
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
                logger.warn("Tarefa %s nao foi agendado pois nao possui uma agenda definida.", jobName);

        } catch (NamingException e) {
            logger.fatal("Job %s/%s nao encontrado no JNDI.", JNDI_QUARTZ_PATH, jobName, e);
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
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("el-syntax")
    private Object processarSubstituicoes(Object value, ServletContext sc) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

        // Processa somente valores string
        if (!(value instanceof String))
            return value;

        StringBuilder sb = new StringBuilder((String)value);
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
