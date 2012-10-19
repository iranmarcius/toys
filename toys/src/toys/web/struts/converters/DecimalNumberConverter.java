package toys.web.struts.converters;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import toys.utils.NumberToys;

/**
 * Converte strings para valores com ponto decinal (Float e Long).
 * @see NumberToys#toDouble(String)
 * @see NumberToys#toDouble(String, Double)
 * @see NumberToys#toFloat(String)
 * @see NumberToys#toFloat(String, Float)
 * @author Iran Marcius
 */
public class DecimalNumberConverter extends StrutsTypeConverter {
	private Logger logger;

	public DecimalNumberConverter() {
		super();
		logger = Logger.getLogger(getClass());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		Object[] result = null;
		if (toClass.equals(Float.class)) {
			result = new Float[values.length];
		} else if (toClass.equals(Double.class)) {
			result = new Double[values.length];
		} else {
			logger.warn("O valor a ser convertido deve ser do tipo java.lang.Float ou java.lang.Double");
			return null;
		}

		for (int i = 0; i < values.length; i++) {
			if (toClass.equals(Float.class)) {
				result[i] = NumberToys.toFloat(values[i], null);
			} else if (toClass.equals(Double.class)) {
				result[i] = NumberToys.toDouble(values[i], null);
			} else {
				logger.warn(String.format("Valor nao convertido: %1$s", values[i]));
			}
		}

		return values.length == 1 ? result[0] : result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}

}
