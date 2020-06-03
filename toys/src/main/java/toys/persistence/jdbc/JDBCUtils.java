package toys.persistence.jdbc;

import toys.exceptions.ToysException;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
     * Executa a consulta extraindo os dados utilizando a saída informada para salvar os dados.
     *
     * @param conn          Conexão com o banco de dados.
     * @param sql           Query a ser executada.
     * @param columnHeaders Flag indicando se a primeira linha dos resultados deve conter os nomes das colunas.
     * @param queryOutput   Objeto responsável pela saída dos dados.
     */
    public void extrair(Connection conn, String sql, boolean columnHeaders, QueryOutput queryOutput) throws SQLException, ToysException {
        queryOutput.resetRowCount();
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                if (columnHeaders)
                    queryOutput.writeHeader(rs);
                while (rs.next())
                    queryOutput.writeRow(rs);
            }
        }
    }

    /**
     * Executa a consulta informada extraindo os dados para uma lista de arrays de objetos.
     *
     * @param conn          Conexão com o banco de dados.
     * @param sql           SQL a ser executada.
     * @param columnHeaders Flag indicando se as colunas devem ser incluídas nos resultados.
     * @return Retorna o número de registros resultantes da consulta.
     * @see #extrair(Connection, String, boolean, QueryOutput)
     */
    public List<Object[]> extrairLista(Connection conn, String sql, boolean columnHeaders) throws ToysException, SQLException {
        var list = new ArrayList<Object[]>();
        var queryOutput = new ListQueryOutput(list);
        extrair(conn, sql, columnHeaders, queryOutput);
        return list;
    }

    /**
     * Método de conveniência para extrair a saida para um arquivo.
     *
     * @param conn         Conexão com o banco de dados.
     * @param sql          Consulta a ser executada.
     * @param charset      Charset utilizado na criação do arquivo de saída.
     * @param caminhoSaida Caminho onde o arquivo será salvo.
     * @return Retorna o total de registros.
     */
    public long extrairCSV(Connection conn, String sql, Charset charset, String caminhoSaida) throws IOException, SQLException, ToysException {
        try (FileOutputStream out = new FileOutputStream(caminhoSaida)) {
            try (OutputStreamWriter outWriter = new OutputStreamWriter(out, charset.name())) {
                try (BufferedWriter writer = new BufferedWriter(outWriter)) {
                    var queryOutput = new CSVQueryOutput(writer, ';');
                    extrair(conn, sql, true, queryOutput);
                    return queryOutput.getRowCount();
                }
            }
        }
    }

}
