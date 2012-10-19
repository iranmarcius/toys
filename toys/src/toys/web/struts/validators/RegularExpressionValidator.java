package toys.web.struts.validators;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Este validador valida um valor com uma express�o regular informada.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public class RegularExpressionValidator extends FieldValidatorSupport {

	/**
	 * Express�o que ser� utilizada para validar o valor.
	 */
	protected String expression;

	/**
	 * Realiza a valida��o. Caso o valor seja nulo a valida��o ser� ignorada.
	 */
	public void validate(Object obj) throws ValidationException {
		if (StringUtils.isBlank(expression)) return;
		String fieldName = getFieldName();
		Object value = getFieldValue(fieldName, obj);
		if (!(value instanceof String) ||
			StringUtils.isBlank(value.toString()) ||
			!value.toString().matches(expression)) {
			addFieldError(fieldName, obj);
		}
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String string) {
		expression = string;
	}

}
