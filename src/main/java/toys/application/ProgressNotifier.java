/*
 * Criado em 22/02/2012 17:42:45 
 */

package toys.application;

/**
 * Esta interface define as implementações necessárias para classes de processos
 * com monitoramento de progresso externo.
 * @author Iran
 */
public interface ProgressNotifier {
	
	/**
	 * Inicializa o monitor de progresso com o texto e o total de passos.
	 * @param texto Texto para ser apresentado.
	 * @param total total de passos que serão executados no processo.
	 */
	void start(String texto, int total);
	
	/**
	 * Notifica a execução de um passo.
	 * @param quantidade Quantidade de passos.
	 * @param texto Texto opcional para ser exibido.
	 */
	void step(int quantidade, String texto);
	
	/**
	 * Envia um texto a ser exibido pelo monitor de progresso. 
	 * @param texto Texto.
	 */
	void text(String texto);
	
	/**
	 * Envia uma notificação de cancelamento para o monitor de progresso.
	 */
	void cancel();
	
	/**
	 * Retorna se o processo foi cancelado.
	 * @return <code>boolean</code>
	 */
	boolean isCancelled();
	
}
