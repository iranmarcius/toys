package toys.exporters;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Implementação do exportador de planilhas do excel especializado em formato tabular.
 * 
 * @author Iran Marcius
 * @since 12/2023
 */
public abstract class AbstractExcelTabularExporter<T> extends AbstractExcelTemplateExporter<T> {
  private List<TemplateSheetData> templateSheetData;

  /**
   * Retorna o número de colunas para a folha informada.
   * 
   * @param sheetIndex Índice da folha.
   */
  protected abstract int getColumnCount(int sheetIndex);

  /**
   * Retorna o índice da linha de cabeçalho para a folha informada.
   * 
   * @param sheetIndex Índice da folha.
   */
  protected abstract int getHeaderRowNumber(int sheetIndex);

  /**
   * Retorna ao índice da linha de corpo para folha informada.
   * 
   * @param sheetIndex Índice da folha.
   */
  protected abstract int getBodyRowNumber(int sheetIndex);

  /**
   * Lê os dados de estilos e tipos de todas as folhas da planilha.
   * 
   * @param targetWorkbook Workbook destino para criação de estilos.
   */
  protected void processTemplate(XSSFWorkbook targetWorkbook) {
    templateSheetData = new ArrayList<>();
    for (int i = 0; i < templateWorkbook.getNumberOfSheets(); i++)
      templateSheetData.add(new TemplateSheetData(
          targetWorkbook,
          templateWorkbook.getSheetAt(i),
          getColumnCount(i),
          getHeaderRowNumber(i),
          getBodyRowNumber(i)
      ));

  }

  /**
   * Gera o cabeçalho na planilha destino e aplica as larguras das.
   * 
   * @param destSheet Folha destino.
   * @param rowNumber Linha onde o cabeçalho será criado.
   */
  protected void createHeader(int sheetIndex, XSSFSheet destSheet, int rowNumber) {
    var templateSheet = templateWorkbook.getSheetAt(sheetIndex);
    applyColumnWidths(
        templateSheet,
        destSheet,
        0,
        getColumnCount(sheetIndex)
    );
    var originRow = templateSheet.getRow(getHeaderRowNumber(sheetIndex));
    var destRow = destSheet.createRow(rowNumber);
    for (int c = 0; c < getColumnCount(sheetIndex); c++) {
      var cell = destRow.createCell(c);
      cell.setCellStyle(templateSheetData.get(sheetIndex).getHeaderStyle(c));
      cell.setCellValue(originRow.getCell(c).getStringCellValue());
    }
  }

  protected TemplateSheetData getTemplateSheetData(int sheetIndex) {
    return templateSheetData.get(sheetIndex);
  }

}
