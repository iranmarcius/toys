package toys;

import toys.exceptions.ToysRuntimeException;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * <p>Esta classe fornece uma forna unificada de acessar configurações de aplicação á partir de um arquivo de propriedades.</p>
 * <p>Por padrão, quando uma instância é criada, ela tentará ler as propriedades de um arquivo de recurso (resource file)
 * com o nome <b>toys-config.properties</b>. Caso o arquivo não seja encontrado um erro será gerado.</p>
 * <p>Aplicações poderão sobrecarregar esta classe incluindo métodos que forneçam acesso a configurações específicas,
 * bastando que para isso as configurações sejam incluídas no arquivo de recurso <b>toys-config.properties</b>.</p>
 *
 * @author Iran
 * @since 19/11/2018
 */
public class ToysConfig {
    public static final String CONFIG_FILENAME = "toys-config.properties";
    private static ToysConfig instance;
    private Properties props;

    protected ToysConfig() {
        super();

        // Verifica a existência do arquivo de configuração.
        URL propsURL = getClass().getClassLoader().getResource(CONFIG_FILENAME);
        if (propsURL == null)
            throw new ToysRuntimeException("Arquivo de configuracoes %s nao encontrado.", CONFIG_FILENAME);

        props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILENAME));
        } catch (IOException e) {
            throw new ToysRuntimeException("Erro lendo arquivo '%s' com as configuracoes da aplicacao.", CONFIG_FILENAME);
        }
    }

    /**
     * Retorna uma instância da classe.
     */
    public static ToysConfig getInstance() {
        if (instance == null)
            instance = new ToysConfig();
        return instance;
    }

    /**
     * Retorna uma propriedade armazenada nas configurações.
     */
    public String getProperty(String name) {
        return props != null ? props.getProperty(name) : null;
    }

    /**
     * Retorna um novo objeto de propriedades construído à partir das propriedades cujo nome inicie o prefixo informado.
     * @param prefixo Prefixo que será utilizado na filtragem.
     * @param manterPrefixo Flag indicando se o prefixo informado deve ser mantido no nome da propriedade do novo mapa.
     * @return Properties
     */
    public Properties getProperties(String prefixo, boolean manterPrefixo) {
        Properties propsFiltradas = new Properties();
        for (Map.Entry<Object, Object> entry: props.entrySet())
            if (entry.getKey().toString().startsWith(prefixo)) {
                String nome = entry.getKey().toString();
                if (!manterPrefixo)
                    nome = nome.substring(prefixo.length() + 1);
                propsFiltradas.put(nome, entry.getValue());
            }
        return propsFiltradas;
    }

}
