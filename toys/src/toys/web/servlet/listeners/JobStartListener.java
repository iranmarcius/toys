package toys.web.servlet.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Implementação base para listeners de inicialização de tarefas.
 * @author Iran
 */
public abstract class JobStartListener implements ServletContextListener {
	protected Log log;
	protected Scheduler scheduler;

	public JobStartListener() {
		super();
		log = LogFactory.getLog(getClass());
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduleJobs(sce);
			scheduler.start();
		} catch (SchedulerException e) {
			handleSchedulerException("Erro incializando o Quartz.", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			handleSchedulerException("Erro finalizando Quartz.", e);
		}
	}

	/**
	 * Método padrão para mensagens de log.
	 */
	protected void log(Class<?> jobClass, String schedule) {
		if (StringUtils.isNotBlank(schedule))
			log.info(String.format("Inicializando job %s com o agendamento %s.", jobClass.getName(), schedule));
		else
			log.warn(String.format("Ignorando job %s. Nenhum agendamento especificado.", jobClass.getName()));
	}

	/**
	 * Gerenciamento de erros.
	 */
	protected abstract void handleSchedulerException(String msg, Throwable e);

	/**
	 * Neste método os jobs serão criados e suas agendas serão definidas.
	 */
	protected abstract void scheduleJobs(ServletContextEvent sce) throws SchedulerException;

}
