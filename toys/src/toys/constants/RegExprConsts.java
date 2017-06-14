package toys.constants;

/**
 * Esta interface define uma série de regular expressions para serem utilizadas em validações de dados.
 */
public class RegExprConsts {

    /**
     * Formato de e-mail.
     */
    public static final String EMAIL = "^[a-zA-Z0-9_][a-zA-Z0-9_\\.\\-]*[a-zA-Z0-9_]@([a-zA-Z0-9_\\-]+\\.)+[a-zA-Z]+$";

    /**
     * Formato para validação de números com separador decimal.
     * O número não deverá conter separadores de milhar e o separador decimal poderá
     * o ponto ou a vírgula.
     */
    public static final String DECIMAL_NUMBER = "^\\d+([,\\.]\\d+)?$";

    /**
     * Formato de moeda brasileira.
     */
    public static final String CURRENCY_BR = "^(\\d{1,3}\\.)*\\d{1,3},\\d+$";

    /**
     * Formato de CEP.
     */
    public static final String ZIP = "^\\d{5,5}\\-?\\d{3,3}$";

   /**
    * Formato para hora, com segundos e milissegundos opcionais.
    */
    public static final String TIME = "^\\d{1,3}(:\\d{1,2}){1,2}(\\.\\d{1,3})?$";

   /**
    * Formato para hora com milissegundos opcionais.
    */
    public static final String TIME_SEC = "^\\d{1,3}(:\\d{1,2}){2,2}(\\.\\d{1,3})?$";

   /**
    * Formato de hora completo.
    */
    public static final String TIME_MS = "^\\d{1,3}(:\\d{1,2}){2,2}\\.\\d{1,3}$";

   /**
    * Formato simples de hora.
    */
    public static final String SIMPLE_TIME = "^\\d{1,2}(:\\d{1,2}){0,2}$";

   /**
    * Formato de IPv4
    */
    public static final String IPV4 = "^(\\d{1,3}\\.){3,3}\\d{1,3}$";

    /**
     * Caracteres separadores e de pontuação.
     */
    public static final String SEPARADORES_PONTUACAO = "[\\.\\-\\/\\(\\)]";

    /*
     * Formatos localizados para o Brasil
     */

    /**
     * Formato de data localizado para o Brasil.
     */
    public static final String DATE_BR = "^\\d{1,2}/\\d{1,2}/\\d{4}$";

    /**
     * Formato de data SQL
     */
    public static final String DATE_SQL = "^\\d{4,4}\\-\\d{2,2}\\-\\d{2,2}$";

    /**
     * Formato de timestamp
     */
    public static final String TIMESTAMP = "^\\d{4,4}\\-\\d{2,2}\\-\\d{2,2} \\d{2,2}:\\d{2,2}:{2,2}\\.\\d{3,3}$";

    /**
     * CPF
     */
    public static final String CPF = "^\\d{3,3}\\.\\d{3,3}\\.\\d{3,3}[-/]\\d{2,2}$";

    /**
     * CNPJ
     */
    public static final String CNPJ = "^\\d{2}(\\.?\\d{3}){2}/\\d{4}\\-?\\d{2}$";

    private RegExprConsts() {
        super();
    }

}
