package toys.web.servlet.listeners;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.cache.FileTemplateLoader;
import toys.utils.FreemarkerUtils;

/**
 * Listener de contexto para inicializar a classe {@link FreemarkerUtils} e disponibilizá-la no contexto da aplicação.
 * Atualmente o listener não aceita parâmetros assumindo por padrão que os templates residem no diretório <code>/WEB-INF/templates/freemarker</code>.
 * @author Iran
 */
public class FreemarkerInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger logger = LogManager.getFormatterLogger();
        logger.info("Inicializando utilitario Freemarker.");

        // Inicializa a classe utilitária do Freemarker
        try {
            String templatesPath = sce.getServletContext().getRealPath("/WEB-INF/templates/freemarker");
            FreemarkerUtils.init(new FileTemplateLoader(new File(templatesPath)));
            logger.info("Caminho para os templates: %s", templatesPath);
        } catch (Exception e) {
            LogManager.getLogger().fatal("Erro inicializando o utilitario Freemarker. As operacoes que utilizarem templates nao funcionarao.", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nenhuma operação é necesária aqui
    }

}
