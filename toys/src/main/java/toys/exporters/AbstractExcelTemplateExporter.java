package toys.exporters;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import toys.exceptions.ToysRuntimeException;

/**
 * Implementação do exportador de planilhas com métodos para leitura de estilos
 * de uma planilha modelo.
 *
 * @author Iran Marcius
 * @since 12/2021
 */
public abstract class AbstractExcelTemplateExporter<T> extends AbstractExcelExporter<T> {
  protected XSSFWorkbook templateWorkbook;

  /**
   * Retorna o resource do template.
   *
   * @return {@code InputStream}
   */
  protected abstract InputStream getTemplateResource();

  /**
   * Abre a planilha modelo papa ler os estilos.
   *
   * @param targetWorkbook Workbook.
   */
  @Override
  protected void beforeCreateContent(XSSFWorkbook targetWorkbook) throws IOException {
    super.beforeCreateContent(targetWorkbook);
    var template = getTemplateResource();
    templateWorkbook = new XSSFWorkbook(Objects.requireNonNull(template));
    logger.debug("Template carregado.");
    processTemplate(targetWorkbook);
    logger.debug("Estilos carregados do template.");
  }

  /**
   * Obtém todas as informações necessárias do template.
   *
   * @param targetWorkbook Workbook destino.
   */
  protected abstract void processTemplate(XSSFWorkbook targetWorkbook);

  @Override
  protected void afterCreateContent(XSSFWorkbook targetWorkbook) {
    try {
      templateWorkbook.close();
      logger.debug("Planilha modelo fechada.");
    } catch (IOException e) {
      throw new ToysRuntimeException(e);
    }
  }
}
