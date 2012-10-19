package toys.web.struts.converters;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class BooleanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values[0] != null) {
        	return (values[0].trim().equals("")) ? null : new Boolean(values[0]);
        }

        return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}

}
