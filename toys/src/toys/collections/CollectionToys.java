/*
 * Criado em 22/07/2004
 */

package toys.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import toys.beans.pairs.KeyValue;
import toys.beans.pairs.StringPair;

/**
 * Conjunto o de métodos utilitários para operações em coleções.
 * @author Iran Marcius
 */
public class CollectionToys {

	/**
	 * Retorna uma sub-lista construída a partir dos índices informados.
	 * @param source Lista de onde os elementos serão copiados
	 * @param index Array de índices dos elementos que farão parte da nova lista.
	 * @return <code>{@link List List}</code>
	 */
	public static List<?> subList(List<?> source, int[] index) {
		List<Object> l = new ArrayList<Object>();
		for (int i = 0; i < index.length; i++) {
			l.add(source.get(index[i]));
		}
		return l;
	}

	/**
	 * Cria um mapa de chave/valor a partir da lista informada. A conversão será executada
	 * apenas em objetos do tipo {@link KeyValue}, {@link StringPair} e <code>Object[]</code>,
	 * neste último considerando apenas os dois primeiros elementos do array.
	 * @param l Lista a ser convertida
	 * @return <code>Map</code>
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Map asMap(List l) {
		Map m = new HashMap();
		for (Object o: l) {
			if (o instanceof KeyValue) {
				KeyValue p = (KeyValue)o;
				m.put(p.getKey(), p.getValue());
			} else if (o instanceof StringPair) {
				StringPair p = (StringPair)o;
				m.put(p.getKey(), p.getValue());
			} else if (o instanceof Object[]) {
				Object[] a = (Object[])o;
				if (a.length >= 2)
					m.put(a[0], a[1]);
			}
		}
		return m;
	}

	/**
	 * Retorna se uma coleção é nula ou vazia.
	 * @param c Coleção a ser verificada
	 * @return <code>boolean</code>
	 */
	public static boolean empty(Collection<?> c) {
		return (c != null) && !c.isEmpty();
	}

	/**
	 * Retorna uma lista convertida em string utilizando os separadores informados e
	 * envolvendo cada item com o quote informado.
	 * @param l
	 * @param separator
	 * @param quote
	 * @return <code>String</code>
	 */
	public static String toString(List<?> l, String separator, String quote) {
		if ((l == null) || l.isEmpty())
			return "";
		StringBuffer sb = new StringBuffer();
		for (Object o: l) {
			if (quote != null) sb.append(quote);
			sb.append(o.toString());
			if (quote != null) sb.append(quote);
			if (separator != null) sb.append(separator);
		}
		if (sb.length() > separator.length())
			sb.setLength(sb.length() - separator.length());
		return sb.toString();
	}

	/**
	 * Retorna uma lista conv ertida em string utilizando o separador informado.
	 * @param l
	 * @param separator
	 * @return <code>String</code>
	 */
	public static String toString(List<?> l, String separator) {
		return toString(l, separator, null);
	}

	/**
	 * Retorna uma lista convertida para string com seus itens separados por vírgula.
	 * @param l
	 * @return <code>String</code>
	 */
	public static String toString(List<?> l) {
		return toString(l, ", ");
	}

}
