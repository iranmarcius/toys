package toys.web.servlet.listeners;

import toys.persistence.EMF;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Este listener deve ser utilizado em aplicações web que utilizem a classe {@link toys.persistence.EMF} para fechar
 * os factories abertos.
 * @author Iran
 */
public class EntityManagerFactoryCloseListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Nada ocorre aqui
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EMF.destroy();
    }

}
