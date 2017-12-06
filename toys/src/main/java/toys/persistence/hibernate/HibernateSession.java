package toys.persistence.hibernate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Classe utilitária para obtenção de sessões do Hibernate.
 * @author Iran Marcius
 */
public class HibernateSession {
    private static final Logger logger = LogManager.getFormatterLogger();
    private static Map<SessionFactoryParams, SessionFactory> sfm = new HashMap<>();

    private HibernateSession() {
    }

    /**
     * Retorna um <code>SessionFactory</code>.
     * @param params Objeto do tipo {@link SessionFactoryParams} com as informações de arquivos de
     * propriedades e mapeamentos que serão utilizados na obtenção do <code>SessionFactory</code>.
     * @return {@link SessionFactory}
     */
    private static synchronized SessionFactory getSessionFactory(SessionFactoryParams params) {
        logger.trace("Obtendo sessao (params=%s)", params);
        SessionFactory sf = sfm.get(params);
        if (sf == null) {
            logger.trace("Criando novo SessionFactory.");
            try {
                Properties props = new Properties();
                props.load(HibernateSession.class.getClassLoader().getResourceAsStream(params.getProperties()));

                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .configure(HibernateSession.class.getClassLoader().getResource(params.getMappings()))
                        .applySettings(props)
                        .build();

                sf = new MetadataSources(registry)
                        .buildMetadata()
                        .buildSessionFactory();

                sfm.put(params, sf);
            } catch (IOException e) {
                throw new HibernateException(e);
            }

        } else {
            logger.trace("Retornando SessionFactory do cache.");
        }
        return sf;
    }

    /**
     * Obtém uma sessão do Hibernate utilizando os parâmetros informados.
     * @param params Objeto do tipo {@link SessionFactoryParams} com as informações dos arquivos de
     * propriedades e mapeamentos.
     * @return {@link Session}
     */
    public static synchronized Session getSession(SessionFactoryParams params) {
        return getSessionFactory(params).openSession();
    }

}
