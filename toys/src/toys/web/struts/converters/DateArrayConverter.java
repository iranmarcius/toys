/*
 * Criado em 20/03/2008 11:47:39
 */

package toys.web.struts.converters;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import toys.constants.ParseConsts;

/**
 * Converte um valor string em um array de datas do tipo <code>java.util.Date</code> ou <code>java.sql.Date</code>
 * seguindo as mesmas especificações da classes <code>{@link StringArrayConverter}</code>.
 * @author Iran
 */
public class DateArrayConverter extends StringArrayConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		List<String> l = split(values);
		Object[] result = null;
		if (toClass.equals(java.util.Date[].class))
			result = new java.util.Date[l.size()];

		if (toClass.equals(java.sql.Date[].class))
			result = new java.sql.Date[l.size()];

		if (result == null)
			throw new RuntimeException("Nenhum parametro foi informado");

		for (int i = 0; i < result.length; i++) {
			Date d = toDate(l.get(i));
			if (toClass.equals(java.util.Date[].class)) {
				result[i] = d;
			} else if (toClass.equals(java.sql.Date[].class)) {
				if (d != null)
					result[i] = new java.sql.Date(d.getTime());
			}
		}
		return result.length > 0 ? result : null;
	}

	/**
	 * Retorna os milisegundos de uma data no formato string.
	 */
	private Date toDate(String s) {
		try {
			return DateUtils.parseDate(s, new String[] {ParseConsts.DATE_BR, ParseConsts.DATE_SQL});
		} catch (Exception e) {
			return null;
		}
	}

}
