package toys.pojos;

import lombok.*;
import toys.enums.Level;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TextMessagePojo {
  private String id;
  private String text;
  private Level level;

  public TextMessagePojo(String text, Level level) {
    this(null, text, level);
  }

  public TextMessagePojo(String text) {
    this(null, text, null);
  }

}
