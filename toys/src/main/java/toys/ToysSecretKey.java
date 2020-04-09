package toys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Classe com métodos e constantes relacionados à segurança.
 * @author Iran
 * @since 09/10/2018
 */
public class ToysSecretKey {
    private static final String ALGORITHM = "AES";
    private static SecretKey instance;

    private ToysSecretKey() {
    }

    /**
     * Retorna uma chave para codificação e decodificação de informações.
     * @return byte[]
     */
    private static byte[] getKey() {
        return new byte[] {
            -51, -32, 125, -16, 26, -116, 96, 63, 7, 107, 59, 12, -111, -127, 85, 76, 84,
            -119, 113, 124, 22, -61, 28, 105, 55, 44, -61, -91, -112, -86, 124, 105
        };
    }

    /**
     * Retorna uma instância da chave secreta.
     */
    public static synchronized SecretKey getInstance() {
        if (instance == null)
            instance = new SecretKeySpec(getKey(), ALGORITHM);
        return instance;
    }

}
