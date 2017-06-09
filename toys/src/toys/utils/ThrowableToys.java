/*
 * Criado em 29/09/2010 15:07:20
 */

package toys.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import toys.StringPair;

/**
 * Esta classe possui uma lista de chaves e mensagens de erro associadas a essa chaves, e pode retornar uma mensagem
 * de erro específica caso o conteúdo de um erro (throwable) possua alguma das palavras chaves da lista.
 * @author Iran
 */
public class ThrowableToys {
	private List<StringPair> erros;

	public ThrowableToys() {
		super();
		erros = new ArrayList<StringPair>();
	}

	public ThrowableToys(StringPair... erros) {
		this();
		this.erros.addAll(Arrays.asList(erros));
	}

	/**
	 * Adiciona uma palavra-chave e uma mensagem associada à lista de mensagens.
	 * @param chave Palavra-chave
	 * @param mensagem Mensagem
	 * @return Retorna a própxia instância deste objeto.
	 */
	public ThrowableToys add(String chave, String mensagem) {
		erros.add(new StringPair(chave, mensagem));
		return this;
	}

	/**
	 * Adiciona uma palavra-chave a partir do nome da classe e uma mensagem correspondente.
	 * @param t Classe do erro.
	 * @param mensagem Mensagem.
	 * @return Retorna a própxia instância deste objeto.
	 */
	public ThrowableToys add(Class<? extends Throwable> t, String mensagem) {
		return add(t.getName(), mensagem);
	}

	/**
	 * Procura uma chaves dos erros dentro da mensagem de erro e, caso seja encontrada,
	 * retorna a mensagem associada.
	 * @param t Erro
	 * @return <code>String</code>
	 */
	public String getErro(Throwable t) {
		for (StringPair p: erros) {

			// verifica se a classe do erro possui uma mensagem específica
			if (t.getClass().getName().equals(p.getKey()))
				return p.getValue();

			// verifica se a palavra-chave consta na string da mensagem do erro
			else if (t.getMessage() != null && t.getMessage().indexOf(p.getKey()) > -1)
				return p.getValue();

			// verifica se a palavra-chave consta na string da mensagem da causa do erro
			else if (t.getCause() != null && t.getCause().getMessage().indexOf(p.getKey()) > -1)
				return p.getValue();
		}

		return null;
	}

}
