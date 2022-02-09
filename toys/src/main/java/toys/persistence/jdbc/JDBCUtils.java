package toys.persistence.jdbc;

import toys.exceptions.ToysException;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.sql.*;
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
   * @param params        Parâmetros opcionais para serem setados na query. Caso sejam informados a consulta será
   *                      executada através de um {@link Connection#prepareStatement prepareStatement}, do contrário
   *                      será utilizado o {@link Connection#createStatement() createStatement}.
   */
  @SuppressWarnings("ThrowableNotThrown")
  public void outputTo(Connection conn, String sql, boolean columnHeaders, QueryOutput queryOutput, Object... params)
    throws SQLException, ToysException {
    queryOutput.resetRowCount();
    Statement st = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      if (params == null || params.length == 0) {
        st = conn.createStatement();
        rs = st.executeQuery(sql);
      } else {
        ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++)
          ps.setObject(i + 1, params[i]);
        rs = ps.executeQuery();
      }
      if (columnHeaders)
        queryOutput.writeHeader(rs);
      while (rs.next())
        queryOutput.writeRow(rs);
    } finally {
      close(rs);
      close(st);
      close(ps);
    }
  }

  /**
   * Executa a consulta informada extraindo os dados para uma lista de arrays de objetos.
   *
   * @param conn          Conexão com o banco de dados.
   * @param sql           SQL a ser executada.
   * @param columnHeaders Flag indicando se as colunas devem ser incluídas nos resultados.
   * @param params        Parâmetros opcionais para serem setados na query.
   * @return Retorna o número de registros resultantes da consulta.
   * @see #outputTo(Connection, String, boolean, QueryOutput, Object...)
   */
  public List<Object[]> list(Connection conn, String sql, boolean columnHeaders, Object... params) throws ToysException, SQLException {
    var list = new ArrayList<Object[]>();
    var queryOutput = new ListQueryOutput(list);
    outputTo(conn, sql, columnHeaders, queryOutput, params);
    return list;
  }

  /**
   * Executa a consulta retornando um único resultado.
   *
   * @param conn            Conexão com o banco de dados.
   * @param sql             Consulta a ser executada.
   * @param errorOnMultiple Esta flag indica se deve ser gerado um erro caso a consulta retorne múltiplos resultados.
   *                        Caso ela esteja ligada e a consulta retorne múltiplos resultados, o método retornará
   *                        apenas o primeiro resultado.
   * @param params          Parâmetros opcionais para serem setados na query.
   * @return <code>Object[]</code>
   * @see #list(Connection, String, boolean, Object...)
   */
  public Object[] singleResult(Connection conn, String sql, boolean errorOnMultiple, Object... params) throws SQLException, ToysException {
    var l = list(conn, sql, false, params);
    if (l.isEmpty())
      return null;
    if (l.size() > 1 && errorOnMultiple)
      throw new ToysException("A consulta retornou multiplos resultados (%d): (%s)", l.size(), sql);
    return l.get(0);
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
  public long outputToCSV(Connection conn, String sql, Charset charset, String caminhoSaida)
    throws IOException, SQLException, ToysException {
    try (FileOutputStream out = new FileOutputStream(caminhoSaida)) {
      try (OutputStreamWriter outWriter = new OutputStreamWriter(out, charset.name())) {
        try (BufferedWriter writer = new BufferedWriter(outWriter)) {
          var queryOutput = new CSVQueryOutput(writer, ';');
          outputTo(conn, sql, true, queryOutput);
          return queryOutput.getRowCount();
        }
      }
    }
  }

  /**
   * Fecha um {@link ResultSet} sem gerar um erro. Caso ele ocorra será retornada como resultado do método.
   *
   * @param rs {@link ResultSet} para ser fechado.
   * @return Retorna nulo ou o erro gerado na operação.
   */
  public Throwable close(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        return e;
      }
    }
    return null;
  }

  /**
   * Fecha um {@link Statement} sem gerar um erro. Caso ele ocorra será retornada como resultado do método.
   *
   * @param st {@link Statement} para ser fechado.
   * @return Retorna nulo ou o erro gerado na operação.
   */
  public Throwable close(Statement st) {
    if (st != null) {
      try {
        st.close();
      } catch (SQLException e) {
        return e;
      }
    }
    return null;
  }

  /**
   * Fecha um {@link PreparedStatement} sem gerar um erro. Caso ele ocorra será retornada como resultado do método.
   *
   * @param ps {@link PreparedStatement} par ser fechado.
   * @return Retorna nulo ou o erro gerado na operação.
   */
  public Throwable close(PreparedStatement ps) {
    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException e) {
        return e;
      }
    }
    return null;
  }

}
