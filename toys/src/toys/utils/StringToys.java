/*
 * Todos os direitos reservados
 */

package toys.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
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
	 * Converte um nome composto cujas palavras estejam separadas por underscores,
	 * em um nome apropriado para ser utilizado de acordo com as especificações
	 * de nomes de propriedades para JavaBeans.
	 * @param name Nome composto separado por underscores
	 * @return Retorna o nome formatado como um nome de propriedade. Ex.:
	 * <b>total_coast</b> será retornado <b>totalCoast</b>, <b>tree_words_name</b>
	 * será retornado como <b>treeWordsName</b> e assim por diante.
	 */
	public static String propertyName(String name) {
		if (name != null) {
			StringBuffer n = new StringBuffer(name.toLowerCase());
			if (n.indexOf("_") > -1) {
				int i = 0;
				while (i < n.length()) {
					if (n.charAt(i) == '_') {
						n.delete(i, i + 1);
						char c = n.charAt(i);
						n.setCharAt(i, Character.toUpperCase(c));
					}
					i++;
				}
			}
			name = n.toString();
		}
		return name;
	}

	/**
	 * Retorna se a string informada está vazia ou é nula.
	 * @param s String a ser analizada
	 * @return Retorna verdadeiro no caso do parâmetro ser nulo ou
	 * conter apenas caracteres de espaço.
	 * @deprecated Será removido em favor do <b>commons-lang</b>.
	 */
	public static boolean empty(String s) {
		return (s == null) || (s.trim().length() == 0);
	}

	/**
	 * Retorna um valor default caso o valor da string informada seja nulo.
	 * @param s String a ser verificada
	 * @param defaultValue Valor default
	 * @return String
	 * @deprecated Será removido em favor do <b>commons-lang</b>.
	 */
	public static String defaultString(String s, String defaultValue) {
		return s == null ? defaultValue : s;
	}

	/**
	 * Retorna uma string vazia caso a string informada seja nula.
	 * @param s String a ser verificada
	 * @return String
	 * @deprecated Será removido em favor do <b>commons-lang</b>.
	 */
	public static String defaultString(String s) {
		return defaultString(s, "");
	}

	/**
	 * Retorna uma string abreviada de acordo com o tamanho máximo fornecido.
	 * @param s String a ser abreviada
	 * @param maxLength Tamanho máximo da string resultante
	 * @return String
	 * @deprecated Será removido em favor do <b>commons-lang</b>.
	 */
	public static String abbreviate(String s, int maxLength) {
		if (s.length() <= maxLength) {
			return s;
		}
		return s.substring(0, maxLength - 3) + "...";
	}

	/**
	 * Retorna a igualdade entre duas strings prevenindo valores nulos
	 * e desconsiderando espaços em branco no início e no final.
	 * @param s1 String 1
	 * @param s2 String 2
	 * @return <code>boolean</code>
	 * @deprecated Será removido em favor do <b>commons-lang</b>.
	 */
	public static boolean isEqual(String s1, String s2) {
		if ((s1 == null) && (s2 == null)) {
			return true;
		} else if ((s1 == null) && (s2 != null)) {
			return false;
		} else if ((s1 != null) && (s2 == null)) {
			return false;
		} else {
			return s1.trim().equals(s2.trim());
		}
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
	 * Separa uma string utilizando o separador informado mas considerando frases
	 * fechadas entre aspas como uma palavra só.
	 * @param s String a ser cortada
	 * @param separator Separador de palavras
	 * @param quote Caractere utilizado para envolver palavras
	 * @return Retorna uma lista de strings onde cada item é um
	 * @deprecated Será removido em favor do <b>commons-lang</b>.
	 */
	public static List<String> wordSplit(String s, char separator, char quote) {

		if (separator == quote)
			throw new RuntimeException("Separador de palavras e quote não podem ser iguais");

		StringBuffer word = new StringBuffer();
		int i = 0;
		boolean quoted = false;
		List<String> words = new ArrayList<String>();
		while (i < s.length()) {
			char c = s.charAt(i);
			if (c == quote) {
				quoted = !quoted;
			} else if (c == separator) {
				if (quoted) {
					word.append(c);
				} else {
					words.add(word.toString());
					word.setLength(0);
				}
			} else {
				word.append(c);
			}
			i++;
		}

		if (word.length() > 0) words.add(word.toString());

		return words;
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
	 * Separa uma string retornando um array com todas as palavras, grupos de espaços, tabulações
	 * e separadores de linha.
	 * @param str String a ser processada
	 * @return <code>String[]</code>
	 */
	public static String[] split(String str) {
		List<String> l = new ArrayList<String>();
		int i = 0;
		char ca = 0;
		StringBuffer sb = new StringBuffer();
		while (i < str.length()) {
			char c = str.charAt(i);

			if (c == ' ') {
				if (ca == ' ') {
					sb.append(c);
				} else {
					if (sb.length() > 0) {
						l.add(sb.toString());
						sb.setLength(0);
					}
					sb.append(c);
				}
			} else {
				if ((c == '\t') || (c == '\n')) {
					if (sb.length() > 0) {
						l.add(sb.toString());
						sb.setLength(0);
					}
					l.add(Character.toString(c));
				} else {
					if (ca == ' ') {
						l.add(sb.toString());
						sb.setLength(0);
					}
					sb.append(c);
				}
			}

			ca = c;
			i++;
		}

		// caso ainda haja alguma palavra não adicionada à lista, adiciona agora
		if (sb.length() > 0)
			l.add(sb.toString());

		String[] palavras = new String[l.size()];
		for (i = 0; i < palavras.length; i++)
			palavras[i] = l.get(i);
		return palavras;
	}

	/**
	 * Formata uma string utilizando o padrão e os valores passados. Caso algum dos valores
	 * seja nulo, retorna o valor default.
	 * @param pattern Padrão para a formatação
	 * @param defaultValue Valor padrão a ser retornado caso algum dos valores seja nulo
	 * @param valores Valores a serem utilizados na formatação
	 * @return <code>String</code>
	 */
	public static String format(String pattern, String defaultValue, Object... valores) {
		for (Object o: valores)
			if (o == null)
				return defaultValue;
		return String.format(pattern, valores);
	}

	/**
	 * Formata uma string utilizando o padrão e os valores passados. Caso algum dos valores
	 * seja nulo, retorna uma string vazia.
	 * @param pattern Padrão para a formatação
	 * @param valores Valores a serem utilizados na formatação
	 * @return <code>String</code>
	 * @see #format(String, String, Object...)
	 */
	public static String format(String pattern, Object... valores) {
		return format(pattern, "", valores);
	}

	/**
	 * Este método funciona como o método {@link String#substring(int, int)} exceto pelo fato de que
	 * caso a porção desejada esteja além do tamanho da string original, será retornado o valor padrão.
	 * @param s String original
	 * @param i Posição inicial
	 * @param f Posição final
	 * @param padrao Valor qeu será retornado em caso de estouro
	 * @return <code>String</code>
	 */
	public static String substring(String s, int i, int f, String padrao) {
		return f <= s.length() ? s.substring(i, f) : padrao;
	}

	/**
	 * Este método funciona como o método {@link String#substring(int)} exceto pelo fato de que
	 * caso a porção desejada esteja além do tamanho da string original, será retornado o valor padrão.
	 * @param s String original
	 * @param i Posição inicial
	 * @param padrao Valor qeu será retornado em caso de estouro
	 * @return <code>String</code>
	 */
	public static String substring(String s, int i, String padrao) {
		return i <= s.length() ? s.substring(i) : padrao;
	}

	/**
	 * Método de conveniência para invocar o {@link #substring(String, int, int, String)} utilizando
	 * um valro padrão vazio.
	 */
	public static String substring(String s, int i, int f) {
		return substring(s, i, f, "");
	}

	/**
	 * Método de conveniência para invocar o {@link #substring(String, int, String)} com um valor padrão
	 * vazio.
	 */
	public static String substring(String s, int i) {
		return substring(s, i, "");
	}

	/**
	 * Retorna uma nova string com n caracteres a partir do lado direito da string original.
	 * @param s String original.
	 * @param i Número de caracteres que serão considerados a partir da direita.
	 * @return <code>String</code>
	 */
	public static String right(String s, int i) {
		return s.substring(s.length() - i);
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
	 * Retorna o valor de um atributo de uma string cujo conteúdo esteja no formado XML.
	 * @param tag Tag XML.
	 * @param attr Nome do atributo.
	 * @return <code>String</code>
	 */
	public static String getTagAttribute(String tag, String attr) {
		int i = tag.indexOf(" " + attr + "=");
		if (i < 0)
			return null;
		String value = tag.substring(i + attr.length() + 2);
		if (value.charAt(0) == '"' || value.charAt(0) == '\'') {
			char quote = value.charAt(0);
			value = value.substring(1);
			if ((i = value.indexOf(quote)) < 0)
				i = value.length();
		} else {
			if ((i = value.indexOf(" ")) < 0)
				if ((i = value.indexOf("/")) < 0)
					if ((i = value.indexOf(">")) < 0)
						i = value.length();
		}
		return value.substring(0, i);
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

}
