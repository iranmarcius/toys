/*
 * Criado em 20/03/2008 11:32:30
 */

package toys.web.struts.converters;

import java.util.List;
import java.util.Map;

import toys.utils.NumberToys;

/**
 * Converte uma string em um array de <code>Integer</code> seguindo as mesmas especificações
 * da classe <code>{@link StringArrayConverter}</code>. Os valores que não puderem ser convertidos
 * serão setados como nulos.
 * @author Iran
 */
public class IntegerArrayConverter extends StringArrayConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		List<String> l = split(values);
		Integer[] result = new Integer[l.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = NumberToys.toInt(l.get(i), null);
		return result.length > 0 ? result : null;
	}

}
