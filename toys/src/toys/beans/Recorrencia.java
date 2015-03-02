package toys.beans;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.time.DateUtils;

import toys.utils.DateToys;
import toys.utils.NumberToys;

/**
 * Esta classe pode ser utilizada em implementações que envolvem recorrência temporal.
 * Ela armazena informações como tipo de recorência e retorna o tempo quando uma
 * nova ocorrência deve ocorrer e seu método {@link #toString()} retorna uma string
 * com a explicação textual da recorrência.
 */
public class Recorrencia {
	private ResourceBundle res;

	/**
	 * Tipos de recorrência.
	 */
	public enum Tipo {

		/**
		 * Recorrência diária
		 */
		DIARIA,

		/**
		 * Recorrência semanal
		 */
		SEMANAL,

		/**
		 * Recorrência mensal
		 */
		MENSAL,

		/**
		 * Recorrência anual
		 */
		ANUAL
	};

	/**
	 * Tipo da recorrência.
	 */
	private Tipo tipo;

	/**
	 * Este campo representa o intervalo entre as ocorrências de um evento.
	 * Dependendo do tipo da recorrência ele pode representar dias, semanas, meses ou anos.
	 */
	public int intervalo;

	/**
	 * O valor deste campo é relevante apenas para recorrências do tipo
	 * {@link Tipo#ANUAL} e indica o mês da recorrência.
	 */
	public int mes;

	/**
	 * O valor deste campo é relevante apenas para recorrências do tipo
	 * {@link Tipo#MENSAL} e {@link Tipo#ANUAL}.
	 * Ele representa uma semana do mês (primeira, segunda, terceira ou quarta)
	 * e seu valor deve estar entre 1 e 4.
	 */
	public int semana;

	/**
	 * O valor deste campo é relevante apenas para recorrências do tipo
	 * {@link Tipo#SEMANAL} e consiste de um array de 7 posições, cada
	 * uma representando um dia da semana, iniciando no domingo. Cada posição
	 * do array é um valor do tipo <code>boolean</code> indicando se o dia
	 * está incluído na recorrência.
	 */
	public boolean[] diaSemana;

	/**
	 * O valor deste campo é relevante apenas para as recorrências do tipo
	 * {@link Tipo#MENSAL} e {@link Tipo#ANUAL} e assume significados
	 * diferentes de acordo com o valor do campo {@link #semana}:
	 * <ul>
	 * 		<li>Se <b>{@link #semana} estiver entre 1 e 4</b>, o valor deste campo representa
	 * 		um dia da semana entre 0 e 6, de domingo a segunda;</li>
	 * 		<li>se <b>{@link #semana} não estiver entre 1 e 4</b>, o valor deste campo representa um dia
	 * 		de mês.</li>
	 * </ul>
	 */
	public int dia;

	/**
	 * Esta flag indica que no cálculo da próxima ocorrência não serão considerados os dias
	 * do mês ou dias da semana. Por exemplo, com a flag desligada, uma recorrência configurada
	 * para ocorrer todo mês na primeira segunda-feira, ocorrerá exatamente dessa forma. Com a flag
	 * ligada, a ocorrência ocorrerá exatamente um mês após a última, não considerando o dia da semana
	 * ou do mês.
	 */
	public boolean usarIntervaloExato;

	/**
	 * Construtor default.
	 */
	public Recorrencia() {
		super();
		res = ResourceBundle.getBundle(getClass().getName());
		diaSemana = new boolean[7];
		usarIntervaloExato = false;
		reset();
	}

