/*
 * Criado em 20/03/2008 11:12:59
 */

package toys.web.struts.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * Converte um valor em um array de strings. O valor poderá estar separado por vírgulas ou
 * colchetes e vírgulas como nos exemplo abaixo:
 * <ul>
 * 	<li><code>valor1,valor2</code> =&gt; String[] {"valor1", "valor2"}</code></li>
 * 	<li><code>[valor 1],[valor 2] =&gt; String[] {"valor 1", "valor 2"}</code></li>
 * </ul>
 * @author Iran
 * @author Ednei Parmigiani Júnior
 */
public class StringArrayConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		List<String> l = split(values);
		String[] result = new String[l.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = l.get(i);
		return result.length > 0 ? result : null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}

	protected List<String> split(String[] values) {
		List<String> l = new ArrayList<String>();
		for (String value: values) {
			String[] v = null;
			if (value.indexOf("],[") > -1) {
				value = value.substring(1, value.length() - 1);
				v = value.split("\\]\\,\\[");
			} else {
				v = value.split(",");
			}
			for (String s: v)
				if (StringUtils.isNotBlank(s))
					l.add(s);
		}
		return l;
	}

}
