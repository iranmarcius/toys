package toys.exceptions;

import toys.LDAPUtils;

public class ToysLDAPNotFoundException extends ToysLDAPException {
    private static final long serialVersionUID = -4275893555395209611L;

    public ToysLDAPNotFoundException() {
        super();
    }

    public ToysLDAPNotFoundException(String message) {
        super(message);
    }

    public ToysLDAPNotFoundException(Throwable cause) {
        super(cause);
    }

    public ToysLDAPNotFoundException(String message, Object... params) {
        super(message, params);
    }

    public ToysLDAPNotFoundException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public ToysLDAPNotFoundException(String message, LDAPUtils ldapUtils) {
        super(message, ldapUtils);
    }

    public ToysLDAPNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ToysLDAPNotFoundException(String message, LDAPUtils ldapUtils, Throwable cause) {
        super(message, ldapUtils, cause);
    }

}
