package toys.persistence.jdbc;

import toys.exceptions.ToysException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ListQueryOutput extends AbstractQueryOutput {
    private final List<Object[]> list;

    public ListQueryOutput(List<Object[]> list) {
        this.list = list;
    }

    @Override
    public void writeHeader(ResultSet rs) throws ToysException {
        try {
            var metadata = rs.getMetaData();
            var columnCount = metadata.getColumnCount();
            var headers = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++)
                headers[i - 1] = metadata.getColumnName(i);
            list.add(headers);
        } catch (SQLException e) {
            throw new ToysException(e);
        }
    }

    @Override
    public void writeRow(ResultSet rs) throws ToysException {
        try {
            var metadata = rs.getMetaData();
            var columnCount = metadata.getColumnCount();
            var row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++)
                row[i - 1] = rs.getObject(i);
            list.add(row);
        } catch (SQLException e) {
            throw new ToysException(e);
        }
    }

}