	/**
	 * Retorna um texto descritivo sobre a recorrência.
	 * @return <code>String</code>
	 */
	@Override
	public String toString() {
		if (tipo == null)
			return res.getString("semRecorrencia");

		StringBuffer sb = new StringBuffer()
			.append(res.getString("aCada")).append(" ")
			.append(intervalo).append(" ");
		DateFormatSymbols dfs = new DateFormatSymbols();

		if (isDiaria())
			sb.append(res.getString("dias"));
		else
			if (isSemanal())
				sb.append(res.getString("semanas"));
			else
				if (isMensal())
					sb.append(res.getString("meses"));
				else
					if (isAnual())
						sb.append(res.getString("anos"));
		sb.append(" ");

		if (!usarIntervaloExato) {
			if (isSemanal()) {
				sb.append(res.getString("nosDias")).append(": ");
				for (int i = 0; i < diaSemana.length; i++) {
					if (diaSemana[i]) {
						sb.append(dfs.getShortWeekdays()[i + 1]).append(", ");
					}
				}
				if (sb.length() > 0) {
					sb.setLength(sb.length() - 2);
				}
				return sb.toString();
			}

			if (isMensal() || isAnual()) {
				if (consideraSemana()) {
					sb.append(res.getString("no"));
				} else {
					sb.append(res.getString("noDia"));
				}
				sb.append(" ");
				switch (semana) {
					case 1: sb.append(res.getString("1o")); break;
					case 2: sb.append(res.getString("2o")); break;
					case 3: sb.append(res.getString("3o")); break;
					case 4: sb.append(res.getString("4o")); break;
					default: sb.append(dia);
				}
				if (consideraSemana())
					sb.append(" ").append(dfs.getWeekdays()[dia + 1]);
				sb.append(" ");
			}

			if (isAnual()) {
				sb
					.append(res.getString("noMes"))
					.append(" ")
					.append(dfs.getMonths()[mes]);
			}
		} else {
			sb.append(res.getString("daUltima"));
		}

		return sb.toString();
	}

	/**
	 * Retorna se é recorrência é diária.
	 * @return <code>boolean</code>
	 */
	public boolean isDiaria() {
		return (tipo != null) && tipo.equals(Tipo.DIARIA);
	}

	/**
	 * Retorna se a recorrência é semanal.
	 * @return <code>boolean</code>
	 */
	public boolean isSemanal() {
		return (tipo != null) && tipo.equals(Tipo.SEMANAL);
	}

	/**
	 * Retorna se a recorrência é mensal.
	 * @return <code>boolean</code>
	 */
	public boolean isMensal() {
		return (tipo != null) && tipo.equals(Tipo.MENSAL);
	}

	/**
	 * Retorna se a recorrência é anual.
	 * @return <code>boolean</code>
	 */
	public boolean isAnual() {
		return (tipo != null) && tipo.equals(Tipo.ANUAL);
	}

	/**
	 * Seta o {@link Calendar} informado para a próxima ocorrência de acordo com as
	 * configurações da recorrência.
	 * @param c Calendar a ser alterado
	 */
	public void setProximaOcorrencia(Calendar c) {
		DateUtils.truncate(c, Calendar.HOUR_OF_DAY);
		if (isDiaria())
			setProximaOcorrenciaDiaria(c);
		else
			if (isSemanal())
				setProximaOcorrenciaSemanal(c);
			else
				if (isMensal())
					setProximaOcorrenciaMensal(c);
				else
					if (isAnual())
						setProximaOcorrenciaAnual(c);
					else
						throw new IllegalArgumentException("Tipo de recorrência inválido");
	}

	/**
	 * Seta a data para a próxima ocorrência de acordo com as configurações da
	 * recorrência.
	 * @param d Data a aser modificada.
	 */
	public void setProximaOcorrencia(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		setProximaOcorrencia(c);
		d.setTime(c.getTimeInMillis());
	}

	/**
	 * Retorna quantos dias da semana estão ligados. Caso o tipo da recorrência
	 * não seja semanal, o valor retornado será -1.
	 * @return <code>int</code>
	 */
	public int numeroDiasSemana() {
		if (isSemanal()) {
			int c = 0;
			for (boolean wd: diaSemana)
				if (wd) c++;
			return c;
		} else {
			return 0;
		}
	}

	/**
	 * Retorna se o número da semana está sendo utilizado na recorrência.
	 * @return <code>boolean</code>
	 */
	public boolean consideraSemana() {
		return ((semana >= 1) && (semana <= 4));
	}

