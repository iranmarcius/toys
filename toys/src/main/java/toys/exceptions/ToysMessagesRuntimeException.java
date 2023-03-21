package toys.exceptions;

import toys.pojos.TextMessagePojo;

import java.io.Serial;
import java.util.List;
import java.util.stream.Collectors;

public class ToysMessagesRuntimeException extends ToysRuntimeException {

  @Serial
  private static final long serialVersionUID = 6886280887692087341L;

  private final transient List<TextMessagePojo> messages;

  public ToysMessagesRuntimeException(List<TextMessagePojo> messages) {
    super();
    this.messages = messages;
  }

  public ToysMessagesRuntimeException(String message, List<TextMessagePojo> messages) {
    super(message);
    this.messages = messages;
  }

  public ToysMessagesRuntimeException(String message, List<TextMessagePojo> messages, Throwable cause) {
    super(message, cause);
    this.messages = messages;
  }

  public ToysMessagesRuntimeException(List<TextMessagePojo> messages, Throwable cause) {
    super(cause);
    this.messages = messages;
  }

  public ToysMessagesRuntimeException(String message, List<TextMessagePojo> messages, Object... params) {
    super(message, params);
    this.messages = messages;
  }

  public ToysMessagesRuntimeException(String message, List<TextMessagePojo> messages, Throwable cause, Object... params) {
    super(message, cause, params);
    this.messages = messages;
  }

  public List<TextMessagePojo> getMessages() {
    return messages;
  }

  public String getMessagesText() {
    return messages
      .stream()
      .map(m -> String.format("%s: %s", m.getId(), m.getText()))
      .collect(Collectors.joining("\n"));
  }

}
