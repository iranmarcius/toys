package toys.exceptions;

import java.io.Serial;

public class ToysPersistenceException extends ToysException {

  @Serial
  private static final long serialVersionUID = 7448801432935139525L;

  public ToysPersistenceException() {
    super();
  }

  public ToysPersistenceException(String message) {
    super(message);
  }

  public ToysPersistenceException(String message, Object... params) {
    super(message, params);
  }

  public ToysPersistenceException(Throwable cause) {
    super(cause);
  }

  public ToysPersistenceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ToysPersistenceException(String message, Throwable cause, Object... params) {
    super(message, cause, params);
  }

}
