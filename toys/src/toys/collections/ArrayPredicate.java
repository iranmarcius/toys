/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 * Criado em 24/03/2005
 */

package toys.collections;

import org.apache.commons.collections.Predicate;

/**
 * Utilizado para pesquisas em listas cujos itens sejam arrays de objetos.
 * @author Iran Marcius
 */
public class ArrayPredicate implements Predicate {

	/**
	 * Informação a ser localizada.
	 */
	public Object data;

	/**
	 * Índice do item do array que será comparado com o valor pesquisado.
	 */
	public int comparisonIndex;

	/**
	 * Construtor default.
	 */
	public ArrayPredicate() {
		super();
	}

	/**
	 * Construtor com inicialização das propriedades.
	 * @param data Valor a ser pesquisado
	 * @param comparisonIndex índice do item do array que será utilizado para comparação
	 */
	public ArrayPredicate(Object data, int comparisonIndex) {
		this();
		this.data = data;
		this.comparisonIndex = comparisonIndex;
	}

	/**
	 * Faz a comparação.
	 */
	public boolean evaluate(Object object) {
		Object[] o = (Object[])object;
		return o[comparisonIndex].equals(data);
	}

}
