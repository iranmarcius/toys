package toys.exporters;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import toys.exceptions.ToysRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Implementação do exportador de planilhas com métodos para leitura de estilos
 * de uma planilha modelo.
 *
 * @author Iran Marcius
 * @since 12/2021
 */
public abstract class AbstractExcelTemplateExporter<T> extends AbstractExcelExporter<T> {
  protected XSSFWorkbook templateWorkbook;
  protected XSSFSheet templateSheet;

  /**
   * Retorna o resource do template.
   *
   * @return {@code InputStream}
   */
  protected abstract InputStream getTemplateResource();

  /**
   * Abre a planilha modelo papa ler os estilos.
   *
   * @param wb Workbook.
   */
  @Override
  protected void preCreateContent(XSSFWorkbook wb) throws IOException {
    super.preCreateContent(wb);
    var template = getTemplateResource();
    templateWorkbook = new XSSFWorkbook(Objects.requireNonNull(template));
    logger.debug("Template carregado.");
    templateSheet = templateWorkbook.getSheetAt(0);
    readStyles(wb);
    logger.debug("Estilos carregados do template.");
  }

  /**
   * Lê os estilos da planilha modelo para criar na planilha destino.
   *
   * @param wb Workbook no qual os estilos serão criados.
   */
  protected abstract void readStyles(XSSFWorkbook wb);

  @Override
  protected void postCreateContent(XSSFWorkbook wb) {
    try {
      templateWorkbook.close();
      logger.debug("Planilha modelo fechada.");
    } catch (IOException e) {
      throw new ToysRuntimeException(e);
    }
  }
}
