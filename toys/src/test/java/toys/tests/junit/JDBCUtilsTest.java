package toys.tests.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import toys.exceptions.ToysException;
import toys.persistence.jdbc.JDBCUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCUtilsTest {
    private static Connection conn;
    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @BeforeClass
    public static void inicializar() throws SQLException {
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://totvs-beta.redeinterna.unitoledo.br/CorporeRMDevelop", "rm", "rm");
    }

    @AfterClass
    public static void finalizar() throws SQLException {
        conn.close();
    }

    @Test
    public void extrairListTest() throws ToysException, SQLException {
        var list = jdbcUtils.extrairLista(conn, "select * from spletivo", true);
        assertFalse(list.isEmpty());
    }

    @Test
    public void extrairCSVTest() throws IOException, SQLException, ToysException {
        jdbcUtils.extrairCSV(conn, "select * from sdocumento", StandardCharsets.ISO_8859_1, "c:/temp/saida.csv");
        assertTrue(true);
    }

}
