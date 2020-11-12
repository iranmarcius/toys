package toys.application;

import toys.enums.Level;

public class ProgressMessage {
    private int total;
    private int current;
    private String text;
    private Level level;

    public ProgressMessage() {
    }

    public ProgressMessage(int total, int current, String text, Level level) {
        this.total = total;
        this.current = current;
        this.text = text;
        this.level = level;
    }

    public ProgressMessage(int total, int current, String text) {
        this(total, current, text, null);
    }

    public ProgressMessage(int total, int current) {
        this(total, current, null);
    }

    public ProgressMessage(String text) {
        this(0, 0, text, null);
    }

    public ProgressMessage(ProgressNotifier notifier) {
        this(
            notifier.getTotal(),
            notifier.getCurrent(),
            notifier.getText(),
            notifier.getLevel()
        );
    }

    @Override
    public String toString() {
        return String.format("ProgressMessage [total=%s, current=%s, text=%s, level=%s]",
            total, current, text, level);
    }

    public int getTotal() {
        return total;
    }

    public int getCurrent() {
        return current;
    }

    public String getText() {
        return text;
    }

    public Level getLevel() {
        return level;
    }

    public int getPercent() {
        return (int) (((double) current / (double) total) * 100);
    }

}
