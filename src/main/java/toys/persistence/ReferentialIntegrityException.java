package toys.persistence;

/**
 * Exception para utilização com erros de integridade referencial.
 * @author Iran Marcius
 */
public class ReferentialIntegrityException extends Exception {
	private static final long serialVersionUID = 3257281444152752180L;

	public ReferentialIntegrityException() {
		super();
	}
	
	public ReferentialIntegrityException(String s) {
		super(s);
	}
	
	public ReferentialIntegrityException(Exception ex) {
		super(ex.getMessage());
		setStackTrace(ex.getStackTrace());
	}
	
	public ReferentialIntegrityException(String s, Exception ex) {
		super(s);
		setStackTrace(ex.getStackTrace());
	}
}
