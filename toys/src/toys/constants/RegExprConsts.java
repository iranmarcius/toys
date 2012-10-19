package toys.constants;

/**
 * Esta interface define uma série de regular expressions para serem utilizadas em validações de dados.
 */
public interface RegExprConsts {

	/**
	 * Formato de e-mail.
	 */
	String EMAIL = "^[a-zA-Z0-9]([\\.\\-_]?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+";

	/**
	 * Formato para validação de números com separador decimal.
	 * O número não deverá conter separadores de milhar e o separador decimal poderá
	 * o ponto ou a vírgula.
	 */
	String DECIMAL_NUMBER = "^\\d+([,\\.]\\d+)?$";
	
	/**
	 * Formato de moeda brasileira.
	 */
	String CURRENCY_BR = "^(\\d{1,3}\\.)*\\d{1,3},\\d+$";

	/**
	 * Formato de CEP.
	 */
   String ZIP = "^\\d{5,5}\\-?\\d{3,3}$";

   /**
    * Formato para hora, com segundos e milissegundos opcionais.
    */
   String TIME = "^\\d{1,3}(:\\d{1,2}){1,2}(\\.\\d{1,3})?$";

   /**
    * Formato para hora com milissegundos opcionais.
    */
   String TIME_SEC = "^\\d{1,3}(:\\d{1,2}){2,2}(\\.\\d{1,3})?$";

   /**
    * Formato de hora completo.
    */
   String TIME_MS = "^\\d{1,3}(:\\d{1,2}){2,2}\\.\\d{1,3}$";

   /**
    * Formato simples de hora.
    */
   String SIMPLE_TIME = "^\\d{1,2}(:\\d{1,2}){0,2}$";

   /**
    * Formato de IPv4
    */
   String IPv4 = "^(\\d{1,3}\\.){3,3}\\d{1,3}$";

	/*
	 * Formatos localizados
	 */

	/**
	 * Formato de data localizado para o Brasil.
	 */
	String DATE_BR = "^\\d{1,2}/\\d{1,2}/\\d{4}$";

	/**
	 * Formato de data SQL
	 */
	String DATE_SQL = "^\\d{4,4}\\-\\d{2,2}\\-\\d{2,2}$";

	/**
	 * Formato de timestamp
	 */
	String TIMESTAMP = "^\\d{4,4}\\-\\d{2,2}\\-\\d{2,2} \\d{2,2}:\\d{2,2}:{2,2}\\.\\d{3,3}$";

	/**
	 * CPF
	 */
	String CPF = "^\\d{3,3}\\.\\d{3,3}\\.\\d{3,3}[-/]\\d{2,2}$";

	/**
	 * CNPJ
	 */

	String CNPJ = "^\\d{2}(\\.?\\d{3}){2}/\\d{4}\\-?\\d{2}$";

}
