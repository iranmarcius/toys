package toys.constants;

/**
 * Constantes utilizadas em operações envolvendo com bancos de dados.
 * @author Iran
 */
public interface DBConsts {

	/**
	 * Limite mínimo de data e hora para o driver jTDS.
	 */
	long MSSQL_TIMESTAMP_MINIMO = -6847793999296l;

	/**
	 * Limite máximo de data e hora para o driver jTDS.
	 */
	long MSSQL_TIMESTAMP_MAXIMO = 253402307999704l;

}
