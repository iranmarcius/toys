package toys.tests.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import toys.exceptions.ToysException;
import toys.persistence.jdbc.JDBCUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JDBCUtilsTest {
    private static Connection conn;
    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @BeforeAll
    public static void inicializar() throws SQLException {
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://totvs-beta.redeinterna.unitoledo.br/CorporeRMDevelop", "rm", "rm");
    }

    @AfterAll
    public static void finalizar() throws SQLException {
        conn.close();
    }

    @Test
    void extrairListTest() throws ToysException, SQLException {
        var list = jdbcUtils.extrairLista(conn, "select * from spletivo", true);
        assertFalse(list.isEmpty());
    }

    @Test
    void extrairCSVTest() throws IOException, SQLException, ToysException {
        jdbcUtils.extrairCSV(conn, "select * from sdocumento", StandardCharsets.ISO_8859_1, "c:/temp/saida.csv");
        assertTrue(true);
    }

}
