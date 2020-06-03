package toys.persistence.jdbc;

import toys.exceptions.ToysException;

import java.sql.ResultSet;

public interface QueryOutput {
    void writeHeader(ResultSet rs) throws ToysException;
    void writeRow(ResultSet rs) throws ToysException;
    void resetRowCount();
    long getRowCount();
}
