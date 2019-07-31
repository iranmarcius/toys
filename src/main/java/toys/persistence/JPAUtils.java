package toys.persistence;

import org.apache.logging.log4j.Logger;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Métododos utilitários para ambientes que utilize JPA.
 *
 * @author Iran
 * @since 07/2019
 */
public class JPAUtils {

    private JPAUtils() {
    }

    public static void finalizeTransaction(UserTransaction transaction, Logger logger) {
        if (transaction == null)
            return;
        try {
            if (transaction.getStatus() == Status.STATUS_MARKED_ROLLBACK)
                transaction.rollback();
        } catch (SystemException e) {
            logger.fatal("Erro realizando rollback na transacao.", e);
        }
    }

}
