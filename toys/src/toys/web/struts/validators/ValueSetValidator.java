package toys.web.struts.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.validator.ValidationException;

/**
 * Este validator verifica se um valor está dentro de um conjunto de valores especificado.
 * A lista de valores a ser utilizada na validação deverá ser especificada utilizando-se
 * o parâmetro <code>values</code> tantas vezes quanto forem os valores a serem utilizados.
 * Adcionalmente é possível especificar uma condição para a validação pois este é também um
 * validador condicional.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 * @see ConditionalValidatorSupport
 */
public class ValueSetValidator extends ConditionalValidatorSupport {

	/**
	 * Lista de valores que serão utilizados na validação.
	 */
	protected List<String> values;

	/**
	 * Construtor padrão.
	 */
	public ValueSetValidator() {
		super();
		values = new ArrayList<String>();
	}

	/**
	 * Executa a validação verificando se o valor especificado está dentro da lista
	 * de valores válidos.
	 */
	@Override
	protected void doValidation(Object obj) throws ValidationException {

		// verifica se o valor enviado é válido
		String fieldName = getFieldName();
		Object o = getFieldValue(fieldName, obj);
		if (!(o instanceof String) ||
			StringUtils.isBlank(o.toString()) ||
			(Collections.binarySearch(values, o.toString()) < 0)) {

			addFieldError(fieldName, obj);
		}
	}

	/**
	 * Seta a lista de valores válidos.
	 * @param commaValues String com os valores, separados por vírgula, que serão
	 * considerados como válidos.
	 */
	public void setValues(String commaValues) {
		if (StringUtils.isBlank(commaValues)) return;
		String[] s = commaValues.trim().split(" *, *");
		values.addAll(Arrays.asList(s));
		Collections.sort(values);
	}

}
