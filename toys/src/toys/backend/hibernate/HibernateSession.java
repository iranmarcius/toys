package toys.backend.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import toys.fs.FileToys;

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
	private static SessionFactory createSessionFactory(SessionFactoryParams params) throws HibernateException {
		log.debug(String.format("Obtendo sessao (params=%s)", params));
		Properties props = null;
		SessionFactory sf = sfm.get(params);
		if (sf == null) {
			log.debug("SessionFactory nao encontrado. Un novo sera criado.");
			Configuration configuration = new Configuration();

			// Verifica se devem ser carregadas as propriedades de um arquivo especificado.
			if (StringUtils.isNotBlank(params.getProperties())) {
				InputStream in = null;
				try {
					log.debug(String.format("Utilizando propriedades do arquivo %s", params.getProperties()));
					in = HibernateSession.class.getClassLoader().getResourceAsStream(params.getProperties());
					props = new Properties();
					props.load(in);
					configuration.setProperties(props);
				} catch (IOException e) {
					throw new HibernateException(e);
				} finally {
					FileToys.closeQuiet(in);
				}
			}

			// Verifica se foi especificado algum arquivo de mapeamento.
			if (!StringUtils.isEmpty(params.getMappings())) {
				log.debug(String.format("Utilizando mapeamentos do arquivo %s", params.getMappings()));
				configuration.configure(params.getMappings());
			} else {
				log.debug("Utilizando mapeamentos do arquivo padrao");
				configuration.configure();
			}

			StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder();
			if (props != null)
				srb.applySettings(props);

			sf = configuration.buildSessionFactory(srb.build());
			sfm.put(params, sf);
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
		return createSessionFactory(params).openSession();
	}

}
