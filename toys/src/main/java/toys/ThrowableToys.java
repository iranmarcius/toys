/*
 * Criado em 29/09/2010 15:07:20
 */

package toys;

import java.sql.SQLException;

/**
 Métodos utilitários para manupulação de erros.
 * @author Iran
 */
public final class ThrowableToys {

    private ThrowableToys() {
        super();
    }

    /**
     * Percorre a cadeia de erros procurando um erro de SQL e retornando a mensagem associada.
     * Caso o erro não seja encontrado retorna nulo.
     */
    public static String extractSQLError(Throwable t) {
        Throwable error = t;
        while (error != null && !(error instanceof SQLException))
            error = error.getCause();
        return error instanceof SQLException ? error.getMessage() : null;
    }

}
