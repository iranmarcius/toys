package toys.swing.threads;

/**
 * Esta interface define um método para ser utilizado em janelas de aplicações
 * GUI que necessitem de atualização periódica dos seus componentes.
 * @author Iran Marcius
 */
public interface ComponentUpdate {
	
	/**
	 * Faz a atualização dos componentes.
	 */
	void updateComponents();

}
