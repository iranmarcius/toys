package toys.jsf.converters;

import toys.StringPair;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "stringPairConverter")
public class StringPairConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            String[] ss = value.split("\\|");
            if (ss.length > 1)
                return new StringPair(ss[0], ss[1]);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof StringPair) {
            StringPair sp = (StringPair) value;
            return String.format("%s|%s", sp.getKey(), sp.getValue());
        } else {
            return "";
        }
    }

}
