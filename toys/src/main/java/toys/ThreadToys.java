/*
 * Criado em 21/01/2010 11:08:46
 */

package toys;


/**
 * Esta classe implementa métodos utilitários para realizar operações envolvendo threads.
 * @author Iran
 */
public class ThreadToys {

	/**
	 * Retorna um array populado com todas as threads ativas.
	 * @return <code>{@link Thread}[]</code>
	 */
	public static Thread[] getThreadsAtivas() {
		Thread[] t = new Thread[Thread.activeCount()];
		Thread.enumerate(t);
		return t;
	}

}
