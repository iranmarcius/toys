/*
 * Criado em 03/09/2013 15:03:39
 */

package toys.swt;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.xssf.usermodel.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Classe utilitária para exportar conteúdos de tabelas para uma planilha do excel.
 * @author Iran
 */
public class TableExcelExporter {

    public TableExcelExporter() {
        super();
    }

    /**
     * Método de conveniência para invocar o método de exportação passando a tabela relacionada ao viewer.
     * @param viewer Viewer contendo a referência para a tabela.
     * @param sheetTitle Título da folha que será criada dentro do arquivo de planilha.
     */
    public void export(TableViewer viewer, String sheetTitle) {
        export(viewer.getTable(), sheetTitle);
    }

    /**
     * Exporta a tabela para o arquivo informado.
     * @param table Referência para a tabela.
     * @param sheetTitle Título da folha que será criada dentro do arquivo de planilha.
     */
    public void export(Table table, String sheetTitle) {

        // Obtém o caminho e nome do arquivo de saída.
        FileDialog d = new FileDialog(table.getShell(), SWT.SAVE);
        d.setText("Exportar tabela para planilha do Excel");
        d.setFilterExtensions(new String[] {"*.xlsx"});
        d.setFilterNames(new String[] {"Planilha do Excel"});
        d.setOverwrite(true);
        String arquivoSaida = d.open();
        if (StringUtils.isBlank(arquivoSaida))
            return;

        // Cria a planilha.
        TableColumn[] cols = table.getColumns();
        XSSFWorkbook wb = new XSSFWorkbook();
        configure(wb);
        XSSFSheet sheet = wb.createSheet(sheetTitle);
        int r = 0;
        int c = 0;
        XSSFRow row = null;
        XSSFCell cell = null;

        XSSFCellStyle headerStyle = getHeaderStyle(wb);

        // Cria o cabeçalho
        row  = sheet.createRow(r++);
        for (TableColumn tc: cols) {
            cell = row.createCell(c++);
            cell.setCellValue(tc.getText());
            if (headerStyle != null)
                cell.setCellStyle(headerStyle);
        }

        // Cria as linhas
        TableItem[] items = table.getItems();
        for (TableItem item: items) {
            row = sheet.createRow(r++);
            for (c = 0; c < cols.length; c++) {
                cell = row.createCell(c);
                setCellValue(item, cell, c, (String)cols[c].getData("fieldId"));
                configureCell(item, cell, c, (String)cols[c].getData("fieldId"));
            }
        }

        // Salva o arquivo.
        try (FileOutputStream out = new FileOutputStream(new File(arquivoSaida))) {
            wb.write(out);
            MessageDialog.openInformation(table.getShell(), "Sucesso", String.format(
                    "Planilha exportada com sucesso para o arquivo %s.", arquivoSaida));
        } catch (IOException e) {
            LogManager.getLogger(getClass()).error(String.format("Erro exportando a planilha para o arquivo %s.", arquivoSaida), e);
            MessageDialog.openError(table.getShell(), "Erro", "Ocorreu um erro exportando a planilha. Consulte o log.");
        }

    }

    /**
     * Retorna o estilo que será utilizado no cabeçalho.
     */
    protected XSSFCellStyle getHeaderStyle(XSSFWorkbook wb) {
        XSSFFont bold = wb.createFont();
        bold.setBold(true);

        XSSFCellStyle style = wb.createCellStyle();
        style.setFont(bold);

        return style;
    }

    /**
     * Método de conveniência para inicializar fontes e estilos caso necessário.
     * @param wb Referência para a planilha.
     */
    protected void configure(XSSFWorkbook wb) {
        // Implementação deve ser feita na classe que herda.
    }

    /**
     * Seta o valor da célula. Este método poderá ser sobrecarregado para casos específicos.
     * @param item Item da tabela.
     * @param cell Célula.
     * @param index Índice da coluna.
     * @param fieldId Identificador do campo caso tabela tenha sido configurada utilizando o
     * {@link SWTUtils#configureColumns(TableViewer, List, CellLabelProvider) SWTUtils.configureColumns}
     */
    protected void setCellValue(TableItem item, XSSFCell cell, int index, String fieldId) {
        cell.setCellValue(item.getText(index));
    }

    /**
     * Configura tipo e estilo da célula. Por padrão nada acontece neste método.
     * @param item Referência para item da tabela.
     * @param cell Referência para a célula.
     * @param index Índice da coluna.
     * @param fieldId Identificador do campo caso tabela tenha sido configurada utilizando o
     * {@link SWTUtils#configureColumns(TableViewer, List, CellLabelProvider) SWTUtils.configureColumns}
     */
    protected void configureCell(TableItem item, XSSFCell cell, int index, String fieldId) {
        // Implementação deve ser feita pela classe que herda.
    }

}
