package toys;

public class ObjectToys {

  private ObjectToys() {
  }

  /**
   * Retorna o valor default caso o valor seja nulo.
   *
   * @param value Valor principal.
   * @param defaultValue Valor default.
   */
  public static <T> T defaultValue(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }

}
