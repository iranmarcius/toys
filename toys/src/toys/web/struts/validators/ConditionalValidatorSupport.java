package toys.web.struts.validators;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * <p>Esta classe cont�m a implementa��o b�sica para validadores condicionais.
 * Entenda-se por validadores condicionais, aqueles cuja valida��o de um campo
 * aocorrer� apenas mediante � satisfa��o de uma condi��o.</p>
 * <p>Os crit�rios para especifica��o da condi��o de valida��o obedecem aos mesmos do
 * validador <i>com.opensymphony.xwork2.validator.validators.ExpressionValidator</i>.</p>
 *
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public abstract class ConditionalValidatorSupport extends FieldValidatorSupport {

	/**
	 * Condi��o para que a valida��o seja invocada.
	 * Caso esta condi��o n�o seja especificada, a valida��o correr�.
	 */
	protected String condition;

	/**
	 * Este m�todo apenas valida a condi��o fornecido para determinar se a valida��o ser�
	 * executada ou n�o. Caso a condi��o resulte em <i>true</i> ser� invocado o m�todo
	 * {@link #doValidation(Object) doValidation} que far� a valida��o do valor enviado.
	 */
	public void validate(Object obj) throws ValidationException {

		// caso nenhuma condisão tenha sido informada, executa a validação.
		if (StringUtils.isBlank(condition)) {
			doValidation(obj);
			return;
		}

		Boolean answer = Boolean.FALSE;
		Object o = getFieldValue(condition, obj);
		if ((o != null) && (o instanceof Boolean)) {
			answer = (Boolean)o;
		}

		if (answer.booleanValue()) doValidation(obj);
	}

	/**
	 * Realiza a valida��o do valor enviado.
	 */
	protected abstract void doValidation(Object obj) throws ValidationException;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
