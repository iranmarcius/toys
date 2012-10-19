/*
 * Criado em 09/09/2009 09:18:38
 */

package toys.utils;

import java.util.Date;

import toys.collections.ArrayToys;

/**
 * Classe utilitária para auxiliar no processamento de argumentos.
 * @author Iran
 * @deprecated Utilizar <b>commons-cli</b>.
 */
public class CmdLineToys {
	private String[] args;

	public CmdLineToys(String[] args) {
		super();
		this.args = args;
	}

	/**
	 * Retorna se o nome de um argumento existe na lista.
	 * @param nome Nome do argumento
	 * @return <code>boolean</code>
	 */
	public boolean isParam(String nome) {
		return ArrayToys.indexOf(nome, args) > -1;
	}

	/**
	 * Retorna o valor de um parâmetro.
	 * @param nome Nome do parâmetro.
	 * @param args Array de parâmetros
	 * @return <code>String</code>
	 */
	public String getString(String nome) {
		if (args == null)
			return null;
		int i = ArrayToys.indexOf(nome, args);
		if (i < 0)
			return null;
		if (i >= args.length)
			return null;
		return args[i + 1];
	}

	/**
	 * Retorna um argumento convertido para inteiro.
	 * @param nome Nome do argumento
	 * @return {@link Integer}
	 */
	public Integer getInteger(String nome) {
		String s = getString(nome);
		return s != null ? Integer.valueOf(s) : null;
	}

	/**
	 * Retorna um argumento informado convertido para data.
	 * @param nome Nome do argumento
	 * @return {@link Date}
	 */
	public Date getDate(String nome) {
		String s = getString(nome);
		return s != null ? DateToys.formatDate(s) : null;
	}

}
