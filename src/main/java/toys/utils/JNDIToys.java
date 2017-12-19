package toys.utils;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * Métodos utilitários para operaçoes JNDI.
 * @author Iran Marcius
 */
public final class JNDIToys {
    private static Context ic;

    /**
     * Nome default da sessão de e-mail.
     */
    public static final String DEFAULT_MAIL_SESSION_PATH = "java:comp/env/mail/Session";

    protected JNDIToys() {
        super();
    }

    /**
     * Retorna uma instância contexto inicial JNDI.
     */
    public static synchronized Context getInitialContext() throws NamingException {
        if (ic == null)
            ic = new InitialContext();
        return ic;
    }

    /**
     * Retorna uma sessão de email com o nome default de sessão de e-mail.
     * @return Session
     * @see #DEFAULT_MAIL_SESSION_PATH
     * @throws NamingException
     */
    public static synchronized Session getMailSession() throws NamingException {
        return getMailSession(DEFAULT_MAIL_SESSION_PATH);
    }

    /**
     * Retorna a sessão de e-mail correspondente ao caminho fornecido. Caso nenhum nome tenha sido especificado, utiliza o nome detault.
     * @param jndiPath
     * @return Session
     * @throws NamingException
     * @see #DEFAULT_MAIL_SESSION_PATH
     */
    public static synchronized Session getMailSession(String jndiPath) throws NamingException {
        Object o = getInitialContext().lookup(jndiPath != null ? jndiPath : DEFAULT_MAIL_SESSION_PATH);
        return (Session)PortableRemoteObject.narrow(o, Session.class);
    }

    /**
     * Retorna o valor da propriedade <b>ambienteDesenvolvimento</b> armazenada no contexto JNDI.
     * @throws NamingException
     */
    public static synchronized boolean isAmbienteDesenvolvimento() throws NamingException {
        return (Boolean)getInitialContext().lookup("java:comp/env/ambienteDesenvolvimento");
    }

}
