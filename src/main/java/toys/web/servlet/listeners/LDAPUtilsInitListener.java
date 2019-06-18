package toys.web.servlet.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toys.JNDIToys;
import toys.LDAPUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * Este listener inicializa a classe {@link LDAPUtils} com os parâmetros configurados no contexto da aplicação
 * no arquivo web.xml e armazena a instância inicializada no contexto da aplicação.
 * @author Iran
 */
public class LDAPUtilsInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger logger = LogManager.getFormatterLogger(getClass());
        logger.info("Inicializando utilitario LDAP.");
        try {
            Properties props = JNDIToys.getLDAPConfig();
            LDAPUtils ldapUtils = new LDAPUtils(props);
            sce.getServletContext().setAttribute(LDAPUtils.class.getName(), ldapUtils);
            logger.debug("Utilitario LDAP iniciado: %s", ldapUtils);
        } catch (Exception e) {
            LogManager.getLogger(getClass()).fatal("Erro incializando utilitario LDAP.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nada acontece aqui.
    }

}
