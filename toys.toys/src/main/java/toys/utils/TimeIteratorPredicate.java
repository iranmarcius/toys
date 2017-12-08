/*
 * Criado em 31/08/2009 17:26:33
 */

package toys.utils;

import java.util.Date;

/**
 * Definição da interface utilizada no {@link TimeIterator}.
 * @author Iran
 */
public interface TimeIteratorPredicate {
	void execute(Date d, TimeIterator iterator);
}
