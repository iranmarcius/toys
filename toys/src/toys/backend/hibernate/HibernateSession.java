package toys.backend.hibernate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static final Log log = LogFactory.getLog(HibernateSession.class);
	private static Map<SessionFactoryParams, SessionFactory> sfm = new HashMap<SessionFactoryParams, SessionFactory>();

	/**
	 * Retorna um <code>SessionFactory</code>.
	 * @param params Objeto do tipo {@link SessionFactoryParams} com as informações de arquivos de
	 * propriedades e mapeamentos que serão utilizados na obtenção do <code>SessionFactory</code>.
	 * @return {@link SessionFactory}
	 * @throws HibernateException
	 */
	private static SessionFactory getSessionFactory(SessionFactoryParams params) throws HibernateException {
		log.debug(String.format("Obtendo sessao (params=%s)", params));
		SessionFactory sf = sfm.get(params);
		if (sf == null) {
			log.debug("Criando novo SessionFactory.");

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
				throw new RuntimeException(e);
			}

		} else {
			log.debug("Retornando SessionFactory ja inicializado.");
		}
		return sf;
	}

	/**
	 * Obtém uma sessão do Hibernate utilizando os parâmetros informados.
	 * @param params Objeto do tipo {@link SessionFactoryParams} com as informações dos arquivos de
	 * propriedades e mapeamentos.
	 * @return {@link Session}
	 */
	public static Session getSession(SessionFactoryParams params) {
		return getSessionFactory(params).openSession();
	}

}
