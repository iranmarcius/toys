package toys.application;

import toys.enums.Level;

/**
 * Implementação default do monitor de progresso.
 *
 * @author Iran
 * @since 02/2012
 */
public class DefaultProgressNotifier implements ProgressNotifier {
  private String pid;
  private int total;
  private int current;
  private String text;
  private Level level;
  private boolean cancelled;

  public DefaultProgressNotifier() {
  }

  public DefaultProgressNotifier(String pid, int total) {
    this.pid = pid;
    this.total = total;
  }

  public DefaultProgressNotifier(int total) {
    this(null, total);
  }

  @Override
  public void start(int total, String text, Object... params) {
    this.total = total;
    start(text, params);
  }

  @Override
  public void start(String text, Object... params) {
    current = 0;
    text(text, Level.INFO, params);
  }

  @Override
  public void start(int total) {
    start(total, null);
  }

  @Override
  public void finish(String text, Object... params) {
    current = total;
    text(text, Level.INFO, params);
  }

  @Override
  public void finish() {
    finish(null);
  }

  @Override
  public void step(int amount, String text, Level level, Object... params) {
    current += amount;
    text(text, level, params);
  }

  @Override
  public void step(int amount, String text, Object... params) {
    step(amount, text, null, params);
  }

  @Override
  public void text(String text, Level level, Object... params) {
    this.text = params != null && params.length > 0 ? String.format(text, params) : text;
    this.level = level;
    onEvent(this);
  }

  @Override
  public void text(String text, Object... params) {
    text(text, null, params);
  }

  @Override
  public void cancel() {
    cancelled = true;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public int getPercent() {
    return (int) (((double) current / (double) total) * 100);
  }

  @Override
  public int getTotal() {
    return total;
  }

  @Override
  public int getCurrent() {
    return current;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public Level getLevel() {
    return level;
  }

  @Override
  public String getPid() {
    return pid;
  }

  @Override
  public void onEvent(ProgressNotifier notifier) {
    // Nada ocorre aqui
  }

}
