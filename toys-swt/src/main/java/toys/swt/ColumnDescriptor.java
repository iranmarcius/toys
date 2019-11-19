/*
 * Criado em 29/08/2013 17:03:50 
 */

package toys.swt;

import org.eclipse.swt.SWT;

/**
 * Objeto de descrição de propriedades de colunas.
 * @author Iran
 */
public class ColumnDescriptor {
	private String id;
	private String header;
	private int width;
	private int style;
	private boolean sorteable;
	
	public ColumnDescriptor() {
		super();
	}
	
	public ColumnDescriptor(String id, String header, int width, int style, boolean sorteable) {
		this();
		this.id = id;
		this.header = header;
		this.width = width;
		this.style = style;
		this.sorteable = sorteable;
	}

	public ColumnDescriptor(String id, String header, int width, int style) {
		this(id, header, width, style, false);
	}

	public ColumnDescriptor(String id, String header, int width, boolean sorteable) {
		this(id, header, width, SWT.NONE, sorteable);
	}

	public ColumnDescriptor(String id, String header, int width) {
		this(id, header, width, SWT.NONE, false);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setStyle(int style) {
		this.style = style;
	}
	
	public int getStyle() {
		return style;
	}
	
	public void setSorteable(boolean sorteable) {
		this.sorteable = sorteable;
	}
	
	public boolean isSorteable() {
		return sorteable;
	}
	
}
