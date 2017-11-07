
package toys.tests;

import java.lang.reflect.InvocationTargetException;

import toys.tests.beans.Bean2;
import toys.utils.BeanToys;

public class BeanToysTest {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException,
		InvocationTargetException, InstantiationException {

		Bean2 b = new Bean2();
		BeanToys.setValor(b, "nome", "Teste");
		BeanToys.setValor(b, "ativo", true);
		BeanToys.setValor(b, "bean1.nome", "Nome");
		System.out.println(BeanToys.getValor(b, "ativo"));
		System.out.println(BeanToys.getValor(b, "nome"));
		System.out.println(BeanToys.getValor(b, "bean1.nome"));
	}

}