	/**
	 * Retorna o tipo da recorrência.
	 * @return {@link Recorrencia.Tipo}
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * Seta o intervalo da recorrência.
	 * @param intervalo Novo intervalo
	 */
	public void setIntervalo(int intervalo) {
		if (intervalo > 0) {
			this.intervalo = intervalo;
		} else {
			throw new IllegalArgumentException("O intervalo deve ser maior que zero");
		}
	}

	/**
	 * Retorna o intervalo da recorrência.
	 * @return <code>int</code>
	 */
	public int getIntervalo() {
		return intervalo;
	}

	/**
	 * Seta os dias da semana como uma combinação de bits.
	 * @param diasSemana
	 */
	public void setDiasSemana(int diasSemana) {
		diaSemana[0] = (diasSemana & DateToys.SUNDAY) > 0;
		diaSemana[1] = (diasSemana & DateToys.MONDAY) > 0;
		diaSemana[2] = (diasSemana & DateToys.TUESDAY) > 0;
		diaSemana[3] = (diasSemana & DateToys.WEDNESDAY) > 0;
		diaSemana[4] = (diasSemana & DateToys.THURSDAY) > 0;
		diaSemana[5] = (diasSemana & DateToys.FRIDAY) > 0;
		diaSemana[6] = (diasSemana & DateToys.SATURDAY) > 0;
	}

	/**
	 * Retorna os dias como uma combinação de bits que resultam num número.
	 * @return <code>int</code>
	 */
	public int getDiaSemanas() {
		int d = 0;
		if (diaSemana[0]) d = d | DateToys.SUNDAY;
		if (diaSemana[1]) d = d | DateToys.MONDAY;
		if (diaSemana[2]) d = d | DateToys.TUESDAY;
		if (diaSemana[3]) d = d | DateToys.WEDNESDAY;
		if (diaSemana[4]) d = d | DateToys.THURSDAY;
		if (diaSemana[5]) d = d | DateToys.FRIDAY;
		if (diaSemana[6]) d = d | DateToys.SATURDAY;
		return d;
	}

	/**
	 * Seta o tipo e reseta todos os outros campos da classe.
	 * @param tipo Tipo da recorrência
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
		reset();
	}

	/**
	 * Seta o mês, a semana e o dia da semana.
	 * @param mes Número do mês, entre 0 e 11.
	 * @param semana Semana do mês, entre 1 e 4
	 * @param dia Dia da semana, entre 0 e 6
	 * @throws IllegalArgumentException Cria uma exceção caso o tipo de recorrência
	 * não seja {@link Tipo#ANUAL} ou caso um dos valores fornecidos não seja válido
	 */
	public void setMesSemanaDia(int mes, int semana, int dia) throws IllegalArgumentException {
		this.mes = mes;
		setSemanaDia(semana, dia);
	}

	/**
	 * Seta o mês e o dia do mês.
	 * @param mes Mês, entre 0 e 11
	 * @param dia Dia do mÊs, entre 1 e 28, caso a recorrência seja mensal, ou
	 * entre 1 e o último dia do mês, caso a recorrência
	 * seja anual.
	 * @throws IllegalArgumentException Cria uma exceção caso o tipo de recorrência
	 * não seja {@link Tipo#MENSAL} ou {@link Tipo#ANUAL} ou se um dos valores
	 * fornecidos não sejam válidos.
	 */
	public void setMesDia(int mes, int dia) throws IllegalArgumentException {
		this.mes = mes;
		this.semana = -1;
		this.dia = dia;
	}

	/**
	 * Seta o número da semana e o dia da semana.
	 * @param semana Um valor entre 1 e 4, representando a semana do mês
	 * @param dia Um valor entre 0 e 6 representando um dia da semana
	 * @throws IllegalArgumentException Cria esta exceção no caso do tipo de
	 * recorrência não seja {@link Tipo#MENSAL} ou {@link Tipo#ANUAL}, ou se
	 * um dos valores informados para {@link #semana} e {@link #day} não
	 * forem válidos.
	 */
	public void setSemanaDia(int semana, int dia) throws IllegalArgumentException {
		this.semana = semana;
		if (isMensal())
			this.mes = -1;
		this.dia = dia;
	}

