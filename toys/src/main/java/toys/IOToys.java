package toys;

import java.io.Closeable;

/**
 * Classe com métodos utilitários para gerenciamento de streams.
 *
 * @author Iran
 * @since 01/2019
 */
public class IOToys {

	/**
	 * Fecha um stream sem emitir erro.
	 */
	public static void closeQuietly(Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (Exception e) {
				// Nada ocorre aqui
			}
	}

}
