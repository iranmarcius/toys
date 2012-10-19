/*
 * Criado em 13/08/2004
 *
 * Hist�rico de revis�es:
 */

package toys.web.struts.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import toys.constants.ParseConsts;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Validador de datas.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public class DateValidator extends FieldValidatorSupport {

	public void validate(Object obj) throws ValidationException {
		String fieldName = getFieldName();
		Object value = getFieldValue(fieldName, obj);
		String date = value != null ? value.toString() : null;

		// se a data estiver vazia e não for obrigatória, retorna sem fazer a validação
		if (StringUtils.isBlank(date)) return;

		// verifica se a data é válida
		try {
			DateUtils.parseDate(date, new String[] {ParseConsts.DATE_BR});
		} catch (Exception e) {
			addFieldError(fieldName, obj);

		}
	}

}