	/**
	 * Seta o dia do mês para recorrências mensais ou anuais.
	 * @param dia Dia do mês.
	 * @throws IllegalArgumentException Cria uam exceção caso a recorrência
	 * não seja do tipo {@link Tipo#MENSAL} ou {@link Tipo#ANUAL} ou o valor do
	 * dia não seja válido.
	 */
	public void setDiaDoMes(int dia) throws IllegalArgumentException {
		this.semana = -1;
		this.mes = -1;
		this.dia = dia;
	}

	/*
	 * ---------------------------
	 * Métodos privados auxiliares
	 * ---------------------------
	 */

	/**
	 * Reseta os valores dos campos da recorrência.
	 */
	private void reset() {
		intervalo = -1;
		Arrays.fill(diaSemana, false);
		semana = -1;
		dia = -1;
		mes = -1;
	}

	/*
	 * ---------------------------------------
	 * Métodos privados para cálculo das datas
	 * ---------------------------------------
	 */

	/**
	 * Retorna a data a próxima ocorrência diária. O valor de {@link #intervalo} representa
	 * o número de dias entre as ocorrências.
	 */
	private void setProximaOcorrenciaDiaria(Calendar c) {
		c.add(Calendar.DAY_OF_MONTH, intervalo);
	}

	/**
	 * Retorna a data da próxima ocorrência semanal.
	 * O valor de {@link #intervalo} refere-se ao intervalo
	 * entre as semanas, e o valor de {@link #day} é uma combinação
	 * dos dias da semana nos quais o evento poderá ocorrer.
	 */
	private void setProximaOcorrenciaSemanal(Calendar c) {

		// se a flag de intervalo exato estiver desligada, considera os dias da semana
		// configurados, caso contrário, simplesmente acrescenta a quantidade de semanas ao intervalo
		if (!usarIntervaloExato) {

			// tenta obter a próxima ocorrência na semana atual
			if (!temOcorrenciaNaSemana(c, true)) {

				// se não conseguir, aplica o intervalo de semanas e tenta encontrar
				// a primeira ocorrência da semana encontrada
				c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				c.add(Calendar.DAY_OF_MONTH, intervalo * 7);
				temOcorrenciaNaSemana(c, false);
			}
		} else {
			c.add(Calendar.DAY_OF_MONTH, intervalo * 7);
		}
	}

