/*
 * Criado em 10/12/2009 14:31:46
 */

package toys.swt.runnables;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Esta classe pode ser utilizada dentro de threads que necessitem exibir mensagens.
 * @author Iran
 */
public class MessageRunnable implements Runnable {
	public enum Tipo {INFORMACAO, ERRO, AVISO, CONFIRMACAO, QUESTAO}

    private Shell shell;
	private Display display;
	private Tipo tipo;
	private Object[] parametros;
	private boolean resultado;

	/**
	 * Cria uma instância da classe setando a referência para o shell.
	 * @param shell
	 */
	public MessageRunnable(Shell shell) {
		super();
		this.shell = shell;
		display = shell.getDisplay();
	}

	/**
	 * Exibe uma mensagem do tipo especificado com os parâmetros informados.
	 * @return Retorna o botão pressionado
	 */
	private boolean mensagem(Tipo tipo, Object... parametros) {
		this.tipo = tipo;
		this.parametros = parametros;
		resultado = false;
		display.syncExec(this);
		return resultado;
	}

	/**
	 * Exibe uma janela de informação.
	 * @param titulo Título da janela.
	 * @param mensagem Mensagem
	 */
	public void informacao(String titulo, String mensagem) {
		mensagem(Tipo.INFORMACAO, titulo, mensagem);
	}

	/**
	 * Exibe uma janela com uma mensagem de erro.
	 * @param titulo Título da janela
	 * @param mensagem mensagem
	 */
	public void erro(String titulo, String mensagem) {
		mensagem(Tipo.ERRO, titulo, mensagem);
	}

	/**
	 * Exibe uma janela com uma mensagem de aviso.
	 * @param titulo Título da janela
	 * @param mensagem mensagem
	 */
	public void aviso(String titulo, String mensagem) {
		mensagem(Tipo.AVISO, titulo, mensagem);
	}

	/**
	 * Exibe uma janela com uma mensagem de confirmação.
	 * @param titulo Título da janela
	 * @param mensagem mensagem
	 * @return Retorna o índice do botão pressionado.
	 */
	public boolean confirmacao(String titulo, String mensagem) {
		return mensagem(Tipo.CONFIRMACAO, titulo, mensagem);
	}

	/**
	 * Exibe uma janela com uma pergunta.
	 * @param titulo Título da janela
	 * @param mensagem mensagem
	 * @return Retorna o índice do botão pressionado.
	 */
	public boolean questao(String titulo, String mensagem) {
		return mensagem(Tipo.QUESTAO, titulo, mensagem);
	}

	@Override
	public void run() {
		switch (tipo) {
		case INFORMACAO:
			MessageDialog.openInformation(shell, (String)parametros[0], (String)parametros[1]);
			break;

		case ERRO:
			MessageDialog.openError(shell, (String)parametros[0], (String)parametros[1]);
			break;

		case AVISO:
			MessageDialog.openWarning(shell, (String)parametros[0], (String)parametros[1]);
			break;

		case CONFIRMACAO:
			resultado = MessageDialog.openConfirm(shell, (String)parametros[0], (String)parametros[1]);
			break;

		case QUESTAO:
			resultado = MessageDialog.openQuestion(shell, (String)parametros[0], (String)parametros[1]);
			break;

		}
	}

}
