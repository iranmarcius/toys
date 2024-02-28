package toys.exporters;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TemplateSheetData {
  private int columnCount;
  private int headerRowNumber;
  private int bodyRowNumber;
  private XSSFCellStyle[] headerStyles;
  private XSSFCellStyle[] bodyStyles;
  private CellType[] types;

  /**
   * Cria um objeto de dados de folha.
   * 
   * @param targetWorkbook  Planilha destino.
   * @param templateSheet   Folha de template.
   * @param columnCount     Número de colunas.
   * @param headerRowNumber Índice da linha de cabeçalho.
   * @param bodyRowNumber   Índice da linha de corpo .
   */
  public TemplateSheetData(
      XSSFWorkbook targetWorkbook,
      XSSFSheet templateSheet,
      int columnCount,
      int headerRowNumber,
      int bodyRowNumber
  ) {
    this.columnCount = columnCount;
    this.headerRowNumber = headerRowNumber;
    this.bodyRowNumber = bodyRowNumber;

    // Coleta e cria todos os estilos do template na planilha destino.
    headerStyles = new XSSFCellStyle[columnCount];
    bodyStyles = new XSSFCellStyle[columnCount];
    types = new CellType[columnCount];
    var headerRow = templateSheet.getRow(headerRowNumber);
    var bodyRow = templateSheet.getRow(bodyRowNumber);
    for (int c = 0; c < getColumnCount(); c++) {
      headerStyles[c] = targetWorkbook.createCellStyle();
      headerStyles[c].cloneStyleFrom(headerRow.getCell(c).getCellStyle());

      bodyStyles[c] = targetWorkbook.createCellStyle();
      bodyStyles[c].cloneStyleFrom(bodyRow.getCell(c).getCellStyle());

      types[c] = bodyRow.getCell(c).getCellType();
    }

  }

  public int getColumnCount() {
    return columnCount;
  }

  public int getHeaderRowNumber() {
    return headerRowNumber;
  }

  public int getBodyRowNumber() {
    return bodyRowNumber;
  }

  public XSSFCellStyle getHeaderStyle(int col) {
    return headerStyles[col];
  }

  public XSSFCellStyle getBodyStyle(int col) {
    return bodyStyles[col];
  }

  public CellType getType(int col) {
    return types[col];
  }

}
