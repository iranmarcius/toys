package toys.exceptions;

public class ToysNonUniqueRuntimeException extends ToysRuntimeException {

  public ToysNonUniqueRuntimeException() {
    super();
  }

  public ToysNonUniqueRuntimeException(String message) {
    super(message);
  }

  public ToysNonUniqueRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public ToysNonUniqueRuntimeException(Throwable cause) {
    super(cause);
  }

  public ToysNonUniqueRuntimeException(String message, Object... params) {
    super(message, params);
  }

  public ToysNonUniqueRuntimeException(String message, Throwable cause, Object... params) {
    super(message, cause, params);
  }

}
