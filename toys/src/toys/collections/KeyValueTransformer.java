package toys.collections;

import org.apache.commons.collections.Transformer;

import toys.beans.pairs.KeyValue;

/**
 * Este transformer deve ser utilizado com o <i>CollectionUtils.collect</i> para converter
 * um item qwue seja um array de dois objetos em um
 * {@link toys.beans.pairs.KeyValue KeyValue}, sendo que o primeiro item do array ser�
 * assumido como sendo a chave e o segundo como sendo o valor.
 * @author Iran Marcius
 */
public class KeyValueTransformer<K, V> implements Transformer {

	@SuppressWarnings("unchecked")
	public Object transform(Object obj) {
		Object[] o = (Object[])obj;
		return new KeyValue<K, V>((K)o[0], (V)o[1]);
	}

}
