package toys.persistence.jdbc;

public abstract class AbstractQueryOutput implements QueryOutput {
    protected long rowCount;

    public void resetRowCount() {
        rowCount = 0;
    }

    public long getRowCount() {
        return rowCount;
    }

}
