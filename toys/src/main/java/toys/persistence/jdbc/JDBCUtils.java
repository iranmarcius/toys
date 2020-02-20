package toys.persistence.jdbc;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utilitário para gerar um arquivo CSV à partir de uma query. Todas as colunas devem ter nomes únicos definidos.
 *
 * @author Iran
 * @since 02/2020
 */
public class JDBCUtils {

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
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                try (OutputStreamWriter outWriter = new OutputStreamWriter(out, charset.name())) {
                    try (BufferedWriter writer = new BufferedWriter(outWriter)) {

                        var sb = new StringBuilder();
                        var metadata = rs.getMetaData();

                        // Gera as linhas com os nomes das colunas
                        for (int i = 1; i <= metadata.getColumnCount(); i++)
                            sb.append(metadata.getColumnName(i)).append(separador);
                        writer.write(sb.substring(0, sb.length() - 1));
                        writer.newLine();

                        // Percorre o resultset salvando os resultados
                        while (rs.next()) {
                            total++;
                            sb.setLength(0);
                            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                                var value = rs.getObject(i);
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
