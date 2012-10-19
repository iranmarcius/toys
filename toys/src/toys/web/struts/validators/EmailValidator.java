/*
 * Criado em 09/09/2004
 */

package toys.web.struts.validators;

import org.apache.commons.lang3.StringUtils;

import toys.constants.RegExprConsts;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Classe para valida��o de e-mails. O valor n�o � obrigat�rio, mas se for fornecido
 * ter� que estar dentro das especifica��es do formato.
 * @author Iran Marcius
 */
public class EmailValidator extends FieldValidatorSupport {

	public void validate(Object o) throws ValidationException {
		String fieldName = getFieldName();
		Object value = getFieldValue(fieldName, o);
		String email = value != null ? value.toString() : null;
		if (StringUtils.isBlank(email)) return;
		if (!email.matches(RegExprConsts.EMAIL)) addFieldError(fieldName, o);
	}

}
