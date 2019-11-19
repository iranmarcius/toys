package toys.swing.models;

import javax.swing.*;
import java.util.List;

/**
 * Implementação básica para criação de ListModels.
 * @author Iran
 */
@SuppressWarnings("rawtypes")
public class BaseListModel extends AbstractListModel {
	private static final long serialVersionUID = 3226196433717534135L;

	private List<?> data;
	
	public BaseListModel() {
		super();
	}
	
	public BaseListModel(List<?> data) {
		this();
		this.data = data;
	}

	public Object getElementAt(int index) {
		return data != null ? data.get(index) : null;
	}

	public int getSize() {
		return data != null ? data.size() : 0;
	}

}
