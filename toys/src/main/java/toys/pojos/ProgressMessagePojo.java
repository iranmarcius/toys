package toys.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import toys.application.ProgressNotifier;
import toys.enums.Level;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ProgressMessagePojo extends TextMessagePojo {
  private int total;
  private int current;

  public ProgressMessagePojo(String id, String text, Level level, int total, int current) {
    super(id, text, level);
    this.total = total;
    this.current = current;
  }

  public ProgressMessagePojo(String id, String text, int total, int current) {
    this(id, text, null, total, current);
  }

  public ProgressMessagePojo(String text, int total, int current) {
    this(null, text, null, total, current);
  }

  public ProgressMessagePojo(int total, int current) {
    this(null, null, null, total, current);
  }

  public ProgressMessagePojo(String text) {
    this(null, text, null, -1, -1);
  }

  public ProgressMessagePojo(ProgressNotifier notifier) {
    this(
      notifier.getPid(),
      notifier.getText(),
      notifier.getLevel(),
      notifier.getTotal(),
      notifier.getCurrent()
    );
  }
}
