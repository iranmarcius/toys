package toys.utils;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * Métodos utilitários e comuns para interação com JNDI.
 * @author Iran Marcius
 */
public class JNDIUtils {
	private static Context ic;

	/**
	 * Nome default da sessão de e-mail.
	 */
	public static final String DEFAULT_MAIL_SESSION_PATH = "java:comp/env/mail/Session";

	/**
	 * Retorna um contexto inicial.
	 * O contexto será criado e cacheado na primeira chamada. Nas chamadas subseqüentes será
	 * retornado o contexto armazenado em cache.
	 * @return Context
	 * @throws NamingException
	 */
	public static Context getInitialContext() throws NamingException {
		if (ic == null)
			ic = new InitialContext();
		return ic;
	}

	/**
	 * Retorna uma mail hs com o nome default de sessão de e-mail.
	 * @return Session
	 * @see #DEFAULT_MAIL_SESSION_PATH
	 * @throws NamingException
	 */
	public static Session getMailSession() throws NamingException {
		return getMailSession(DEFAULT_MAIL_SESSION_PATH);
	}

	/**
	 * Retorna a sessão de e-mail correspondente ao caminho fornecido.
	 * Caso nenhum nome tenha sido especificado, utiliza o nome detault.
	 * @param jndiPath
	 * @return Session
	 * @throws NamingException
	 * @see #DEFAULT_MAIL_SESSION_PATH
	 */
	public static Session getMailSession(String jndiPath) throws NamingException {
		if (jndiPath == null) {
			jndiPath = DEFAULT_MAIL_SESSION_PATH;
		}
		Object o = getInitialContext().lookup(jndiPath);
		return (Session)PortableRemoteObject.narrow(o, Session.class);
	}

	/**
	 * Procura por um objeto do tipo <code>java.lang.Boolean</code> no contexto com o nome
	 * <b>debugging</b> (<code>java:comp/env/debugging</code>). Caso não encontre o objeto,
	 * retorna o valor <code>FALSE</code>.
	 * @return <code>boolean</code>
	 * @throws NamingException
	 */
	public static boolean isDebugging() throws NamingException {
		Object o = getInitialContext().lookup("java:comp/env/debugging");
		if ((o != null) && (o instanceof Boolean))
			return ((Boolean)o).booleanValue();
		else
			return false;
	}

}
