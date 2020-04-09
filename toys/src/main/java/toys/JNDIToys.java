package toys;

import javax.mail.Session;
import javax.naming.*;
import java.util.Properties;

/**
 * Métodos utilitários para operaçoes JNDI.
 *
 * @author Iran Marcius
 */
public final class JNDIToys {
    private static Context ic;

    /**
     * Nome default da sessão de e-mail.
     */
    public static final String PATH_MAIL_SESSION = "java:global/mail/Session";

    /**
     * Caminho para o armazenamento da flag de ambiente de desenvolvimento.
     */
    public static final String PATH_AMBIENTE_DESENVOLVIMENTO = "java:global/toys/ambienteDesenvolvimento";

    /**
     * Caminho para o armazenamento da senha mestre nas configurações de segurança.
     */
    public static final String PATH_MASTER_KEY = "java:global/toys/seguranca/masterKey";

    /**
     * Caminho base onde são armazenadas as configurações de segurança.
     */
    public static final String PATH_LDAP_CONFIG = "java:global/toys/seguranca/ldap";

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
     *
     * @return Retorna uma sessão de e-mail.
     * @see #PATH_MAIL_SESSION
     */
    public static synchronized Session getMailSession() throws NamingException {
        return getMailSession(PATH_MAIL_SESSION);
    }

    /**
     * Retorna a sessão de e-mail correspondente ao caminho fornecido. Caso nenhum nome tenha sido especificado, utiliza o nome detault.
     *
     * @param jndiPath Caminho do objeto de sessão dentro do diretório.
     * @return Session Retorna uma sessão de e-mail.
     * @see #PATH_MAIL_SESSION
     */
    public static synchronized Session getMailSession(String jndiPath) throws NamingException {
        Object o = getInitialContext().lookup(jndiPath != null ? jndiPath : PATH_MAIL_SESSION);
        return (Session) o;
    }

    /**
     * Tenta obter um valor do JNDI retornando o valor  default caso ele não exista. O valor
     * armazenado no JNDI deve ser do mesmo tipo do valor default.
     *
     * @param lookup       Nome para pesquisa.
     * @param defaultValue Valor default caso o nome original não exista.
     * @return Retorna o valor armazenado no JNDI ou o valor default.
     */
    @SuppressWarnings("unchecked")
    public static synchronized <T> T lookup(String lookup, T defaultValue) {
        try {
            return (T) getInitialContext().lookup(lookup);
        } catch (NamingException e) {
            return defaultValue;
        }
    }

    /**
     * Retorna a senha mestre armazenada nas configurações de segurança no caminho especificado em {@link #PATH_MASTER_KEY}.
     */
    public static synchronized String getMasterKey() throws NamingException {
        return (String) getInitialContext().lookup(PATH_MASTER_KEY);
    }

    /**
     * Retorna um objeto de propriedades lidas à partir de um caminho JNDI fornecido. Os nomes
     * das propriedades não irão conter o caminho informado, ou seja, caso o caminho final seja
     * <code>java:/global/seguranca/servidor</code> o nome da propriedade será apenas <code>servidor</code>.
     *
     * @param basePath Caminho base para a leitura das configuraçõe.
     * @return Retorna um objeto do tipo {@link Properties}.
     */
    public static synchronized Properties getJNDIProperties(String basePath) throws NamingException {
        NamingEnumeration<Binding> enums = getInitialContext().listBindings(basePath);
        Properties props = new Properties();
        while (enums.hasMoreElements()) {
            Binding b = enums.nextElement();
            props.put(b.getName(), b.getObject());
        }
        return props;
    }

}
