package toys.backend.hibernate;

import org.hibernate.transform.BasicTransformerAdapter;

/**
 * Converte o primeiro resultado da tupla para inteiro. O primeiro resultado
 * deverá necessariamente inteiro.
 * @author Iran
 */
public class IntegerTransformer extends BasicTransformerAdapter {
	private static final long serialVersionUID = 766622019606439869L;

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		return ((Number)tuple[0]).intValue();
	}

}
