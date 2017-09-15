package toys.web.servlet.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;

import toys.utils.FreemarkerUtils;

/**
 * Listener de contexto para inicializar a classe {@link FreemarkerUtils}. Atualmente o listener não aceita
 * parâmetros, portanto os templates serão buscados no diretório padrão <code>/WEB-INF/templates/freemarker</code>.
 * @author Iran
 */
public class FreemarkerInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LogManager.getLogger().info("Inicializando utilitario Freemarker.");

        // Inicializa a classe utilitária do Freemarker
        try {
            FreemarkerUtils.init(sce.getServletContext().getRealPath("/WEB-INF/templates/freemarker"));
        } catch (Exception e) {
            LogManager.getLogger().fatal("Erro inicializando o utilitario Freemarker. As operacoes que utilizarem templates nao funcionarao.", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nenhuma operação é necesária aqui
    }

}