	/**
	 * <p>Retorna a próxima ocorrência com intervalo mensal.</p>
	 * <p>O valor de {@link #intervalo} representa o número de meses entre cada
	 * ocorrência.</p>
	 * <p>Caso o valor de {@link #semana} seja <b>maior que zero</b>, significa que a
	 * ocorrência se dará na semana especificada por este parâmetro (primeira, segunda, etc),
	 * e o valor de {@link #day} será considerado como sendo o dia da semana, começando
	 * por zero (domingo).</p>
	 * <p>No caso do valor de {@link #semana} ser <b>igual zero</b>, o valor de {@link #day}
	 * será considerado como o dia do mês.</p>
	 */
	private void setProximaOcorrenciaMensal(Calendar c) {
		Calendar cal = (Calendar)c.clone();

		// caso a flag de intervalo exato esteja desligada, considera os dias setados na recorrência,
		// caso contrário apenas soma a quantidade de dias dos meses entre um intervalo e outro
		if (!usarIntervaloExato) {
			posicionarProximoDiaOcorrencia(cal, dia, -1, semana);
			if (!c.before(cal)) {
				cal.add(Calendar.MONTH, intervalo);
				posicionarProximoDiaOcorrencia(cal, dia, -1, semana);
			}
		} else {
			for (int i = 0; i < intervalo; i++)
				cal.add(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
		}
		c.setTime(cal.getTime());
	}

	/**
	 * Retorna a próxima recorrência anual.
	 */
	private void setProximaOcorrenciaAnual(Calendar c) {
		Calendar cal = (Calendar)c.clone();

		// caso a flag de usar intervalo exato esteja desligada, calcula a próxima ocorrência
		// utilizando os dias setados, caso contrário soma a quantidade de dias do ano à data atual
		// quantas vezes estiver especificado no intervalo
		if (!usarIntervaloExato) {
			posicionarProximoDiaOcorrencia(cal, dia, mes, semana);
			if (!c.before(cal)) {
				cal.add(Calendar.YEAR, intervalo);
				posicionarProximoDiaOcorrencia(cal, dia, mes, semana);
			}
		} else {
			for (int i = 0; i < intervalo; i++)
				cal.add(Calendar.DAY_OF_YEAR, cal.getMaximum(Calendar.DAY_OF_YEAR));
		}
		c.setTime(cal.getTime());
	}

	/**
	 * Retorna se existe uma ocorrência na semana da data configurada no objeto <code>Calendar</code>
	 * passado. A data configurada no objeto de calendário será setada para a data da ocorrência encontrada.
	 * @param c Calendário com a data base
	 * @param pularDiaAtual Se a flag estiver ativada, pula o dia corrente.
	 * @return <code>boolean</code>
	 */
	private boolean temOcorrenciaNaSemana(Calendar c, boolean pularDiaAtual) {
		int wd = c.get(Calendar.DAY_OF_WEEK);
		int ultimoDia = c.getMaximum(Calendar.DAY_OF_WEEK);
		if (pularDiaAtual)
			wd++;
		for (int d = wd; d <= ultimoDia; d++) {
			if (diaSemana[d - 1]) {
				c.set(Calendar.DAY_OF_WEEK, d);
				return true;
			}
		}
		return false;
	}

	/**
	 * Posiciona o dia do mês no dia ordinal da semana correspondente. Ex: primeira segunda-feira,
	 * terceiro domingo, etc.
	 * @param c Objeto de calendário que será modificado
	 * @param diaSemana Dia da semana
	 * @param semana Semana do mês (primeira, segunda, etc)
	 */
	private void posicionarNoDiaDaSemana(Calendar c, int diaSemana, int semana) {
		int mesOriginal = c.get(Calendar.MONTH);

		// posiciona o dia para a primeira ocorrência do dia da semana no mês
		c.set(Calendar.DAY_OF_MONTH, c.getMinimum(Calendar.DAY_OF_MONTH));
		while (c.get(Calendar.DAY_OF_WEEK) != diaSemana)
			c.add(Calendar.DAY_OF_MONTH, 1);

		// posiciona na semana especificada (primeira, segunda, terceira ou quarta)
		c.add(Calendar.DAY_OF_MONTH, (semana - 1) * 7);

		// se o dia saiu do mês, gera uma exceção
		if (c.get(Calendar.MONTH) != mesOriginal) {
			throw new IllegalArgumentException("Valor do dia da semana invalido");
		}
	}

	/**
	 * Posiciona o calendário passado na data da próxima ocorrência dentro de um ano.
	 * @param c Referência para o calendário que será modificado
	 * @param dia Dia da ocorrência
	 * @param mes Mês da ocorrência
	 * @param semana Semana do mês da ocorrência
	 */
	private void posicionarProximoDiaOcorrencia(Calendar c, int dia, int mes, int semana) {
		if (mes > -1)
			c.set(Calendar.MONTH, mes);

		// verica os valores dos campos
		if (NumberToys.inRange(semana, 1, 4)) {
			if (NumberToys.inRange(dia, 0, 6))
				posicionarNoDiaDaSemana(c, dia, semana);
			else
				throw new IllegalArgumentException(
						"Quando a semana for especificada, o dia deverá estar entre 0 e 6");
		} else {
			if (dia > 0)
				c.set(Calendar.DAY_OF_MONTH, dia);
			else
				throw new IllegalArgumentException(
						"Quando a semana não for especificada, o dia deverá representar um dia do mês");
		}
	}

}
