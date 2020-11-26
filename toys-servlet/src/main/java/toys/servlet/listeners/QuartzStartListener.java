package toys.servlet.listeners;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toys.application.quartz.QuartzUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener para inicialização de tarefas do Quarts.
 *
 * @author Iran
 */
public class QuartzStartListener implements ServletContextListener {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Inicializando agendador de tarefas.");

        String propsFilename = sce.getServletContext().getInitParameter("jobsProperites");
        if (propsFilename == null)
            propsFilename = "quartz.properties";

        try {
            scheduler = new QuartzUtils()
                .withEnv("appRoot", sce.getServletContext().getRealPath("."))
                .init(propsFilename);

        } catch (Exception e) {
            logger.error("Erro inicializando agendador de tarefas.", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            try {
                scheduler.shutdown();
                logger.info("Quartz foi encerrado.");
            } catch (SchedulerException e) {
                logger.error("Erro finalizando Quartz.", e);
            }
        }
    }

}
