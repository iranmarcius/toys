package toys.application;

import toys.enums.Level;

public class ProgressMessage {
    private String pid;
    private int total;
    private int current;
    private String text;
    private Level level;

    public ProgressMessage() {
    }

    public ProgressMessage(String pid, int total, int current, String text, Level level) {
        this.pid = pid;
        this.total = total;
        this.current = current;
        this.text = text;
        this.level = level;
    }

    public ProgressMessage(String pid, int total, int current, String text) {
        this(pid, total, current, text, null);
    }

    public ProgressMessage(String pid, int total, int current) {
        this(pid, total, current, null);
    }

    public ProgressMessage(String pid, String text) {
        this(pid, 0, 0, text, null);
    }

    public ProgressMessage(ProgressNotifier notifier) {
        this(
            notifier.getPid(),
            notifier.getTotal(),
            notifier.getCurrent(),
            notifier.getText(),
            notifier.getLevel()
        );
    }

    @Override
    public String toString() {
        return String.format("ProgressMessage [pid=%s, total=%s, current=%s, text=%s, level=%s]",
            pid, total, current, text, level);
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}
