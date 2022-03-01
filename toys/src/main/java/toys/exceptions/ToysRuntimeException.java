package toys.exceptions;

import java.io.Serial;

public class ToysRuntimeException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 6761638810157534432L;

  public ToysRuntimeException() {
    super();
  }

  public ToysRuntimeException(String message) {
    super(message);
  }

  public ToysRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public ToysRuntimeException(Throwable cause) {
    super(cause);
  }

  public ToysRuntimeException(String message, Object... params) {
    this(String.format(message, params));
  }

  public ToysRuntimeException(String message, Throwable cause, Object... params) {
    this(String.format(message, params), cause);
  }

}
