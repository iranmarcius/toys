package toys.persistence;

import org.slf4j.LoggerFactory;
import toys.exceptions.ToysPersistenceException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Classe utilitária para obtenção e gerenciamento de vários EntityManagerFactory.
 *
 * @author Iran
 */
public class EMF {
    private static final Map<String, EntityManagerFactory> emfMap = new HashMap<>();

    private EMF() {
    }

    /**
     * Retorna o {@link EntityManagerFactory} correspondente ao nome informado. Caso o objeto não exista no mapa
     * ele será instanciado utilizando as propriedades informadas, do contrário será retornado o objeto existente.
     *
     * @param unitName   Nome da unidade de persistência.
     * @param properties Mapa de propriedades adicionais para o {@link EntityManagerFactory}. Caso o valor seja nulo
     *                   serão utilizadas somente as propriedades existentes no arquivo <code>persistence.xml</code>.
     * @return Retorna uma instância do {@link EntityManagerFactory} solicitado.
     */
    public static synchronized EntityManagerFactory getEntityManagerFactory(String unitName, Properties properties) throws ToysPersistenceException {

        if (unitName == null)
            throw new ToysPersistenceException("Nome da unidade de persistencia nao foi informado");

        var logger = LoggerFactory.getLogger(EMF.class);
        EntityManagerFactory emf = emfMap.get(unitName);
        if (emf == null) {
            logger.debug("Criando EntityManagerFactory para a unidade de persistencia {}.", unitName);
            emf = properties == null ? Persistence.createEntityManagerFactory(unitName) : Persistence.createEntityManagerFactory(unitName, properties);
            emfMap.put(unitName, emf);
        } else {
            logger.debug("Retornando EntityManagerFactory do cache para a unidade de persistencia {}.", unitName);
        }

        return emf;
    }

    /**
     * Retorna o {@link EntityManagerFactory} correspondente ao nome informado utilizando as propriedades adicionais no arquivo informado.
     *
     * @param unitName       Nome da unidade de persistência.
     * @param propertiesFile Nome do arquivo com as propriedades adicionais. O arquivo deve estar dentro do diretório de resources. Caso ele não exista
     *                       serão utilizadas somente as propriedades do arquivo <code>persistence.xml</code>.
     * @return Retorna uma instância do {@link EntityManagerFactory} solicitado.
     * @see #getEntityManagerFactory(String, Properties)
     */
    public static synchronized EntityManagerFactory getEntityManagerFactory(String unitName, String propertiesFile) throws IOException, ToysPersistenceException {
        InputStream in = EMF.class.getClassLoader().getResourceAsStream(propertiesFile);
        Properties properties = null;
        if (in != null) {
            properties = new Properties();
            properties.load(in);
        }
        return getEntityManagerFactory(unitName, properties);
    }

    /**
     * Retorna o {@link EntityManagerFactory} correspondente ao nome informado. Caso exista um arquivo de propriedades com o nome da unidade de persistência
     * na raiz da pasta resources, utiliza as propriedades adicionais além das propriedades no arquivo <code>persistence.xml</code>.
     *
     * @param unitName Nome da unidade de persistência.
     * @return Retorna uma instância do {@link EntityManagerFactory} solicitado.
     * @see #getEntityManagerFactory(String, Properties)
     */
    public static synchronized EntityManagerFactory getEntityManagerFactory(String unitName) throws ToysPersistenceException, IOException {
        return getEntityManagerFactory(unitName, String.format("%s.properties", unitName));
    }

    /**
     * Fecha o {@link EntityManager} informado prevenindo um valor nulo.
     */
    public static synchronized void close(EntityManager em) {
        if (em != null)
            em.close();
    }

    /**
     * Método utilitário para realizar rollback em transações realizando verificações. O rollback só será
     * invocado se o {@link EntityManager} informado não for nulo e se houver uma transação ativa.
     *
     * @param em Objeto do tipo {@link EntityManager}.
     */
    public static synchronized void rollbackTransaction(EntityManager em) {
        if (em != null && em.getTransaction().isActive())
            em.getTransaction().rollback();
    }

    /**
     * Fecha e remove todas as instâncias do mapa de {@link EntityManagerFactory}.
     */
    public static synchronized void destroy() {
        var logger = LoggerFactory.getLogger(EMF.class);
        for (Map.Entry<String, EntityManagerFactory> entry : emfMap.entrySet()) {
            entry.getValue().close();
            logger.debug("EntityManagerFactory para a unidade de persistencia {} fechado.", entry.getKey());
        }
        emfMap.clear();
    }

}
