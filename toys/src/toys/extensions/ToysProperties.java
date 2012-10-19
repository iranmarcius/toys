package toys.extensions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import toys.beans.pairs.StringPair;
import toys.utils.DateToys;

/**
 * Esta classe é uma espécie de estensão da classe {@link Properties} que adiciona diversas
 * funcionalidades não existentes.
 * @author Iran Marcius
 * @deprecated Utilizar o <b>commons-config</b>.
 */
public class ToysProperties extends Properties {
	private static final long serialVersionUID = -3280422816353054662L;
	private static final Log log = LogFactory.getLog(ToysProperties.class);

	public ToysProperties() {
		super();
	}

	public ToysProperties(InputStream in) {
		super();
		try {
			load(in);
		} catch (IOException e) {
			log.error("Erro lendo stream informacoes de propriedades.", e);
		}
	}

	public ToysProperties(String caminho)  {
		super();
		try {
			load(new FileInputStream(caminho));
		} catch (IOException e) {
			log.error(String.format("Erro lendo arquivo %s com as propriedades.", caminho), e);
		}
	}

	/**
	 * Retorna o valor de uma propriedade cujo nome é composto do nome da classe seguido por
	 * um ponto e o sufixo informados.
	 * @param clazz Classe para obtenção do nome
	 * @param sufixo Sufixo a ser concatenado ao nome da classe para formar o nome completo da propriedade
	 * @return <code>String</code>
	 */
	public String getProperty(Class<?> clazz, String sufixo) {
		return getProperty(clazz.getName() + "." + sufixo);
	}

