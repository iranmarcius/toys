package toys.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import ognl.OgnlException;

/**
 * Implementa métodos utilitários para operações com objetos.
 * @author Iran Marcius
 */
public final class BeanToys {

	/**
	 * Pesquisa no array de objetos do tipo {@link Method} o método cujo nome corresponda
	 * ao padrão informado.
	 * @param methods Array de métodos que será pesquisado.
	 * @param regex Expressão regular que será utilizada para localizar o método.
	 * @return Retorna uma referência para o método procurado ou <code>null</code> caso nenhum
	 * método seja encontrado ou não seja possível acessá-lo.
	 */
	public static Method pesquisarMetodo(Method[] methods, String regex) {
		for (Method m: methods)
			if (m.getName().matches(regex))
				if (Modifier.isPublic(m.getModifiers()))
					return m;
		return null;
	}

	/**
	 * Pesquisa dentro de um array com objetos do tipo {@link Field} um cujo nome seja
	 * igual ao nome informado.
	 * @param fields Array de objetos do tipo {@link Field}.
	 * @param nome Nome do campo desejado.
	 * @return Retorna o campo cujo nome corresponda ao informado ou <code>null</code> caso
	 * não exista nenhum ou não seja possível ler seu valor.
	 */
	public static Field pesquisarCampo(Field[] fields, String nome) {
		for (Field f: fields)
			if (f.getName().equals(nome))
				if (Modifier.isPublic(f.getModifiers()))
					return f;
		return null;
	}

