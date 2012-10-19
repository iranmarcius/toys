/*
 * Departamento de Desenvolvimento - ISIC Brasil 
 * Todos os direitos reservados
 * Criado em 28/04/2005
 */

package toys.collections;

import org.apache.commons.collections.Transformer;

/**
 * Este transformador de itens retorna o primeiro item de um array de objetos.
 * É particularmente útil para conversão de lista de arrays de objetos retornados
 * por queries do Hibernate.
 * @author Iran Marcius
 */
public class FirstArrayItemTransformer implements Transformer {

	public Object transform(Object input) {
		return ((Object[])input)[0];
	}

}
