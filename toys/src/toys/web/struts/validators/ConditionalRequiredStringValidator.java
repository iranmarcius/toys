package toys.web.struts.validators;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.validator.ValidationException;

/**
 * Este validador verifica se uma string é requerida de acordo com a condição fornecida.
 * O parâmetro {@link ConditionalValidatorSupport#condition condition}
 * irá possuir a condição que será verificada.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 * @see ConditionalValidatorSupport
 */
public class ConditionalRequiredStringValidator extends ConditionalValidatorSupport {

	@Override
	protected void doValidation(Object obj) throws ValidationException {
		String fieldName = getFieldName();
		Object value = getFieldValue(fieldName, obj);
		if (!(value instanceof String) ||
			StringUtils.isBlank(value.toString())) {
			addFieldError(fieldName, obj);
		}

	}

}
