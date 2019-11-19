package toys.swing.utils;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Métodos utilitários que utilizam a classe <i>JOptionPane</i>.
 * @author Iran Marcius
 */
public class OPUtils {
	private static final ResourceBundle res = ResourceBundle.getBundle(OPUtils.class.getName());

	/**
	 * Exibe uma mensagem de erro.
	 * @param parent Componente pai
	 * @param message Mensagem a ser exibida
	 */
	public static void showError(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, res.getString("title.error"),
			JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Exibe uma mensagem de aviso.
	 * @param parent Componente pai
	 * @param message Mensagem a ser exibida
	 */
	public static void showWarning(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, res.getString("title.warning"),
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Exibe uma mensagem de informação.
	 * @param parent Componente pai
	 * @param message Mensagem a ser exibida
	 */
	public static void showInformation(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, res.getString("title.information"),
			JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Exibe uma janela de confirmação com os botões Sim e Não.
	 * @param parent Componente pai
	 * @param message Mensagem a ser exibida
	 * @return Retorna TRUE se o botão <b>Sim</b> for pressionado, ou FALSE se o
	 * botão <b>Não</b> for pressionado
	 */
	public static boolean showYesNoConfirmation(Component parent, String message) {
		int r = JOptionPane.showConfirmDialog(parent, message,
			res.getString("title.confirmation"), JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE);
		return r == 0;
	}

}