	/**
	 * Retorna o tipo da propriedade do objeto informado.
	 * @param obj Objeto
	 * @param prop Nome da propriedade
	 * @return Retorna o tipo da propriedade
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Class<?> getTipoPropriedade(Object obj, String prop) {
		String regex = "^(get|is)" + prop.substring(0, 1).toUpperCase() + prop.substring(1) + "$";
		Method m = pesquisarMetodo(obj.getClass().getMethods(), regex);
		if (m != null) {
			return m.getReturnType();
		} else {
			Field f = pesquisarCampo(obj.getClass().getFields(), prop);
			if (f != null)
				return f.getType();
			else
				return null;
		}
	}

	/**
	 * Retorna o valor de uma propriedade de um objeto. O método tentará, primeiro, obter o valor
	 * da propriedade através de um getter. Caso não exista ou não esteja acessível, o método
	 * tentará obter o valor diretamente do campo declarado.
	 * @param obj Objeto de onde será lido o valor da propriedade
	 * @param propriedade Nome da propriedade desejada. Ex.:
	 * <ul>
	 * 	<li><code>nome = obj.getNome()</code></li>
	 * 	<li><code>situacao.descricao = obj.getSituacao().getDescricao()</code></li>
	 * </ul>
	 * @return Retorna o valor da propriedade desejada ou <code>null</code> caso não seja possível
	 * acessar o valor ou caso não exista nenhum método ou campo com o nome informado no objeto.
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Object getValor(Object obj, String prop)
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		String restante = null;
		int i = prop.indexOf(".");
		if (i > -1) {
			restante = prop.substring(i + 1);
			prop = prop.substring(0, i);
		}

		// Tenta obter o valor pelo método
		String reGetter = "^(get|is)" + prop.substring(0, 1).toUpperCase() + prop.substring(1) + "$";
		Method m = pesquisarMetodo(obj.getClass().getMethods(), reGetter);
		if (m != null) {
			Object o = m.invoke(obj);
			if (restante == null)
				return o;
			else
				return getValor(o, restante);
		}

		// Caso não tenha conseguido obter o valor através do método, tenta obter diretamente
		// do campo
		Field f = pesquisarCampo(obj.getClass().getFields(), prop);
		if (f != null) {
			Object o = f.get(obj);
			if (restante == null)
				return o;
			else
				return getValor(o, restante);
		}

		// caso nenhuma dos dois métodos tenha funcionado, retorna um valor nulo
		return null;
	}


	/**
	 * Seta o valor de uma propriedade de um objeto. O método tentará setar o valor primeiramente
	 * através do método setter. Caso não exista ou não seja acessível, tentará setar o valor diretamente
	 * no campo declarado.
	 * @param obj Objeto com a propriedade que será modificada.
	 * @param prop Nome da propriedade que será modificada.
	 * @param valor Valor que será atribuído
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void setValor(Object obj, String prop, Object valor) throws IllegalArgumentException,
		IllegalAccessException, InvocationTargetException, InstantiationException {

		String restante = null;
		int i = prop.indexOf(".");
		if (i > -1) {
			restante = prop.substring(i + 1);
			prop = prop.substring(0, i);
		}

		// tenta setar o valor através do setter
		String reSetter = "^set" + prop.substring(0, 1).toUpperCase() + prop.substring(1) + "$";
		Method m = pesquisarMetodo(obj.getClass().getMethods(), reSetter);
		if (m != null) {
			if (restante == null) {
				m.invoke(obj, valor);
			} else {
				Class<?> clazz = getTipoPropriedade(obj, prop);
				Object o = clazz.newInstance();
				setValor(o, restante, valor);
				m.invoke(obj, o);
			}
			return;
		}

		// caso não tenha sido possível setar o valor através do setter, tenta setar diretamente
		// no campo
		Field f = pesquisarCampo(obj.getClass().getFields(), prop);
		if (f != null) {
			if (restante == null) {
				f.set(obj, valor);
			} else {
				Class<?> clazz = getTipoPropriedade(obj, prop);
				Object o = clazz.newInstance();
				setValor(o, restante, valor);
				f.set(obj, o);
			}
		}

	}

	/**
	 * Popula as propriedades de um bean com os valores fornecidos no array.
	 * @param bean Objeto cujos valores serão preenchidos
	 * @param properties Nome das propriedades que serão setadas no objeto
	 * @param values Array com os valores que serão atribuídos às propriedades do bean
	 * @param startIndex Índice a partir do qual os valores do array serão lidos
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws OgnlException
	 */
	public static void populate(Object bean, String[] properties, Object[] values, int startIndex)
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		for (int i = 0; i < properties.length; i++)
			setValor(bean, properties[i], values[startIndex + i]);
	}

	/**
	 * Retorna a representação string, no formato &quot;<code>campo=valor</code>&quot;,
	 * do objeto fornecido.
	 * @param obj Objeto que será utilizado para criação da string
	 * @param propriedades Nomes dos campos que serão utilizados na criação da string.
	 * @return <code>String</code>
	 */
	public static String toString(Object obj, String... propriedades) {
		StringBuffer sb = new StringBuffer("{");
		for (String prop: propriedades) {
			try {
				sb.append(prop).append("=");
				sb.append(getValor(obj, prop));
			} catch (Exception e) {
				sb.append(e.getClass().getName());
			}
			sb.append(", ");
		}
		if (sb.length() >= 2) sb.setLength(sb.length() - 2);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Formata uma string utilizando o formato e os valores das propriedades informadas.
	 * @param obj Objeto com os valores
	 * @param formato Formato
	 * @param props Propriedades do objeto que serão lidas.
	 * @return <code>String</code>
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws OgnlException
	 */
	public static String format(Object obj, String formato, String... props) throws IllegalArgumentException,
		IllegalAccessException, InvocationTargetException {
		Object[] valores = new Object[props.length];
		for (int i = 0; i < props.length; i++)
			valores[i] = getValor(obj, props[i]);
		return String.format(formato, valores);
	}

	/**
	 * Verifica se o objeto passado é nulo. Se for, retorna uma nova instância da classe informada.
	 * @param o Objeto
	 * @param clazz Classe que será instanciada caso o objeto seja nulo
	 * @return <code>Object</code>
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object instanceIfNull(Object o, Class<?> clazz)
		throws InstantiationException, IllegalAccessException {
		if (o == null)
			o = clazz.newInstance();
		return o;
	}

	/**
	 * <p>Altera o valor de um objeto com base em um modificador fornecido.
	 * Este modificador deve ser especificado na forma <code>modificador:valor</code></p>.
	 * Os modificadores válidos, e seus tipos aplicáveis, são os seguintes:<br>
	 * <table border="0">
	 * 	<tr>
	 * 		<td><b>Modificador</b></td>
	 * 		<td><b>Tipos aplicáveis</b></td>
	 * 		<td><b>Ação</b></td>
	 * 		<td><b>Resultado</b></td>
	 * 	</tr>
	 * 	<tr>
	 *		<td><b>inc</b></td>
	 *		<td><i>Integer</i>, <i>Float</i></td>
	 *		<td>Incrementa o valor do objeto. O parâmetro pode ser positivo ou negativo, o que causará um decremento</td>
	 * 		<td>Mesmo que o fornecido</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>concat</b></td>
	 * 		<td><i>String</i>, <i>Integer</i>, <i>Float</i></td>
	 *		<td>Contatena ao valor do parâmetro ao valor string do objeto obtido.</td>
	 *		<td>String</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>concatBefore</b></td>
	 * 		<td><i>String</i>, <i>Integer</i>, <i>Float</i>, <i>Date</i></td>
	 *		<td>Funciona como o <i>concat</i>, exceto que o valor � concatenado antes no valor do objeto.</td>
	 *		<td>String</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>timeIncSeconds</b></td>
	 * 		<td><i>Date</i></td>
	 *		<td>Soma o número de segundos especificados ao objeto</td>
	 *		<td>Mesmo que o fornecido</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>timeIncMinutes</b></td>
	 * 		<td><i>Date</i></td>
	 *		<td>Soma o número de minutos especificados ao objeto</td>
	 *		<td>Mesmo que o fornecido</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>timeIncHours</b></td>
	 * 		<td><i>Date</i></td>
	 *		<td>Soma o número de horas especificadas ao objeto</td>
	 *		<td>Mesmo que o fornecido</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>timeIncDays</b></td>
	 * 		<td><i>Date</i></td>
	 *		<td>Soma o número de dias especificados ao objeto</td>
	 *		<td>Mesmo que o fornecido</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>timeIncMonths</b></td>
	 * 		<td><i>Date</i></td>
	 *		<td>Soma o número de meses especificados ao objeto</td>
	 *		<td>Mesmo que o fornecido</td>
	 *	</tr>
	 *	<tr>
	 *		<td><b>timeIncYears</b></td>
	 * 		<td><i>Date</i></td>
	 *		<td>Soma o número de anos especificados ao objeto</td>
	 *		<td>Mesmo que o fornecido</td>
	 *	</tr>
	 * </table>
	 * <br><br>
	 * Creation date: (20/2/2002 14:04:03)
	 *
	 * @param obj Objeto que servirá de base para modificação
	 * @param modifier Modificador, conforme tabela acima
	 * @return <code>Object</code>
	 */
	public static Object modify(Object obj, String modifier) {

		// separa o modificador em especificação e valor
		StringTokenizer t = new StringTokenizer(modifier, ":");
		String mod = "";
		String value = "";
		mod = t.nextToken();
		value = t.nextToken();

		// inc
		if (mod.equals("inc")) {

			// Integer
			if (obj instanceof Integer) {
				int v = Integer.parseInt(value);
				return Integer.valueOf(((Integer) obj).intValue() + v);
			}

			// Float
			if (obj instanceof Float) {
				float v = Float.parseFloat(value);
				return new Float(((Float) obj).floatValue() + v);
			}

		// contact & concatBefore
		} else if (mod.equals("concat") || mod.equals("concatBefore")) {
			if ((obj instanceof String) || (obj instanceof Integer) || (obj instanceof Float)) {
				String v = obj.toString();
				return mod.equals("concat") ? v + value : value + v;
			}

		// timeInc
		} else if (mod.startsWith("timeInc")) {
			if (obj instanceof Date) {
				int v = Integer.parseInt(value);

				// cria um objeto de calend�rio onde as opera��es ser�o aplicadas
				Calendar c = Calendar.getInstance();
				c.setTime((Date) obj);

				// verifica o modificador a ser aplicado
				int p = -1;
				if (mod.equals("timeIncSeconds")) p = Calendar.SECOND; else
				if (mod.equals("timeIncMinutes")) p = Calendar.MINUTE; else
				if (mod.equals("timeIncHours")) p = Calendar.HOUR_OF_DAY; else
				if (mod.equals("timeIncDays")) p = Calendar.DAY_OF_MONTH; else
				if (mod.equals("timeIncMonths")) p = Calendar.MONTH; else
				if (mod.equals("timeIncYears")) p = Calendar.YEAR;

				// altera o valor e retorna o objeto
				if (p != -1) {
					c.set(p, c.get(p) + v);
					return c.getTime();
				}
			}
		}

		// se o processamento chegar neste ponto, significa que o modificador especificado
		// não pode ser aplicado ao objeto fornecido
		throw new IllegalArgumentException(String.format(
				"O modificador %1$s nao pode ser aplicado ao tipo %2$s",
				modifier, obj.getClass().getName()));
	}

	/**
	 * Este método formata um objeto, seja qual for seu tipo, utilizando o formato especificado.
	 * @param obj Objeto a ser formatado
	 * @param format Especificação do formato
	 * @return <code>String</code>
	 */
	public static String formatObject(Object obj, String format) {

		// o objeto é do tipo Date ou Time
		if ((obj instanceof java.util.Date) || (obj instanceof java.sql.Time)) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format((java.util.Date) obj);

			// o objeto é um número
		} else if ((obj instanceof Integer) || (obj instanceof Float)) {
			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
			df.applyPattern(format);
			return df.format(obj);
		}

		throw new IllegalArgumentException(String.format(
				"O tipo %1$s nao e suportado", obj.getClass().getName()));
	}

	/**
	 * Cria um <code>hashCode</code> a partir dos valores informados.
	 * @param odd
	 * @param multiplier
	 * @param objs
	 * @return <code>int</code>
	 * @deprecated Utilizar templates oferecidos pelo Eclipse
	 */
	public static int computeHashCode(int odd, int multiplier, Object... objs) {
		int result = odd;
		for (Object o: objs) {
			if (o instanceof Boolean) {
				result += multiplier * result + (((Boolean)o).booleanValue() ? 0 : 1);
			} else if (o instanceof Byte) {
				result += multiplier * result + ((Byte)o).intValue();
			} else if (o instanceof Short) {
				result += multiplier * result + ((Short)o).intValue();
			} else if (o instanceof Integer) {
				result += multiplier * result + ((Integer)o).intValue();
			} else if (o instanceof Long) {
				long l = ((Long)o).longValue();
				result += multiplier * result + (int)(l ^ (l >>> 32));
			} else if (o instanceof Float) {
				result += multiplier * result + Float.floatToIntBits(((Float)o).floatValue());
			} else if (o instanceof Double) {
				long l = Double.doubleToLongBits(((Double)o).doubleValue());
				result += multiplier * result + (int)(l ^ (l >>> 32));
			} else {
				result += multiplier * result + o.hashCode();
			}
		}
		return result;
	}

	/**
	 * Retorna se dois objetos são iguals realizando a comparação através do método
	 * <code>equals</code> destes, mesmo sendo valores nulos.
	 * @param o1 Primeiro objeto
	 * @param o2 Segundo objeto
	 * @return Retorna uma flag indicando se os elementos são iguais ou não,
	 * considerando-os como iguais caso ambos sejam nulos.
	 */
	public static boolean isEqual(Object o1, Object o2) {
		if (o1 != null)
			return o1.equals(o2);
		else
			return o2 == null;
	}

}