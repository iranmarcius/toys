package toys;

/**
 * Métodos utilitários para lidar com arquivos.
 *
 * @author Iran Marcius
 * @since 09/2021
 */
public class FileToys {

  private FileToys() {
  }

  /**
   * Converte um tamanho de arquivo legível para o tamanho que ele representa.
   *
   * @param textSize Especificação do tamanho do arquivo. Pode conter os sufixos:
   *                 <ul>
   *                 <li>B - Tamanho especificado em bytes.</li>
   *                 <li>K - tamanho especificado em kilobytes.</li>
   *                 <li>M - tamanho especificado em megabytes.</li>
   *                 <li>G - tamanho especificado em gigabytes.</li>
   *                 </ul>
   * @return <code>long</code>
   */
  public static long textSizeToLong(String textSize) {
    long size = Long.parseLong(textSize.replaceFirst("[bBkKmMgG]$", ""));
    if (textSize.matches("^\\d+[Kk]$"))
      size *= 0x00000400;
    else if (textSize.matches("^\\d+[mM]$"))
      size *= 0x00100000;
    else if (textSize.matches("\\d+[Gg]$"))
      size *= 0x40000000;
    return size;
  }

}
