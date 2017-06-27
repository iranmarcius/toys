package toys.web.servlet.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Implementação base para listeners de inicialização de tarefas.
 * @author Iran
 */
public abstract class JobStartListener implements ServletContextListener {
    protected final Logger logger = LogManager.getFormatterLogger();
    protected Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduleJobs(sce);
            scheduler.start();
        } catch (SchedulerException e) {
            getErrorLogger().fatal("Erro incializando o Quartz.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            getErrorLogger().fatal("Erro finalizando Quartz.", e);
        }
    }

    /**
     * Método padrão para mensagens de log.
     */
    protected void stdLog(Class<?> jobClass, String schedule) {
        if (StringUtils.isNotBlank(schedule))
            logger.info("Inicializando job %s com o agendamento %s.", jobClass.getName(), schedule);
        else
            logger.warn("Ignorando job %s. Nenhum agendamento especificado.", jobClass.getName());
    }

    /**
     * Neste método os jobs serão criados e suas agendas serão definidas.
     */
    protected abstract void scheduleJobs(ServletContextEvent sce) throws SchedulerException;

    /**
     * Retorna o logger utilizado para erros. Deve ser uma instância retornada pelo método
     * {@link LogManager#getFormatterLogger()} ou algum log que aceite formatação de mensagens.
     */
    protected abstract Logger getErrorLogger();

}
