/*
 * Criado em 22/02/2012 17:46:36 
 */

package toys.application;

/**
 * Implementação default do monitor de progresso.
 * @author Iran
 */
public class DefaultProgressNotifierImpl implements ProgressNotifier {
	private int total;
	private int passo;
	private boolean cancelado;

	@Override
	public void start(String texto, int total) {
		this.total = total;
		passo = 0;
		System.out.printf("Inicializando. Total de passos: %s%n", total);
	}

	@Override
	public void step(int quantidade, String texto) {
		passo += quantidade;
		System.out.printf("Passo %d de %d%n", passo, total);
	}
	
	@Override
	public void text(String texto) {
		System.out.println(texto);
	}
	
	@Override
	public void cancel() {
		// Nenhuma implementação definida.
	}

	@Override
	public boolean isCancelled() {
		return cancelado;
	}
	
}
