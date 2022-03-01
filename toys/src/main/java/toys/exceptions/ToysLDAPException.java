package toys.exceptions;

import toys.LDAPUtils;

import java.io.Serial;

public class ToysLDAPException extends ToysException {

  @Serial
  private static final long serialVersionUID = 1610066187127484595L;

  public ToysLDAPException() {
  }

  public ToysLDAPException(String message) {
    super(message);
  }

  public ToysLDAPException(Throwable cause) {
    super(cause);
  }

  public ToysLDAPException(String message, Object... params) {
    super(message, params);
  }

  public ToysLDAPException(String message, Throwable cause, Object... params) {
    super(message, cause, params);
  }

  public ToysLDAPException(String message, LDAPUtils ldapUtils) {
    this("%s %s", message, ldapUtils);
  }

  public ToysLDAPException(String message, Throwable cause) {
    super(message, cause);
  }

  public ToysLDAPException(String message, LDAPUtils ldapUtils, Throwable cause) {
    this("%s %s", message, cause, ldapUtils);
  }

}
