/*
 * Criado em 05/04/2007 16:15:06 
 */

package toys.beans;

/**
 * Esta é uma classe simples para ser utilizada em models de componentes visuais de árvores,
 * combo boxes e listas. Ela oferece um meio de armazenar o objeto de dados e o nome que se
 * deseja que seja exibido nesses componentes.
 * @author Iran
 */
public class ModelBean {
	public String name;
	public Object data;
	public boolean checked;
	
	public ModelBean(String name, Object data) {
		this.name = name;
		this.data = data;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
