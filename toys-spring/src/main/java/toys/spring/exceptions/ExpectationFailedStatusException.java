package toys.spring.exceptions;

import java.io.Serial;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExpectationFailedStatusException extends ResponseStatusException {

  @Serial
  private static final long serialVersionUID = 1565394866216648331L;

  public ExpectationFailedStatusException() {
    super(HttpStatus.EXPECTATION_FAILED);
  }

  public ExpectationFailedStatusException(String msg, Object... params) {
    super(HttpStatus.EXPECTATION_FAILED, String.format(msg, params));
  }

}
