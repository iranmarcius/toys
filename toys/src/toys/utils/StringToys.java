/*
 * Todos os direitos reservados
 */

package toys.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import toys.constants.RegExprConsts;

/**
 * Possui métodos utilitários para manipulação de strings.
 * @author Iran Marcius
 */
public class StringToys {

	/**
	 * Retorna se uma string está num formato que represente um número com casas
	 * decimais.
	 * @param number String representando um número
	 * @return <code>boolean</code>
	 */
	public static boolean isDecimalFormat(String number) {
		return (number != null) && number.matches(RegExprConsts.DECIMAL_NUMBER);
	}

	/**
	 * Retorna uma string fornecida com seu tamanho limitado para o máximo informado.
	 * @param s String que será processada
	 * @param maxLength Tamanho máximo do valor que será retornado
	 * @return Retorna a string com o tamanho truncado para o máximo permitido ou, caso
	 * o valor original seja nulo ou seu tamanho não ultrapasse o máximo, a própria
	 * string.
	 */
	public static String limitLength(String s, int maxLength) {
		return (s == null) || (s.length() <= maxLength) ? s : s.substring(0, maxLength);
	}

	/**
	 * Gera uma string com uma quantidade de espaços à direita e limita seu tamanho
	 * ao limite informado.
	 * @param s String original.
	 * @param maxLength Tamanho da string resultante
	 * @return <code>String</code>
	 */
	public static String spacesRight(String s, int maxLength) {
		String fmtStr = "%-" + maxLength + "s";
		return String.format(fmtStr, s != null ? s : "").substring(0, maxLength);
	}

	/**
	 * Retorna um double formatado com zeros à esquerda, limitando seu tamanho ao máximo
	 * informado e com o número de casas decimais informadas.
	 * @param n Número que será formatado
	 * @param maxLength Tamanho máximo
	 * @param decimals Casas decimais
	 * @return
	 */
	public static String zerosLeft(double n, int maxLength, int decimals) {
		String fmtStr = "%0" + (maxLength + 1) + "." + decimals + "f";
		return String.format(fmtStr, n).replaceAll("[\\.,]", "").substring(0, maxLength);
	}

	/**
	 * Retorna um inteiro formatado com zeros à esquerda limitando seu tamanho ao máximo
	 * informado.
	 * @param n Número que será formatado
	 * @param maxLength Tamanho máximo
	 * @return
	 */
	public static String zerosLeft(int n, int maxLength) {
		String fmtStr = "%0" + maxLength + "d";
		return String.format(fmtStr, n).substring(0, maxLength);
	}

	/**
	 * Retorna a representação de uma string em hexadecimal.
	 * @param s String que será transformada
	 * @return <code>String</code>
	 */
	public static String str2hex(String s) {
		return str2hex(s.getBytes());
	}

