/*
 * Criado em 17/03/2008 11:37:49
 */

package toys.beans.pairs;


/**
 * Classe para armazenamento de pares de valores do tipo <code>String</code>. Esta classe já
 * está prepadada para funcionar com a API <code>XStream</code>.
 * @author Iran
 */
public class StringPair {

	/**
	 * Chave
	 */
	public String key;

	/**
	 * Valor
	 */
	public String value;

	/**
	 * Construtor default.
	 */
	public StringPair() {
		super();
	}

	/**
	 * Cria uma instância desta classe atribuindo os valores de chave e valor.
	 * @param key Chave
	 * @param value Valor
	 */
	public StringPair(String key, String value) {
		this();
		this.key = key;
		this.value = value;
	}

	/*
	 * Acessors
	 */

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
