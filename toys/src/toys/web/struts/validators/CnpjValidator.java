/*
 * Criado em 05/07/2004
 */

package toys.web.struts.validators;

import org.apache.commons.lang3.StringUtils;

import toys.utils.ValidationToys;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Validator para CNPJs. Este validador n�o obriga que o valor do CNPJ seja informado,
 * ele apenas valida um CNPJ caso o valor tenha sido informado. Para impor obrigatoriedade
 * no valor ele deve ser utilizado em conjunto com o <i>requiredstring</i>.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public class CnpjValidator extends FieldValidatorSupport {

	public void validate(Object obj) throws ValidationException {
		String fieldName = getFieldName();
		Object value = getFieldValue(fieldName, obj);
		if (StringUtils.isNotBlank((String)value))
			if (!ValidationToys.isCNPJValid(value.toString()))
				addFieldError(fieldName, obj);
	}

}
