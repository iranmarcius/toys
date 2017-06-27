package toys.web.servlet.listeners;

import java.util.Calendar;
import java.util.Date;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
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

import toys.utils.JNDIUtils;
import toys.utils.LocaleToys;

/**
 * Listener para inicialização de tarefas do Quarts.
 * @author Iran
 */
public class QuartzStartListener implements ServletContextListener {

    /**
     * caminho base para os agendamentos
     */
    private static final String JNDI_QUARTZ_PATH = "java:comp/env/quartz";

    /**
     * Agendamento da tarefa no formato cron do Quartz.
     */
    private static final String CFG_AGENDA = "agenda";

    /**
     * Tempo de átraso na execução da tarefa em segundos.
     */
    private static final String CFG_ATRASO = "atraso";

    protected final Logger logger = LogManager.getFormatterLogger();
    protected Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Inicializando agendador de tarefas.");

        // Verifica se existem tarefas configuradas
        NamingEnumeration<NameClassPair> names = null;
        try {
            names = JNDIUtils.getInitialContext().list(JNDI_QUARTZ_PATH);
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
                agendarTarefa(scheduler, names.nextElement().getName());
            scheduler.start();
            logger.info("Agendador de taredas iniciado com sucesso.");
        } catch (SchedulerException e) {
            logger.fatal("Erro geral agendando tarefas.", e);
        }

    }

    /**
     * Agenda uma tarefa no agendador.
     * @param scheduler Referência para o agendador.
     * @param jobClassName Nome da tarefa que será utilizado para obter as configurações.
     * @throws NamingException
     */
    @SuppressWarnings("unchecked")
    private void agendarTarefa(Scheduler scheduler, String jobClassName) {
        logger.info("Agendando tarefa %s.", jobClassName);
        try {

            // Obtém as configurações do job
            String path = JNDI_QUARTZ_PATH + "/" + jobClassName;
            Context context = (Context)JNDIUtils.getInitialContext().lookup(path);
            NamingEnumeration<NameClassPair> cfgs = JNDIUtils.getInitialContext().list(path);
            String agenda = null;
            Long atraso = null;
            JobDataMap jobData = new JobDataMap();
            while (cfgs.hasMoreElements()) {
                String cfg = cfgs.nextElement().getName();
                if (cfg.equals(CFG_ATRASO)) {
                    atraso = (Long)context.lookup(CFG_ATRASO);
                    logger.info("\tatraso=%ds", atraso);
                } else if (cfg.equals(CFG_AGENDA)) {
                    agenda = (String)context.lookup(CFG_AGENDA);
                    if (StringUtils.isNotEmpty(agenda))
                        logger.info("\tagenda=%s", agenda);
                    else
                        agenda = null;
                } else {
                    Object value = context.lookup(cfg);
                    jobData.put(cfg, value);
                    logger.info("\t%s=%s", cfg, value);
                }
            }

            // Configura as informações do job
            Class<? extends Job> jobClazz = (Class<Job>)Class.forName(jobClassName);
            JobBuilder jb = JobBuilder.newJob().ofType(jobClazz);
            if (!jobData.isEmpty())
                jb.usingJobData(jobData);
            JobDetail jobDetail = jb.build();

            // Configura a trigger
            if (agenda == null && atraso != null) {
                agenda = execucaoUnica(atraso);
                atraso = null;
                logger.info("\tagendamento de execucao unica=%s", agenda);
            }
            Trigger trigger = null;
            if (agenda != null) {
                TriggerBuilder<CronTrigger> tb = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(agenda));
                if (atraso != null)
                    tb.startAt(new Date(System.currentTimeMillis() + atraso));
                trigger = tb.build();
            }

            // O agendamento será feito apenas se a trigger tiver um agendamento
            if (trigger != null)
                scheduler.scheduleJob(jobDetail, trigger);
            else
                logger.warn("Tarefa %s nao foi agendado pois nao possui uma agenda definida.", jobClassName);

        } catch (NamingException e) {
            logger.fatal("Job %s/%s nao encontrado no JNDI.", JNDI_QUARTZ_PATH, jobClassName, e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Erro instanciando a tarefa %s.", jobClassName, e);
        } catch (SchedulerException e) {
            logger.fatal("Ocorreu um erro no agendamento da tarefa %s.", jobClassName, e);
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
     * Cria um agendamento com execução única para disparar após decorridos os milissegundos especificados no offset.
     */
    private String execucaoUnica(Long offset) {
        Calendar c = Calendar.getInstance(LocaleToys.BRAZIL);
        c.add(Calendar.SECOND, offset.intValue());
        return String.format("%d %d %d * * ?", c.get(Calendar.SECOND), c.get(Calendar.MINUTE), c.get(Calendar.HOUR_OF_DAY));
    }

}
