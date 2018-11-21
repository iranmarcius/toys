package toys.utils;

import javax.mail.Session;
import javax.naming.*;
import java.util.Properties;

/**
 * Métodos utilitários para operaçoes JNDI.
 * @author Iran Marcius
 */
public final class JNDIToys {
    private static Context ic;

    /**
     * Nome default da sessão de e-mail.
     */
    public static final String PATH_MAIL_SESSION = "java:/comp/env/mail/Session";

    /**
     * Caminho para o armazenamento da flag de ambiente de desenvolvimento.
     */
    public static final String PATH_AMBIENTE_DESENVOLVIMENTO = "java:/comp/env/toys/ambienteDesenvolvimento";

    /**
     * Caminho para o armazenamento da senha mestre nas configurações de segurança.
     */
    public static final String PATH_MASTER_KEY = "java:/comp/env/toys/seguranca/masterKey";

    /**
     * Caminho base onde são armazenadas as configurações de segurança.
     */
    public static final String PATH_LDAP_CONFIG = "java:/comp/env/toys/seguranca/ldap";

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
     * @return Retorna uma sessão de e-mail.
     * @see #PATH_MAIL_SESSION
     */
    public static synchronized Session getMailSession() throws NamingException {
        return getMailSession(PATH_MAIL_SESSION);
    }

    /**
     * Retorna a sessão de e-mail correspondente ao caminho fornecido. Caso nenhum nome tenha sido especificado, utiliza o nome detault.
     * @param jndiPath Caminho do objeto de sessão dentro do diretório.
     * @return Session Retorna uma sessão de e-mail.
     * @see #PATH_MAIL_SESSION
     */
    public static synchronized Session getMailSession(String jndiPath) throws NamingException {
        Object o = getInitialContext().lookup(jndiPath != null ? jndiPath : PATH_MAIL_SESSION);
        return (Session)o;
    }

    /**
     * Retorna o valor da propriedade <b>ambienteDesenvolvimento</b> armazenada no contexto JNDI.
     */
    public static synchronized boolean isAmbienteDesenvolvimento() throws NamingException {
        return (Boolean)getInitialContext().lookup(PATH_AMBIENTE_DESENVOLVIMENTO);
    }

    /**
     * Retorna a senha mestre armazenada nas configurações de segurança no caminho especificado em {@link #PATH_MASTER_KEY}.
     */
    public static synchronized String getMasterKey() throws NamingException {
        return (String)getInitialContext().lookup(PATH_MASTER_KEY);
    }

    /**
     * Retorna as configurações de segurança em forma de propriedades. As configurações serão lidas a partir do caminho especificado
     * em {@link #PATH_LDAP_CONFIG}.
     * @return Retorna um mapa de propriedades contendo as configurações de segurança.
     */
    public static synchronized Properties getLDAPConfig() throws NamingException {
        NamingEnumeration<Binding> enums = getInitialContext().listBindings(PATH_LDAP_CONFIG);
        Properties props = new Properties();
        while (enums.hasMoreElements()) {
            Binding b = enums.nextElement();
            props.put(b.getName(), b.getObject());
        }
        return props;
    }

}
