package toys.persistence.jdbc;

import toys.exceptions.ToysException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CSVQueryOutput extends AbstractQueryOutput {
    private final BufferedWriter writer;
    private final char separator;

    public CSVQueryOutput(BufferedWriter writer, char separator) {
        this.writer = writer;
        this.separator = separator;
    }

    @Override
    public void writeHeader(ResultSet rs) throws ToysException {
        try {
            var metadata = rs.getMetaData();
            var columnCount = metadata.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                writer.write(metadata.getColumnName(i));
                if (i < columnCount)
                    writer.write(separator);
            }
            writer.newLine();
        } catch (SQLException | IOException e) {
            throw new ToysException(e);
        }
    }

    @Override
    public void writeRow(ResultSet rs) throws ToysException {
        try {
            var columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                var value = rs.getObject(i);
                if (value != null)
                    writer.write(value.toString().trim());
                if (i < columnCount)
                    writer.write(separator);
            }
            writer.newLine();

        } catch (SQLException | IOException e) {
            throw new ToysException(e);
        }
    }

}
