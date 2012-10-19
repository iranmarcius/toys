/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 * Criado em 03/02/2005
 */

package toys.web.struts.validators;

import org.apache.commons.lang3.StringUtils;

import toys.utils.DateToys;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Valida uma string verificando se ela representa uma hora v�lida.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public class TimeValidator extends FieldValidatorSupport {

	public void validate(Object obj) throws ValidationException {
		String fieldName = getFieldName();
		Object value = getFieldValue(fieldName, obj);
		String time = value != null ? value.toString() : null;
		if (StringUtils.isBlank(time)) return;

		// verifica se a data á válida
		if (DateToys.createTime(time) == null) {
			addFieldError(fieldName, obj);
		}
	}

}
