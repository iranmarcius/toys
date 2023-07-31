package toys.spring.exceptions;

import java.io.Serial;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenStatusException extends ResponseStatusException {

  @Serial
  private static final long serialVersionUID = 1565394866216648331L;

  public ForbiddenStatusException() {
    super(HttpStatus.FORBIDDEN);
  }

  public ForbiddenStatusException(String msg, Object... params) {
    super(HttpStatus.FORBIDDEN, String.format(msg, params));
  }

}
