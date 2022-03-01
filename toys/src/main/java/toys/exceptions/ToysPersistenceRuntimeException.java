package toys.exceptions;

import java.io.Serial;

public class ToysPersistenceRuntimeException extends ToysRuntimeException {

  @Serial
  private static final long serialVersionUID = 3226517714564973790L;

  public ToysPersistenceRuntimeException() {
    super();
  }

  public ToysPersistenceRuntimeException(String message) {
    super(message);
  }

  public ToysPersistenceRuntimeException(String message, Object... params) {
    super(message, params);
  }

  public ToysPersistenceRuntimeException(Throwable cause) {
    super(cause);
  }

  public ToysPersistenceRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public ToysPersistenceRuntimeException(String message, Throwable cause, Object... params) {
    super(message, cause, params);
  }

}
