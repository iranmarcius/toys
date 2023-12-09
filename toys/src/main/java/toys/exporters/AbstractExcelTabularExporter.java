package toys.exporters;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Implementação do exportador de planilhas do excel especializado em formato tabular.
 * 
 * @author Iran Marcius
 * @since 12/2023
 */
public abstract class AbstractExcelTabularExporter<T> extends AbstractExcelTemplateExporter<T> {
  protected XSSFCellStyle[] headerStyles;
  protected XSSFCellStyle[] bodyStyles;
  protected CellType[] types;

  /**
   * Retorna o número de colunas da tabela.
   * 
   * @return int
   */
  protected abstract int getColumnCount();

  /**
   * Retorna o número da linha de cabeçalho.
   * 
   * @return int
   */
  protected abstract int getHeaderRowNumber();

  /**
   * Retorna o número da linha de corpo.
   * 
   * @return int
   */
  protected abstract int getBodyRowNumber();

  /**
   * Implementação padrão para inicialização dos arrays de estilos e tipos.
   * 
   * @param wb Workbook destino para criação de estilos e linhas de cabeçalho.
   */
  protected void processTemplate(XSSFWorkbook wb) {
    headerStyles = new XSSFCellStyle[getColumnCount()];
    bodyStyles = new XSSFCellStyle[getColumnCount()];
    types = new CellType[getColumnCount()];
    var bodyRow = templateSheet.getRow(getBodyRowNumber());
    for (int c = 0; c < getColumnCount(); c++) {
      headerStyles[c] = cloneCellStyle(templateSheet, getHeaderRowNumber(), c, wb);
      bodyStyles[c] = cloneCellStyle(templateSheet, getBodyRowNumber(), c, wb);
      types[c] = bodyRow.getCell(c).getCellType();
    }
  }

  /**
   * Gera o cabeçalho na planilha destino e aplica as larguras das.
   * 
   * @param dest      Folha destino.
   * @param rowNumber Linha onde o cabeçalho será criado.
   */
  protected void createHeader(XSSFSheet sheet, int rowNumber) {
    applyColumnWidths(templateSheet, sheet, 0, getColumnCount());
    var originRow = templateSheet.getRow(getHeaderRowNumber());
    var destRow = sheet.createRow(rowNumber);
    for (int c = 0; c < getColumnCount(); c++) {
      var cell = destRow.createCell(c);
      cell.setCellStyle(headerStyles[c]);
      cell.setCellValue(originRow.getCell(c).getStringCellValue());
    }
  }

}
