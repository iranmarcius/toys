/*
 * Criado em 29/10/2004
 */

package toys.collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Possui métodos utilitários para executar operações com arrays.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public class ArrayToys {

	/**
	 * Retorna os itens de um array como uma string.
	 * @param array Array a ser processado
	 * @param separator Separador de itens do array
	 * @param quote Expressão a ser inserida no início e no final de cada item além
	 * do separador
	 * @return <code>String</code>
	 */
	public static String toString(Object[] array, String separator, String quote) {
		if (array == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if (quote != null) sb.append(quote);
			sb.append(array[i].toString());
			if (quote != null) sb.append(quote);
			if (separator != null) sb.append(separator);
		}
		if (sb.length() > separator.length()) {
			sb.setLength(sb.length() - separator.length());
		}
		return sb.toString();
	}

	/**
	 * Retorna uma string com os itens do array separados com o separador informado.
	 * @param array Array com os itens
	 * @param separator Separador que será utilizado na construção da lista
	 */
	public static String toString(Object[] array, String separator) {
		return toString(array, separator, null);
	}

	/**
	 * Retorna uma string com os itens do array separados com vírgula. Ex.: 1, 2, 3, 4.
	 * @param array Array de itens
	 */
	public static String toString(Object[] array) {
		return toString(array, ", ");
	}

	/**
	 * Retorna um array de strings a partir do array de objetos informado.
	 * @param array Array com os objetos
	 * @return retorna o array com as strings de cada item do array de objetos.
	 */
	public static String[] stringArray(Object[] array) {
		if (array == null) {
			return null;
		}
		String[] s = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			s[i] = array[i] != null ? array[i].toString() : null;
		}
		return s;
	}

	/**
	 * Retorna um array de strings a partir da lista fornecida.
	 * @param l Lista com os itens que farão parte do array.
	 * @return Retornao um array de strings com os itens da lista informada.
	 */
	public static String[] stringArray(List<String> l) {
		return stringArray(l.toArray());
	}

	/**
	 * Retorna a intersecção dos elementos dos dois arrays.
	 * @param a1 Primeiro array
	 * @param a2 Segundo array
	 * @return <code>Object[]</code>
	 */
	public static Object[] intersection(Object[] a1, Object[] a2) {
		if ((a1 == null) || (a2 == null)) return new String[0];
		int c = 0;
		Object[] si = new Object[a1.length + a2.length];
		for (int i = 0; i < a1.length; i++) {
			for (int j = 0; j < a2.length; j++) {
				if (a1[i].equals(a2[j])) {
					si[c++] = a1[i];
				}
			}
		}
		Object[] r = new Object[c];
		System.arraycopy(si, 0, r, 0, c);
		return r;
	}
	
	/**
	 * Converte um array de <i>Strings</i> em uma array de <i>Integers</i>.
	 * @param strings Array com as strings.
	 * @return Retorna um array de <i>Integer</i> 
	 */
	public static Integer[] toIntegerArray(String[] strings) {		
		if (strings == null)
			return null;
		
		int s = strings.length;
		Integer[] integers = new Integer[s];
		for (int i = 0; i < s; i++)
			integers[i] = Integer.valueOf(strings[i]);
		
		return integers;
	}

	/**
	 * Converte a lista de <i>Integers</i> em um array de <i>Integers</i>.
	 * @param l List com os inteiros.
	 * @return Retorna um array de <i>Integer</i>.
	 */
	public static Integer[] integerArray(List<Integer> l) {
		if (l == null) {
			return null;
		}
		Integer[] integers = new Integer[l.size()];
		int s = l.size();
		for (int i = 0; i < s; i++) {
			integers[i] = l.get(i);
		}
		return integers;
	}

	/**
	 * Retorna a posição de um elemento dentro de um array.
	 * @param element Elemento a ser pesquisado
	 * @param array Array a ser pesquisado
	 * @return Retorna o índice do elemento ou -1 caso o elemento não exista no
	 * array ou o array ou o elemento sejam nulos
	 */
	public static int indexOf(Object element, Object[] array) {
		if ((array == null) || (element == null))
			return -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Este método funciona como o {@link #indexOf(Object, Object[])}, exceto pelo fato de que
	 * trabalha apenas com strings não levando em conta a caixa das strings.
	 * @param s String a ser pesquisada
	 * @param array Array de strings no qual a pesquisa será feita
	 * @return Retorna a posição da string dentro do array ou -1 caso não seja encontrada.
	 */
	public static int indexOfIgnoreCase(String s, String[] array) {
		if ((s == null) || (array == null))
			return -1;
		String expr = s.toUpperCase();
		for (int i = 0; i < array.length; i++) {
			if (array[i].toUpperCase().equals(expr))
				return i;
		}
		return -1;
	}

	/**
	 * Inverte a ordem dos elementos de um array.
	 * @param a Array a ser invertido.
	 */
	public static void reverse(Object[] a) {
		int i = 0;
		int j = a.length - 1;
		while (i < j) {
			Object o = a[i];
			a[i] = a[j];
			a[j] = o;
			i++;
			j--;
		}
	}

	/**
	 * Desloca os itens de um array para a direita. Os itens que forem deslocados para além da
	 * capacidade do array serão perdidos. Os itens que ficarem à esquerda serão nulos.
	 * @param array Array cujos itens serão deslocados
	 * @param shift Número de posições do deslocamento
	 */
	public static void shiftRight(Object[] array, int shift) {
		for (int i = array.length - shift - 1; i >= 0; i--) array[i + shift] = array[i];
		for (int i = shift - 1; i >= 0; i--) array[i] = null;
	}

	/**
	 * Desloca os itens de um array para a esquerda. Os itens que forem deslocados para
	 * uma posição abaixo de zero, serão perdidos. Os itens que ficarem à direita serão nulos.
	 * @param array Array cujos itens serão deslocados
	 * @param shift Número de posições do deslocamento
	 */
	public static void shiftLeft(Object[] array, int shift) {
		for (int i = shift; i < array.length; i++) array[i - shift] = array[i];
		for (int i = shift + 1; i < array.length; i++) array[i] = null;
	}

	/**
	 * Retorna uma cópia do array original eliminando todos as posições nulas.
	 * Por conseqüência, o array retornado poderá ter um tamanho menor que o original.
	 * @param array Array a ser "compactado"
	 * @return <code>Object[]</code>
	 */
	public static Object[] compact(Object[] array) {
		if (array == null) return null;
		if (array.length == 0) return new Object[0];
		int i = 0;
		for (Object o: array) if (o != null) i++;
		Object[] newArray = new Object[i];
		i = 0;
		for (Object o: array) if (o != null) newArray[i++] = o;
		return newArray;
	}
	
	/**
	 * Retorna se os arrays informados são iguais comparando cada elemento.
	 * @param a1 Primeiro array.
	 * @param a2 Segundo array.
	 * @return <code>boolean</code>
	 */
	public static boolean isEquals(Object[] a1, Object[] a2) {
		
		if ((a1 == null && a2 != null) ||
			(a1 != null && a2 == null) ||
			(a1.length != a2.length))
			return false;
		
		for (int i = 0; i < a1.length; i++)
			if (!a1[i].equals(a2[i]))
				return false;
		
		return true;
	}
	
	/**
	 * Converte um array de strings no formato <code><b>key=value</b></code> para um <code>Map</code>
	 * onde <b>key</b> será a chave e <b>value</b> será o valor. Ambos, chave e valor, serão
	 * do tipo <code>String</code>
	 * @param pairs Array de strings no formato esperado.
	 * @return {@link Map}
	 */
	public static Map<String, String> makeMap(String[] pairs) {
		Map<String, String> m = new HashMap<String, String>();
		for (String s: pairs) {
			int i = s.indexOf('=');
			String key = s.substring(0, i).trim();
			String value = s.substring(i + 1).trim();
			m.put(key, value);
		}
		return m;
	}

}
