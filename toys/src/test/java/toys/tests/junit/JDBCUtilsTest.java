package toys.tests.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import toys.persistence.jdbc.JDBCUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCUtilsTest {
    private static Connection conn;
    private JDBCUtils jdbcUtils = new JDBCUtils();

    @BeforeClass
    public static void inicializar() throws ClassNotFoundException, SQLException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://totvs-beta.redeinterna.unitoledo.br/CorporeRMDevelop", "rm", "rm");
    }

    @AfterClass
    public static void finalizar() throws SQLException {
        conn.close();
    }

    @Test
    public void extrairCSVTest() throws IOException, SQLException {
        jdbcUtils.extrairCSV(conn, "select * from sdocumento", StandardCharsets.ISO_8859_1, ";", "c:/temp/saida.csv");
        assertTrue(true);
    }

}
