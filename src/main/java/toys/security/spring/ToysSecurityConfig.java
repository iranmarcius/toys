package toys.security.spring;

import toys.exceptions.ToysRuntimeException;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Esta classe é responsável por ler as configurações existentes no arquivo <code>security.config</code>, que deve existir
 * no diretório raiz das classes, e manter uma instância únida dessas informações.
 */
public class ToysSecurityConfig {
    public static final String SECURITY_PROPERTIES = "toys-security.properties";
    private static ToysSecurityConfig instance;
    private Properties props;

    private ToysSecurityConfig() {
        super();

        // Verifica a existência do arquivo de configuração.
        URL propsURL = getClass().getClassLoader().getResource(SECURITY_PROPERTIES);
        if (propsURL == null)
            throw new ToysRuntimeException("Arquivo de configuracao %s nao encontrado.", SECURITY_PROPERTIES);

        props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream(SECURITY_PROPERTIES));
        } catch (IOException e) {
            throw new ToysRuntimeException("Erro lendo arquivo de configuracoes de seguranca %s.", SECURITY_PROPERTIES);
        }

    }

    /**
     * Retorna uma instância das configurações de segurança.
     */
    public static ToysSecurityConfig getInstance() {
        if (instance == null)
            instance = new ToysSecurityConfig();
        return instance;
    }

    /**
     * Retorna uma propriedade armazenada nas configurações.
     */
    public String getProperty(String name) {
        return props != null ? props.getProperty(name) : null;
    }

    /**
     * Retorna uma cópia das propriedades lidas do arquivo de configuração.
     */
    public Properties getProperties() {
        return props != null ? new Properties(props) : null;
    }

}
