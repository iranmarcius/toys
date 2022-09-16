/*
 * Criado em 22/02/2012 17:42:45
 */

package toys.application;

import toys.enums.Level;

/**
 * Esta interface define as implementações comuns para monitoramente.
 *
 * @author Iran
 */
public interface ProgressNotifier {
  void start(int total, String text, Object... params);

  void start(String text, Object... params);

  void start(int total);

  void finish();

  void finish(String text, Object... params);

  void step(int ammount, String text, Level level, Object... params);

  void step(int ammount, String text, Object... params);

  void text(String text, Level level, Object... params);

  void text(String text, Object... params);

  void cancel();

  boolean isCancelled();

  int getTotal();

  int getCurrent();

  int getPercent();

  String getText();

  Level getLevel();

  String getPid();

  void onEvent(ProgressNotifier notifier);
}
