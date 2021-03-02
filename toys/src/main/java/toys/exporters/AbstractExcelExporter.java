package toys.exporters;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Implementação básica de classe utilitária para exportação de planilhas.
 *
 * @author Iran
 * @since 03/2021
 */
public abstract class AbstractExcelExporter extends AbstractExporter {

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
     * @param endCol   Coluna final.
     */
    protected void applyColumnWidths(XSSFSheet orig, XSSFSheet dest, int startCol, int endCol) {
        for (int col = startCol; col <= endCol; col++)
            dest.setColumnWidth(col, orig.getColumnWidth(col));
    }

    /**
     * Método invocado imediatamente antes criação do conteúdo da planilha.
     * A implementação padrão não realiza nenhuma operação.
     *
     * @param wb Workbook.
     */
    protected void preCreateContent(XSSFWorkbook wb) {
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
                preCreateContent(wb);
                createContent(wb);
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

}
