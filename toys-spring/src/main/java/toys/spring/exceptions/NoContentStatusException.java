package toys.spring.exceptions;

import java.io.Serial;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoContentStatusException extends ResponseStatusException {

  @Serial
  private static final long serialVersionUID = 1565394866216648331L;

  public NoContentStatusException() {
    super(HttpStatus.NO_CONTENT);
  }

  public NoContentStatusException(String msg, Object... params) {
    super(HttpStatus.NO_CONTENT, String.format(msg, params));
  }

}
