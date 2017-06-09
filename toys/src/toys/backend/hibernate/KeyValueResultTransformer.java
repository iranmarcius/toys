package toys.backend.hibernate;

import org.hibernate.transform.BasicTransformerAdapter;
import org.hibernate.transform.ResultTransformer;

import toys.KeyValue;

/**
 * Este {@link ResultTransformer} converte o resultado de uma consulta em um objeto {@link KeyValue}.
 * Para tanto serão utilizados os valores das duas primeiras colunas da pesquisa como chave (key) e valoe (value)
 * respectivamente.
 * @author Iran
 */
public class KeyValueResultTransformer extends BasicTransformerAdapter {
	private static final long serialVersionUID = 4079801972598844466L;

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		return new KeyValue<Object, Object>(tuple[0], tuple[1]);
	}
	
}
