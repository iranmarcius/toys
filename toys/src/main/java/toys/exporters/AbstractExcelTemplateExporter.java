package toys.exporters;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import toys.exceptions.ToysRuntimeException;

import java.io.IOException;
import java.util.Objects;

/**
 * Implementação do exportador de planilhas com métodos para leitura de estilos
 * de uma planilha modelo.
 *
 * @author Iran Marcius
 * @since 12/2021
 */
public abstract class AbstractExcelTemplateExporter extends AbstractExcelExporter {
  protected XSSFWorkbook templateWorkbook;
  protected XSSFSheet templateSheet;

  /**
   * Retorna o caminho da planilha modelo.
   *
   * @return String
   */
  protected abstract String getTemplateResourcePath();

  /**
   * Abre a planilha modelo papa ler os estilos.
   *
   * @param wb Workbook.
   */
  @Override
  protected void preCreateContent(XSSFWorkbook wb) {
    super.preCreateContent(wb);
    try {
      logger.debug("Lendo planilha modelo: {}", getTemplateResourcePath());
      templateWorkbook = new XSSFWorkbook(Objects.requireNonNull(
        getClass().getClassLoader().getResourceAsStream(getTemplateResourcePath())));
      templateSheet = templateWorkbook.getSheetAt(0);
      readStyles(wb);
    } catch (IOException e) {
      throw new ToysRuntimeException("Erro lendo planilha modelo. modelo=%s.", getTemplateResourcePath(), e);
    }
  }

  /**
   * Lê os estilos da planilha modelo.
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
