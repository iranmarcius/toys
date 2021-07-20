package toys.spring.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class NoContentStatusException extends ResponseStatusException {

  @Serial
  private static final long serialVersionUID = 1565394866216648331L;

  public NoContentStatusException() {
    super(HttpStatus.NO_CONTENT);
  }

}
