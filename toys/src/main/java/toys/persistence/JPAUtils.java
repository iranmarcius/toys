package toys.persistence;

import org.slf4j.Logger;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Métododos utilitários para ambientes JPA.
 *
 * @author Iran
 * @since 07/2019
 */
public class JPAUtils {

    private JPAUtils() {
    }

    /**
     * Realiza rollback na transação informada prevenindo a propagação de erros e logando-os caso necessário.
     *
     * @param transaction Objeto do tipo {@link UserTransaction} com a transação na qual será realizado o rollback.
     * @param logger      Caso informado, este logger será utilizado para registrar eventuais erros na operação de rollback.
     */
    public static synchronized void rollback(UserTransaction transaction, Logger logger) {
        if (transaction == null)
            return;
        try {
            int status = transaction.getStatus();
            if (status == Status.STATUS_ACTIVE || status == Status.STATUS_MARKED_ROLLBACK)
                transaction.rollback();
        } catch (SystemException e) {
            logger.error("Erro realizando rollback na transacao.", e);
        }
    }

}