	/**
	 * Retorna o array de bytes como uma string de valores hexadecimais.
	 * @param b Array de bytes
	 * @return <code>String</code>
	 */
	public static String str2hex(byte[] b) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			s.append(String.format("%02x", b[i]));
		return s.toString();
	}

	/**
	 * Converte uma string com representações hexadecimais em uma string normal.
	 * @param s String com a representação hexadecimal dos bytes
	 * @return <code>String</code>
	 */
	public static String hex2str(String s) {
		if (s.length() % 2 > 0)
			return null;
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < s.length(); i += 2)
			b[(i + 1) / 2] = (byte)Integer.parseInt(s.substring(i, i + 2), 16);
		return new String(b);
	}

	/**
	 * Retorna um array de inteiros a partir de uma string que representando um número.
	 * @param s String a ser processada
	 * @return Returna um array de <code>int</code> com os números contidos na string.
	 * Caso qualquer um dos caracteres da string não sejam a representação de um
	 * número, a função retornará <code>null</code>.
	 */
	public static int[] toIntArray(String s) {
		try {
			int[] ints = new int[s.length()];
			for (int i = 0; i < s.length(); i++)
				ints[i] = Integer.parseInt(s.substring(i, i + 1));
			return ints;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converte um valor string numa lista de valores considerando a vírgula e os finais de linha como separadores de itens.
	 * @param str Valor string.
	 * @return <code>List&lt;String&gt;</code>
	 */
	public static List<String> toList(String str) {
		List<String> l = new ArrayList<>();
		String[] sa = str.split("\r\n");
		for (String s: sa) {
			String[] ss = s.split(" *, *");
			for (String sss: ss)
				l.add(sss);
		}
		return l;
	}

	/**
	 * Retorna uma string que está delimitada entre outras duas informadas ou
	 * <code>null</code> caso não haja uma string entre as duas informadas.
	 * @param s String a ser pesquisada
	 * @param s1 Primeira string
	 * @param s2 Segunda string
	 * @return <code>String</code>
	 */
	public static String stringBetween(String s, String s1, String s2) {
		int i = s.indexOf(s1);
		int j = s.indexOf(s2);
		if ((i == -1) || (j == -1) || (i > j)) {
			return null;
		}
		return s.substring(i + s1.length(), j);
	}

	/**
	 * Faz substituições das variáveis existentes na string pelos valores
	 * existentes no objeto de propriedades informado. Serão consideradas variáveis
	 * todas as expressões escritas no formado <b>${nome}</b>.
	 * @param s String original.
	 * @param p mapa de propreidades cujos valores serão utilizados na substituição.
	 * Os valores que não forem encontrados no objeto de propriedades serão substituídos
	 * por vazios.
	 * @return <code>String</code>
	 */
	public static String replaceProperties(String s, Properties p) {

		// substitui as propriedades existentes
		Iterator<Map.Entry<Object, Object>> i = p.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<Object, Object> entry = i.next();
			s = s.replaceAll("\\$\\{" + entry.getKey().toString() + "\\}",
				entry.getValue().toString());
		}

		// substitui todas as outras variáveis não encontradas no mapa de propriedades
		// por valores vazios
		s = s.replaceAll("\\$\\{.+\\}", "");

		return s;
	}

	/**
	 * Faz substituições das variáveis existentes na string pelos valores existentes
	 * nas propriedades do sistema.
	 * @param s String original
	 * @see #replaceProperties(String, Properties)
	 */
	public static String replaceProperties(String s) {
		return replaceProperties(s, System.getProperties());
	}

	/**
	 * Retorna uma nova string composta pela repetição da string original.
	 * @param s String a ser repetida
	 * @param count Número de repetições
	 * @return String
	 */
	public static String repeat(String s, int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * Retorna uma nova string criada a partir da string informada, preenchida com espaços à
	 * direita de acordo com o tamanho especificado.
	 * @param s String original
	 * @param length Tamanho
	 * @return <code>String</code>
	 */
	public static String forceLength(String s, int length) {
		String format = new StringBuffer().append("%-").append(length).append("s").toString();
		return String.format(format, s).substring(0, length);
	}

	/**
	 * Processa uma string mantendo apenas os desejados.
	 * @param s String a ser processada
	 * @param allow Regular expression com os caracteres que devem ser mantidos na string.
	 * @return String
	 */
	public static String cleanValue(String s, String allow) {
		if (s == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
			if (s.substring(i, i + 1).matches(allow))
				sb.append(s.charAt(i));
		return sb.toString();
	}

	/**
	 * Retorna uma string criada a partir do erro informado.
	 * @param t Objeto com o erro
	 * @return <code>String</code>
	 */
	public static String toString(Throwable t) {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		t.printStackTrace(new PrintStream(bao));
		return new String(bao.toByteArray());
	}

	/**
	 * Retorna a primeira palavra antes do primeiro espaço em uma string.
	 * Caso não haja espaços retorna a string original.
	 * @param s String
	 * @return String
	 */
	public static String firstWord(String s) {
		if (s == null)
			return null;
		int i = s.indexOf(" ");
		return i > -1 ? s.substring(0, i) : s;
	}

	/**
	 * Retorna a primeira letra de uma string convertida para maiúsculo. Caso a letra seja uma vogal
	 * acentuada retorna ela sem o acento.
	 * @param s String que será anlisada.
	 * @return <code>String</code>
	 */
	public static String primeiraLetraSemAcento(String s) {
		if (s == null || s.length() < 2)
			return "";
		String l = s.toUpperCase().substring(0, 1);
		if ("ÁÀÃÂ".indexOf(l) > -1)
			return "A";
		else if ("ÉÈÊ".indexOf(l) > -1)
			return "E";
		else if ("ÍÌÎ".indexOf(l) > -1)
			return "I";
		else if ("ÓÒÕÔ".indexOf(l) > -1)
			return "O";
		else if ("ÚÙÛ".indexOf(l) > -1)
			return "U";
		else
			return l;
	}

	/**
	 * Retorna um mapa de chaves e valores a partir de uma string com um formato específico.
	 * @param s String com os nomes de chaves e valores. Deve estar no formado <code>chave1=valor1|chave2=valor2|...chaveN=valorN</code>.
	 * @return {@link Map}
	 */
	public static Map<String, String> toMap(String s) {
		String[] kvs = s.split("\\|");
		Map<String, String> m = new HashMap<>();
		for (String kv: kvs) {
			String[] ss = kv.split("\\=");
			m.put(ss[0], ss[1]);
		}
		return m;
	}

}
