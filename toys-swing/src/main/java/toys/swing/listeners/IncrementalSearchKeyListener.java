package toys.swing.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Classe abstrata com implementações básicas para realizar de buscas incrementais em
 * componentes visuais com listas.
 * @author Iran Marcius
 */
public abstract class IncrementalSearchKeyListener extends KeyAdapter {
	
	/**
	 * Milisegundos representando o momento da digitação da tecla
	 */
	protected long lastType;
	
	/**
	 * Expressão a ser pesquisada na lista.
	 */
	protected StringBuffer searchExpr;
	
	/**
	 * Referência para a lista de dados que será pesquisada.
	 */
	protected List<?> data;
	
	/**
	 * Construtor com inicialização de propriedades.
	 * @param data Lista de dados
	 */
	public IncrementalSearchKeyListener(List<?> data) {
		super();
		searchExpr = new StringBuffer();
		setData(data);
	}
	
	/**
	 * Intercepta a tecla pressionada para realizar a busca incremental.
	 * @param event Evento de teclado
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		
		// verifica o tempo decorrido deste a última tecla pressionada 
		long now = System.currentTimeMillis();
		if (now - lastType > 2000) searchExpr.setLength(0);
		
		// processa apenas se for um caracter "interceptível"
		if (Character.isLetterOrDigit(event.getKeyChar()) ||
			Character.isSpaceChar(event.getKeyChar())) {
		
			// acrescenta o caractér à expressão de busca e determina o índice na lista
			searchExpr.append(event.getKeyChar());
			int i = findIndex(searchExpr.toString());
			if (i > -1) doUpdateComponent(event, i);
			event.consume();
		} else {
			searchExpr.setLength(0);
		}

		// atualiza o momento
		lastType = now;
	}
	
	/**
	 * Atualiza a posição do item no componente
	 * @param event Evento de teclado
	 * @param index Índice
	 */
	protected abstract void doUpdateComponent(KeyEvent event, int index);
	
	/**
	 * Retorna o índice do primeiro item iniciado com a expressão de pesquisa  
	 */
	protected abstract int findIndex(String expr);
	
	/**
	 * Seta a referência para a lista de dados.
	 * @param data Lista de dados
	 */
	public void setData(List<?> data) {
		this.data = data;
	}

}