	/**
	 * Este método funciona como o {@link #getProperty(Class, String)}, mas retornando um valor
	 * padrão caso a propriedade informada não exista.
	 */
	public String getProperty(Class<?> clazz, String sufixo, String padrao) {
		return getProperty(clazz.getName() + "." + sufixo, padrao);
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>int</code>.
	 * @param nome Nome da propriedade
	 * @return <code>int</code>
	 */
	public int getInt(String nome) {
		return Integer.parseInt(getProperty(nome));
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>int</code>.
	 * Caso ocorra algum erro na conversão, retorna o valor padrão.
	 * @param nome Nome da propriedade
	 * @param padrao Valor padrão
	 * @return <code>int</code>
	 */
	public int getInt(String nome, int padrao) {
		try {
			return Integer.parseInt(getProperty(nome));
		} catch (Exception e) {
			return padrao;
		}
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>double</code>.
	 * @param nome Nome da propriedade
	 * @return <code>double</code>
	 */
	public double getDouble(String nome) {
		return Double.parseDouble(getProperty(nome));
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>double</code>.
	 * @param nome Nome da propriedade
	 * @param padrao Valor padrão caso ocorra algum erro na conversão.
	 * @return <code>double</code>
	 */
	public double getDouble(String nome, int padrao) {
		try {
			return Double.parseDouble(getProperty(nome));
		} catch (Exception e) {
			return padrao;
		}
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>long</code>.
	 * @param nome Nome da propriedade.
	 * @return <code>long</code>
	 */
	public long getLong(String nome) {
		return Long.parseLong(getProperty(nome));
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>long</code>.
	 * @param nome Nome da propriedade.
	 * @param padrao Valor padrão caso ocorra algum erro na conversão.
	 * @return <code>long</code>
	 */
	public long getLong(String nome, long padrao) {
		try {
			return Long.parseLong(getProperty(nome));
		} catch (Exception ex) {
			return padrao;
		}
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>boolean</code>.
	 * @param nome Nome da propriedade
	 * @return <code>boolean</code>
	 */
	public boolean getBoolean(String nome) {
		return Boolean.parseBoolean(getProperty(nome));
	}

	/**
	 * Retorna o valor de uma propriedade convertido para <code>boolean</code>.
	 * @param nome Nome da propriedade.
	 * @param padrao Valor padrão caso ocorra algum erro na conversão.
	 * @return <code>boolean</code>
	 */
	public boolean getBoolean(String nome, boolean padrao) {
		try {
			return Boolean.parseBoolean(getProperty(nome));
		} catch (Exception e) {
			return padrao;
		}
	}

	/**
	 * Retorna uma representação de tempo no formado string convertido para um valor long. Pra que
	 * a conversão seja feita corretamente, o valor da propriedade deverá seguir as especificações
	 * do método {@link DateToys#timeStr2ms(String)}.
	 * @param nome Nome da propriedade
	 * @return <code>long</code>
	 */
	public long getTime(String nome) {
		return DateToys.timeStr2ms(getProperty(nome));
	}

	/**
	 * Método de conveniência para invocar o {@link #getTime(String)} retornando um valor padrão
	 * caso ocorra algum erro.
	 * @param nome Nome da propriedade
	 * @param padrao Valor padrão
	 */
	public long getTime(String nome, long padrao) {
		try {
			return getTime(nome);
		} catch (Exception ex) {
			return padrao;
		}
	}

	/**
	 * Retorna uma lista de strings criada à partir do valor da propriedade especificada.
	 * O valor da propriedade deverá ser uma série de strings separadas por vírgula.
	 * @param nome Nome da propriedade.
	 * @return <code>List&lt;String&gt;</code>
	 */
	public List<String> getList(String nome) {
		String s = getProperty(nome);
		String[] itens = s.split(", *");
		return Arrays.asList(itens);
	}

	/**
	 * Retorna uma lista de inteiros construída a partir do valor de uma propriedade.
	 * O valor da deve ser uma seqüência de números separados por vírgulas. Ex.: <i>1, 2, 3</i>.
	 * Pode-se também especificar intervalos de valores da seguinte forma: <i>1..10, 11, 12, 13..15</i>.
	 * @param nome Nome da propriedade.
	 * @return <code>List&lt;Integer&gt;</code>
	 */
	public List<Integer> getIntList(String nome) {
		String s = getProperty(nome);
		List<Integer> l = new ArrayList<Integer>();
		String[] itens = s.split(" *\\, *");
		for (String item: itens) {
			int p = item.indexOf("..");
			if (p > -1) {
				int i = Integer.parseInt(item.substring(0, p));
				int f = Integer.parseInt(item.substring(p + 2));
				for (int j = i; j <= f; j++) {
					p = Collections.binarySearch(l, j);
					if (p < 0) l.add((p * -1) - 1, j);
				}
			} else {
				Integer i = Integer.valueOf(item);
				p = Collections.binarySearch(l, i);
				if (p < 0) l.add((p * -1) - 1, i);
			}
		}
		return l;
	}

	/**
	 * Retorna um objeto {@link Properties} criado a partir do valor de uma propriedade.
	 * Para que este método funcione corretamente, o valor da propriedade deverá estar no seguinte:
	 * <i>propriedade=key1=value1 | key2=value2 | ... | keyN=valueN</i>
	 * @param nome Nome da propriedade de onde os valores serão lidos
	 * @return <code>{@link Properties}</code>
	 */
	public Properties getProperties(String nome) {
		Properties p = new Properties();
		String[] ss = getProperty(nome).split(" *\\| *");
		for (String s: ss) {
			int i = s.indexOf('=');
			p.setProperty(s.substring(0, i).trim(), s.substring(i + 1).trim());
		}
		return p;
	}

	/**
	 * Este método funciona da mesma forma que o {@link #getProperties(String)},
	 * exceto que retornará uma lista de objetos do tipo {@link StringPair}.
	 * @param nome Nome da propriedade
	 * @return <code>List&lt;{@link StringPair}&gt;</code>
	 */
	public List<StringPair> getStringPairList(String nome) {
		String[] ss = getProperty(nome).split(" *\\| *");
		List<StringPair> l = new ArrayList<StringPair>();
		for (String s: ss) {
			int i = s.indexOf('=');
			l.add(new StringPair(s.substring(0, i).trim(), s.substring(i + 1).trim()));
		}
		return l;
	}

	/**
	 * Retorna um objeto do tipo {@link Map} a partir do valor de uma propriedade.
	 * @param nome Nome da propriedade
	 * @return <code>{@link Map}&lt;String, String&gt;</code>
	 */
	public Map<String, String> getMap(String nome) {
		Properties p = getProperties(nome);
		SortedMap<String, String> m = new TreeMap<String, String>();
		for (Entry<Object, Object> entry: p.entrySet())
			m.put(entry.getKey().toString(), entry.getValue().toString());
		return m;
	}

	/**
	 * Retorna um objeto do tipo {@link Date}. O valor do parâmetro armazenado. O valor do parâmetro
	 * armazenado pode ser do próprio tipo {@link Date} ou {@link Calendar}.
	 * @param nome Nome da propriedade
	 * @return {@link Date}
	 */
	public Date getDate(String nome) {
		Object o = get(nome);
		if (o == null)
			return null;
		if (o instanceof Date)
			return (Date)o;
		if (o instanceof Calendar)
			return ((Calendar)o).getTime();
		return null;
	}

}
