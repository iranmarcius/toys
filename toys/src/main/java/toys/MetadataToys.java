package toys;

import toys.records.BinaryContentMetadata;

import java.util.Base64;

/**
 * Métodos utilitários para metadados.
 */
public class MetadataToys {

  private MetadataToys() {
  }

  /**
   * Retorna as informações de uma string representando uma imagem codificada em base64 retornando
   * as informações de forma estruturada.
   *
   * @param base64Content Imagem codificada no formado base64.
   * @return {@link BinaryContentMetadata}
   */
  public static BinaryContentMetadata imageData(String base64Content) {
    var i = base64Content.indexOf(",");
    var prefix = base64Content.substring(0, i);
    var base64 = base64Content.substring(i + 1);
    var content = Base64.getDecoder().decode(base64);
    i = prefix.indexOf(";");
    var contentEncoding = prefix.substring(i + 1);
    var contentType = prefix.substring(5, i);
    return new BinaryContentMetadata(content, content.length, contentType, contentEncoding);
  }

}
