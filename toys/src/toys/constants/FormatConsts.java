/*
 * Criado em 09/04/2009 10:20:53 
 */

package toys.constants;

/**
 * Definições de constantes utilizadas para formatar dados.
 * @author Iran
 */
public interface FormatConsts {
	
	/**
	 * Data no formato BR
	 */
	String DATE_BR = "%1$td/%1$tm/%1$tY";
	
	/**
	 * Data no formato SQL
	 */
	String DATE_SQL = "%1$tY-%1$tm-%1$td";
	
	/**
	 * Horas e minutos
	 */
	String TIME_HM = "%1$tH:%1$tM";
	
	/**
	 * Horas, minutos e segundos
	 */
	String TIME_HMS = "%1$tH:%1$tM:%1$tS";
	
	/**
	 * Horas, minutos, segundos e milissegundos
	 */
	String TIME_HMSL = "%1$tH:%1$tM:%1$tS.%1$tL";
	
	/**
	 * Data e hora completos, no formato Timestamp
	 */
	String TIMESTAMP = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL";
	
}
