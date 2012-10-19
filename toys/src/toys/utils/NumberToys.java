package toys.utils;

import java.math.BigDecimal;



/**
 * Esta classe possui métodos utilitários para operações com valores numéricos.
 * @author Iran Marcius
 */
public class NumberToys {

	/**
	 * Retorna se um valor existe dentro do array de valores do mesmo tipo fornecido.
	 * Caso um dos parâmetros seja nulo, o método retornará <code>FALSE</code>.
	 * @param value Valor a ser pesquisado
	 * @param values Array de valores
	 * @return boolean Flag indicando se o valor foi encontrado ou não
	 */
	public static boolean exists(Integer value, Integer[] values)  {
		if (values == null) return false;
		if (value == null) return false;
		for (Integer v: values)
			if ((v != null) && v.equals(value)) return true;
		return false;
	}

	/**
	 * Retorna o valor do limite mais próximo excedido. Em outras palavras, caso algum dos
	 * limites informados tenha sido excedido pelo valor, será retornado o valor limite,
	 * do contrário será retornado o valor original.
	 * @param value Valor original
	 * @param lo Limite interior
	 * @param hi Limite superior
	 * @return <code>int</code>
	 */
	public static int bound(int value, int lo, int hi) {
		if (value < lo) return lo; else
		if (value > hi) return hi; else return value;
	}

	/**
	 * Converte uma string contendo um double formatado num valor double considerando
	 * o formato de número brasileiro.
	 * @param s String a ser convertida
	 * @return <code>java.lang.Double</code>
	 */
	public static synchronized Double toDouble(String s) {
		if (s == null)
			return null;
		if (s.indexOf('.') >= 0 && s.indexOf(',') >= 0)
			return Double.valueOf(s.replaceAll("\\.", "").replace(',', '.'));
		else if (s.indexOf(',') >= 0)
			return Double.valueOf(s.replace(',', '.'));
		else
			return Double.valueOf(s);
	}

	/**
	 * Retorna uma string convertida para o número correspondente.
	 * @param s String a ser convertida
	 * @param defaultValue Valor default caso a conversão falhe
	 * @return <code>java.lang.Double</code>
	 */
	public static Double toDouble(String s, Double defaultValue) {
		try {
			return toDouble(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Retorna uma string convertida para um valor do tipo <code>float</code>.
	 * @param s String a ser convertida.
	 * @return <code>java.lang.Float</code>
	 */
	public static Float toFloat(String s) {
		return toDouble(s).floatValue();
	}

	/**
	 * Retorna uma string convertida para um valor do tipo <code>float</code>. Caso ocorra
	 * algum erro na conversão, retorna o valor default informado.
	 * @param s String a ser convertida.
	 * @param defaultValue Valor default a ser retornado no caso de erro de conversão.
	 * @return <code>java.lang.Float</code>
	 */
	public static Float toFloat(String s, Float defaultValue) {
		try {
			return toFloat(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Retorna se um valor informado está dentro da faixa especificada.
	 * @param d Número a ser verificado
	 * @param min Limite inferior
	 * @param max Limite superior
	 * @return <code>boolean</code>
	 */
	public static boolean inRange(int d, int min, int max) {
		return d >= min && d <= max;
	}

	/**
	 * Retorna se um número informado está dentro da faixa especificada.
	 * @param d Número a ser verificado
	 * @param min Valor mínimo da faixa
	 * @param max Valor máximo da faixa
	 * @return <code>boolean</code>
	 */
	public static boolean inRange(double d, double min, double max) {
		return d >= min && d <= max;
	}


	/**
	 * Retorna se um número informado está dentro da faixa especificada.
	 * @param d Número a ser verificado.
	 * @param min Valor mínimo da faixa.
	 * @param max Valor máximo da faixa.
	 * @return <code>boolean</code>
	 */
	public static boolean inRange(long d, long min, long max) {
		return d >= min && d <= max;
	}

	/**
	 * Converte uma string para um inteiro retornando o valor default em caso
	 * de erro na conversão.
	 * @param s String a ser convertida
	 * @param defaultValue Valor default para o caso de erro
	 * @return <code>Integer</code>
	 */
	public static Integer toInt(String s, Integer defaultValue) {
		try {
			return Integer.valueOf(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Retorna o objeto informado convertido para um <code>int</code>.
	 * @param n Qualquer objeto que seja uma instância da classe {@link Number}.
	 * @return <code>int</code>
	 */
	public static int toInt(Number n) {
		if (n instanceof Integer)
			return ((Integer)n).intValue();
		if (n instanceof Long)
			return ((Long)n).intValue();
		if (n instanceof BigDecimal)
			return ((BigDecimal)n).intValue();
		throw new RuntimeException(String.format("Tipo nao reconhecido para conversao: %s",
				n.getClass().getName()));
	}

	/**
	 * Converte uma string para um long retornando o valor default caso ocorra algum
	 * erro na conversão.
	 * @param s String que será convertida
	 * @param defaultValue Valor padrão
	 * @return <code>Long</code>
	 */
	public static Long toLong(String s, Long defaultValue) {
		try {
			return Long.valueOf(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Compara os dois inteiros fornecidos retornando se são iguais. Este método
	 * trata também valores nulos, ou seja, caso um dos valores seja nulo e o outro
	 * não seja, o resultado da comparação será falso.
	 * @param i1 Primeiro inteiro
	 * @param i2 Segundo inteiro
	 * @return <code>boolean</code>
	 */
	public static boolean equals(Integer i1, Integer i2) {
		if (i1 == null && i2 == null)
			return true;
		if (i1 != null && i2 != null)
			return i1.equals(i2);
		return false;
	}

	/**
	 * Aplica o algorítmo modulo10 a um número.
	 * @param numero Número a ser analisado
	 * @return <code>int</code>
	 */
	public static int modulo10(long numero) {
		String sn = Long.toString(numero);
		int m = 2;
		int soma = 0;
		for (int i = sn.length() - 1; i >= 0; i --) {
			int n = Integer.valueOf(sn.substring(i, i + 1));
			String ns = Integer.toString(n * m);
			for (int j = 0; j < ns.length(); j++)
				soma += Integer.valueOf(ns.substring(j, j + 1));
			m = m == 1 ? 2 : 1;
		}
		int resto = soma % 10;
		return resto != 0 ? 10 - resto : 0;
	}

	/**
	 * Força o tipo de um valor numérico para outro tipo determinado.
	 * @param valor Valor a ser analisado
	 * @param clazz Tipo que deverá ser retornado
	 * @return {@link NumberToys}
	 */
	public static Number converterTipo(Number valor, Class<?> clazz) {
		if (valor.getClass().equals(clazz))
			return valor;
		if (clazz.equals(Short.class))
			return valor.shortValue();
		if (clazz.equals(Byte.class))
			return valor.byteValue();
		if (clazz.equals(Integer.class))
			return valor.intValue();
		if (clazz.equals(Long.class))
			return valor.longValue();
		if (clazz.equals(Float.class))
			return valor.floatValue();
		if (clazz.equals(Double.class))
			return valor.doubleValue();
		return valor;
	}

}
