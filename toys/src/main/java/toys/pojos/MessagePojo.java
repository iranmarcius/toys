package toys.pojos;

import lombok.*;
import toys.enums.Level;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessagePojo {
  private String id;
  private String text;
  private Level level;

  public MessagePojo(String text, Level level) {
    this(null, text, level);
  }

  public MessagePojo(String text) {
    this(null, text, null);
  }

}
