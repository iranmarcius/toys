package toys;

/**
 * Esta classe possui métodos utilitários para operações com valores numéricos.
 *
 * @author Iran Marcius
 */
public class NumberToys {

  private NumberToys() {
  }

  /**
   * Retorna se um valor existe dentro do array de valores do mesmo tipo fornecido.
   * Caso um dos parâmetros seja nulo, o método retornará <code>FALSE</code>.
   *
   * @param value  Valor a ser pesquisado
   * @param values Array de valores
   * @return boolean Flag indicando se o valor foi encontrado ou não
   */
  public static boolean exists(Integer value, Integer[] values) {
    if (values == null) return false;
    if (value == null) return false;
    for (Integer v : values)
      if ((v != null) && v.equals(value)) return true;
    return false;
  }

  /**
   * Retorna o valor do limite mais próximo excedido. Em outras palavras, caso algum dos
   * limites informados tenha sido excedido pelo valor, será retornado o valor limite,
   * do contrário será retornado o valor original.
   *
   * @param value Valor original
   * @param lo    Limite interior
   * @param hi    Limite superior
   * @return <code>int</code>
   */
  public static int boundary(int value, int lo, int hi) {
    if (value < lo) return lo;
    else if (value > hi) return hi;
    else return value;
  }

  /**
   * Converte uma string contendo um double formatado num valor double considerando
   * o formato de número brasileiro.
   *
   * @param s String a ser convertida
   * @return <code>java.lang.Double</code>
   */
  public static Double toDouble(String s) {
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
   *
   * @param s            String a ser convertida
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
   * Retorna o valor {@link Double} representado pelo número informado.
   *
   * @param n            Objeto do tipo {@link Number}.
   * @param valorDefault Valor default a ser retornado caso o número seja nulo.
   * @return {@link Double}
   */
  public static double toDouble(Number n, double valorDefault) {
    return n != null ? n.doubleValue() : valorDefault;
  }

  /**
   * Retorna o valor double representado pelo número informado ou nulo.
   */
  public static Double toDouble(Number n) {
    return n != null ? n.doubleValue() : null;
  }

  /**
   * Retorna uma string convertida para um valor do tipo <code>float</code>.
   *
   * @param s String a ser convertida.
   * @return <code>java.lang.Float</code>
   */
  public static Float toFloat(String s) {
    return s != null ? toDouble(s).floatValue() : null;
  }

  /**
   * Retorna uma string convertida para um valor do tipo <code>float</code>. Caso ocorra
   * algum erro na conversão, retorna o valor default informado.
   *
   * @param s            String a ser convertida.
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
   *
   * @param d   Número a ser verificado
   * @param min Limite inferior
   * @param max Limite superior
   * @return <code>boolean</code>
   */
  public static boolean inRange(int d, int min, int max) {
    return d >= min && d <= max;
  }

  /**
   * Retorna se um número informado está dentro da faixa especificada.
   *
   * @param d   Número a ser verificado
   * @param min Valor mínimo da faixa
   * @param max Valor máximo da faixa
   * @return <code>boolean</code>
   */
  public static boolean inRange(double d, double min, double max) {
    return d >= min && d <= max;
  }


  /**
   * Retorna se um número informado está dentro da faixa especificada.
   *
   * @param d   Número a ser verificado.
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
   *
   * @param s            String a ser convertida
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
   * Retorna o {@link Integer} representado pelo número informado.
   *
   * @param n            Um objeto do tipo {@link Number}.
   * @param valorDefault Valor que deve ser retornado caso o objeto informado seja nulo.
   * @return <code>int</code>
   */
  public static int toInt(Number n, int valorDefault) {
    return n != null ? n.intValue() : valorDefault;
  }

  /**
   * Retorna o valor {@link Integer} representado pelo número informado ou nulo;
   */
  public static Integer toInt(Number n) {
    return n != null ? n.intValue() : null;
  }

  /**
   * Retorna uma string onde haja números inteiros separados por vírgula e converte num
   * array de inteiros.
   *
   * @param s String on formato <code>inteiro, inteiro, inteiro... inteiro</code>.
   * @return <code>int[]</code>
   */
  public static int[] toIntArray(String s) {
    var ss = s.split(" *, *");
    var ints = new int[ss.length];
    for (int i = 0; i < ss.length; i++)
      ints[i] = Integer.parseInt(ss[i]);
    return ints;
  }

  /**
   * Converte uma string para um long retornando o valor default caso ocorra algum
   * erro na conversão.
   *
   * @param s            String que será convertida
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
   * Aplica o algorítmo modulo10 a um número representado pela string informada.
   *
   * @param numero Número a ser analisado.
   * @return <code>int</code>
   */
  public static String modulo10(String numero) {
    int m = 2;
    int soma = 0;
    for (int i = numero.length() - 1; i >= 0; i--) {
      int n = Integer.parseInt(numero.substring(i, i + 1));
      String ns = Integer.toString(n * m);
      for (int j = 0; j < ns.length(); j++)
        soma += Integer.parseInt(ns.substring(j, j + 1));
      m = m == 1 ? 2 : 1;
    }
    int resto = soma % 10;
    return String.valueOf(resto != 0 ? 10 - resto : 0);
  }

  /**
   * Força o tipo de um valor numérico para outro tipo determinado.
   *
   * @param valor Valor a ser analisado
   * @param clazz Tipo que deverá ser retornado
   * @return {@link NumberToys}
   */
  public static Number convert(Number valor, Class<?> clazz) {
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

  /**
   * Retorna o resultado da soma dos dois valores inteiros prevenindo valores nulos.
   *
   * @param a Primeiro valor inteiro.
   * @param b Segundo valor inteiro.
   * @return <code>Double</code>
   */
  public static Integer sum(Integer a, Integer b) {
    if (a == null && b == null)
      return 0;
    else if (a == null)
      return b;
    else if (b == null)
      return a;
    else
      return a + b;
  }

  /**
   * Retorna o resultado da soma dos dois valores double prevenindo valores nulos.
   *
   * @param a Primeiro valor double.
   * @param b Segundo valor double.
   * @return <code>Double</code>
   */
  public static Double sum(Double a, Double b) {
    if (a == null && b == null)
      return 0d;
    else if (a == null)
      return b;
    else if (b == null)
      return a;
    else
      return a + b;
  }

}
