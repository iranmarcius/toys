/*
 * Criado em 13/01/2005
 */

package toys.collections;

import org.apache.commons.collections.Predicate;

import toys.beans.pairs.KeyValue;

/**
 * Classe utilizada para pesquisa em listas cujos itens sejam do tipo
 * <code>{@link KeyValue}</code>.
 * @author Iran Marcius
 */
public class KeyValuePredicate implements Predicate {
	
	/**
	 * Tipos de pesquisa.
	 */
	enum Type {KEY, VALUE};
	
	public Type searchType;
	public Object data;
	
	/**
	 * Retorna uma instância da classe
	 * @param searchType
	 */
	public KeyValuePredicate(Type searchType) {
		super();
		this.searchType = searchType; 
	}

	public boolean evaluate(Object o) {
		KeyValue<?, ?> p = (KeyValue<?, ?>)o;
		if (searchType == Type.KEY) {
			return  p.getKey().equals(data);
		} else if (searchType == Type.VALUE) {
			return p.getValue().equals(data);
		}
		return false;
	}

}
