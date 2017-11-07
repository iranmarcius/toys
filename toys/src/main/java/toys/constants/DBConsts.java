package toys.constants;

/**
 * Constantes utilizadas em operações envolvendo com bancos de dados.
 * @author Iran
 */
public class DBConsts {

	/**
	 * Limite mínimo de data e hora para o driver jTDS.
	 */
	public static final long JTDS_MINTS = -6847793999296l;

	/**
	 * Limite máximo de data e hora para o driver jTDS.
	 */
	public static final long JTDS_MAXTS = 253402307999704l;

	/**
	 * Nome da função GETDATE do SQL Server.
	 */
	public static final String MSSQL_GETDATE_FUNC = "getdate";

	private DBConsts() {
	}

}
