package toys.exceptions;

public class ToysException extends Exception {
    private static final long serialVersionUID = 7761049067130802158L;

    public ToysException() {
        super();
    }

    public ToysException(String message) {
        super(message);
    }

    public ToysException(String message, Throwable cause) {
        super(message, cause);
    }

    public ToysException(Throwable cause) {
        super(cause);
    }

    public ToysException(String message, Object... params) {
        this(String.format(message, params));
    }

    public ToysException(String message, Throwable cause, Object... params) {
        this(String.format(message, params), cause);
    }

}
