package toys.spring.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthOptionsException extends AuthenticationException {
    private static final long serialVersionUID = -5921899723988669302L;

    public AuthOptionsException(String msg, Object... params) {
        super(String.format(msg, params));
    }

    public AuthOptionsException(String msg, Throwable t, Object... params) {
        super(String.format(msg, params), t);
    }

}
