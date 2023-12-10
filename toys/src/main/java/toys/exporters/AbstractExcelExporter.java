package toys.exporters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Implementação básica de classe utilitária para exportação de planilhas.
 *
 * @author Iran
 * @since 03/2021
 */
public abstract class AbstractExcelExporter<T> extends AbstractExporter {

  /**
   * Retorna uma célula da aba informada garantindo que ela seja criada caso
   * não exista.
   *
   * @param sheet Aba.
   * @param row   Linha.
   * @param col   Coluna.
   */
  protected XSSFCell ensureGetCell(XSSFSheet sheet, int row, int col) {
    var sheetRow = sheet.getRow(row);
    if (sheetRow == null)
      sheetRow = sheet.createRow(row);
    var cell = sheetRow.getCell(col);
    if (cell == null)
      cell = sheetRow.createCell(col);
    return cell;
  }

  /**
   * Clona o estilo de uma célula existente na aba de origem criando-o no workbook.
   *
   * @param orig Aba de origem onde existem as células que devem ser clonadas.
   * @param row  Linha da célula cujo estilo deve ser clonado.
   * @param col  Coluna da célula cujo estilo deve ser clonado.
   * @param dest Workbook onde o estilo clonado será criado.
   * @return Retorna um objeto de estilo.
   */
  protected XSSFCellStyle cloneCellStyle(XSSFSheet orig, int row, int col, XSSFWorkbook dest) {
    XSSFCellStyle styleDest = dest.createCellStyle();
    XSSFCellStyle styleOrig = orig.getRow(row).getCell(col).getCellStyle();
    styleDest.cloneStyleFrom(styleOrig);
    return styleDest;
  }

  /**
   * Aplica à folha destino as larguras das colunas eespecificadas da folha de origem.
   *
   * @param orig     Folha origem.
   * @param dest     Folha destino.
   * @param startCol Coluna inicial.
   * @param count    Total de colunas.
   */
  protected void applyColumnWidths(XSSFSheet orig, XSSFSheet dest, int startCol, int count) {
    logger.trace("Aplicando larguras de colunas.");
    for (int col = startCol; col <= startCol + count; col++)
      dest.setColumnWidth(col, orig.getColumnWidth(col));
  }

  /**
   * Cria um grupo de células em uma linha.
   *
   * @param sheet  Folha.
   * @param rowNum Número da linha.
   * @param colNum Número da coluna inicial.
   * @param total  Quantidade de células que serão criadas na linha.
   * @return Retorna a linha criada.
   */
  protected XSSFRow createRowCells(XSSFSheet sheet, int rowNum, int colNum, int total) {
    var row = sheet.createRow(rowNum);
    for (int i = 0; i < total; i++)
      row.createCell(colNum + i);
    return row;
  }

  /**
   * Cria uma célula setando o conteúdo.
   */
  protected <V> XSSFCell
      createCell(XSSFRow row, int col, V value, CellType type, XSSFCellStyle style) {
    if (logger.isTraceEnabled())
      logger.trace("Criando célula: linha={}, coluna={}, valor={}", row.getRowNum(), col, value);
    var cell = row.createCell(col);
    if (value != null) {
      if (value instanceof String v)
        cell.setCellValue(v);
      else if (value instanceof Number v)
        cell.setCellValue(v.doubleValue());
      else if (value instanceof Date v)
        cell.setCellValue(v);
      else if (value instanceof LocalDateTime v)
        cell.setCellValue(v);
      else
        cell.setCellValue(String.format("%s (não tratado)", value));
    }
    cell.setCellType(value != null ? type : CellType.BLANK);
    cell.setCellStyle(style);
    return cell;
  }

  /**
   * Método invocado imediatamente antes criação do conteúdo da planilha.
   * A implementação padrão não realiza nenhuma operação.
   *
   * @param wb Workbook.
   */
  protected void preCreateContent(XSSFWorkbook wb) throws IOException {
    // Nada ocorre aqui.
  }

  /**
   * Método invocado imediatamente após a criação do conteúdo da planilha.
   *
   * @param wb Workbook.
   */
  protected void postCreateContent(XSSFWorkbook wb) {
    // Nada ocorre aqui
  }

  /**
   * Exporta a planilha.
   *
   * @return <code>byte[]</code>
   */
  protected byte[] export() throws IOException {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      try (XSSFWorkbook wb = new XSSFWorkbook()) {
        logger.debug("Invocando preCreateContent.");
        preCreateContent(wb);
        logger.debug("Criando conteúdo.");
        createContent(wb);
        logger.debug("Invocando posCreateContent.");
        postCreateContent(wb);
        wb.write(out);
      }
      return out.toByteArray();
    }
  }

  /**
   * Cria o conteúdo da planilha.
   *
   * @param wb Workbook no qual o conteúdo será criado.
   */
  protected abstract void createContent(XSSFWorkbook wb);

  /**
   * Cria a planilha de excel em formato binário a partir dos dados informados.
   *
   * @param data Dados para popular a planilha.
   */
  public abstract byte[] export(T data) throws IOException;

}
