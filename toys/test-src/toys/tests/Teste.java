package toys.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import toys.tests.beans.Bean1;

public class Teste {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Bean1 bean1 = new Bean1();
		Method getter = bean1.getClass().getMethod("getValor");
		Object[] ens = getter.getReturnType().getEnumConstants();
		for (Object en: ens)
			System.out.println(en);
		
	}

}
