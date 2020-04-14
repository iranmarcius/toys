package toys.persistence.jdbc;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário para gerar um arquivo CSV à partir de uma query. Todas as colunas devem ter nomes únicos definidos.
 *
 * @author Iran
 * @since 02/2020
 */
public class JDBCUtils {

    /**
     * Executa a conslta informada extraindo os dados para uma lista contendo arrays de objetos
     * contendo os valores de cada coluna da consulta.
     *
     * @param conn          Conexão com o banco de dados.
     * @param sql           Query a ser executada.
     * @param columnHeaders flag indicando se a primeira linha dos resultados deve conter os nomes das colunas.
     * @return <code>List&lt;Object[]&gt;</code>
     */
    public List<Object[]> extrair(Connection conn, String sql, boolean columnHeaders) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                var metadata = rs.getMetaData();

                if (columnHeaders) {
                    var headers = new Object[metadata.getColumnCount()];
                    for (int i = 1; i <= metadata.getColumnCount(); i++)
                        headers[i - 1] = metadata.getColumnName(i);
                    result.add(headers);
                }

                while (rs.next()) {
                    var row = new Object[metadata.getColumnCount()];
                    for (int i = 1; i <= metadata.getColumnCount(); i++)
                        row[i - 1] = rs.getObject(i);
                    result.add(row);
                }
            }
        }
        return result;
    }

    /**
     * Extrai os dados de uma consulta SQL salvando-os no stream informado no formato CSV.
     *
     * @param conn      Conexão com o banco de dados.
     * @param sql       SQL a ser executada.
     * @param out       Stream de saída.
     * @param charset   Charset das informações.
     * @param separador Separador de campos.
     * @return Retorna o número de registros resultantes da consulta.
     */
    public int extrair(Connection conn, String sql, OutputStream out, Charset charset, String separador) throws SQLException, IOException {
        int total = 0;
        var result = extrair(conn, sql, true);
        try (OutputStreamWriter outWriter = new OutputStreamWriter(out, charset.name())) {
            try (BufferedWriter writer = new BufferedWriter(outWriter)) {
                var sb = new StringBuilder();
                for (Object[] row : result) {
                    sb.setLength(0);
                    for (Object value : row) {
                        if (value instanceof String)
                            sb.append(value.toString().trim());
                        else
                            sb.append(value);
                        sb.append(separador);
                    }
                    writer.write(sb.substring(0, sb.length() - 1));
                    writer.newLine();
                }
            }
        }
        return total;
    }

    /**
     * Método de conveniência para extrair a saida para um arquivo.
     *
     * @see #extrair(Connection, String, OutputStream, Charset, String)
     */
    public int extrairCSV(Connection conn, String sql, Charset charset, String separator, String caminhoSaida) throws IOException, SQLException {
        try (FileOutputStream out = new FileOutputStream(caminhoSaida)) {
            return extrair(conn, sql, out, charset, separator);
        }
    }

}
