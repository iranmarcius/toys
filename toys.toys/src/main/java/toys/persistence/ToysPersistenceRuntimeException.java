package toys.persistence;

public class ToysPersistenceRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 3226517714564973790L;

    public ToysPersistenceRuntimeException() {
        super();
    }

    public ToysPersistenceRuntimeException(String message) {
        super(message);
    }

    public ToysPersistenceRuntimeException(Throwable cause) {
        super(cause);
    }

    public ToysPersistenceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
