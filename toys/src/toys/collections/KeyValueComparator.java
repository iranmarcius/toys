package toys.collections;

import java.io.Serializable;
import java.util.Comparator;

import toys.beans.pairs.KeyValue;

/**
 * Este comparator deve ser utilizado para objetos do tipo
 * <code>{@link KeyValue}</code> com um valor string informado.
 * @author Iran Marcius
 */
public class KeyValueComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = 7564195171262568471L;
	
	/**
	 * Se esta flag estiver ativada, a comparação será feita sem levar em consideração
	 * a caixa das palavras.
	 */
	public boolean caseInsensitive;
	
	/**
	 * Construtor default.
	 */
	public KeyValueComparator() {
		super();
		caseInsensitive = false;
	}
	
	/**
	 * Construtor com inicialização valores.
	 * @param caseInsensitive Flag indicando se a caixa do texto deve ser respeitada.
	 */
	public KeyValueComparator(boolean caseInsensitive) {
		this();
		this.caseInsensitive = caseInsensitive;
	}
	
	/**
	 * @see Comparator#compare(Object, Object)
	 */
	@Override
	public int compare(Object o1, Object o2) {
		KeyValue<?, ?> p = (KeyValue<?, ?>)o1;
		String s1 = (String)p.getValue();
		String s2 = (String)o2;
		if (caseInsensitive) {
			s1 = s1.toUpperCase();
			s1 = s2.toUpperCase();
		}
		return s1.compareTo(s2);
	}

}
