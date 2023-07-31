package toys.spring.exceptions;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class AuthOptionsException extends AuthenticationException {

  @Serial
    private static final long serialVersionUID = -5921899723988669302L;

    public AuthOptionsException(String msg, Object... params) {
        super(String.format(msg, params));
    }

    public AuthOptionsException(String msg, Throwable t, Object... params) {
        super(String.format(msg, params), t);
    }

}
