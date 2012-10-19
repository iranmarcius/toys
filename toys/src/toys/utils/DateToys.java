/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 */

package toys.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import toys.application.LocaleUtils;

/**
 * Provê diversos métodos para manipulação e conversão datas e horas.
 * @author Iran Marcius
 */
public final class DateToys {
    private static Log log = LogFactory.getLog(DateToys.class);

    @Deprecated
    public static final long MILLIS_PER_SECOND = 1000;
    @Deprecated
    public static final long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
    @Deprecated
    public static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    @Deprecated
    public static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
    @Deprecated
    public static final long MILLIS_PER_WEEK = MILLIS_PER_DAY * 7;

    public static final byte SUNDAY 	= 0x01;
    public static final byte MONDAY 	= 0x02;
    public static final byte TUESDAY 	= 0x04;
    public static final byte WEDNESDAY 	= 0x08;
    public static final byte THURSDAY 	= 0x10;
    public static final byte FRIDAY 	= 0x20;
    public static final byte SATURDAY 	= 0x40;

    /**
     * Quantidade de minutos em um dia.
     */
    public static final int MINUTES_PER_DAY = 1440;

    /**
     * Quantidade de minutos numa semana.
     */
    public static final int MINUTES_PER_WEEK = 10080;

    /**
     * Cria uma data à partir da string informada. Para que o método funcione
     * corretamente, a data deverá estar no formato específico da localidade
     * informada.
     * @param s String a ser convertida em data
     * @param locale Localidade. Caso este valor seja nulo, será utilizada a
     * localidade atual de acordo com o a configuração do sistema.
     * @return Caso a string informada seja uma data válida para a localidade,
     * retorna um objeto <code>java.util.Date</code>, caso contrário, retorna
     * <b>null</b>.
     * @deprecated Será removido em favor do commons-lang. 
     */
    public static Date formatDate(String s, Locale locale) {
        Date d = null;
        try {
            if (locale == null) locale = Locale.getDefault();
            DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
            formatter.setLenient(false);
            d = formatter.parse(s);
        } catch (Exception e) {
            log.warn(String.format("Erro na conversao para data (%1$s, %2$s)", s, locale));
            return null;
        }
        return d;
    }

    /**
     * Cria uma data a partir da string informada. Para que o método funcione
     * corretamente, a data deverá estar informada no formato específico da
     * localidade padrão.
     * @param s String a ser convertida em data
     * @return Caso a string informada seja uma data válida para a localidade,
     * retorna um objeto <code>java.util.Date</code>, caso contrário, retorna
     * <b>null</b>.
     * @deprecated Será removido em favor do <b>commons-lang3</b>.
     */
    public static Date formatDate(String s) {
        return formatDate(s, Locale.getDefault());
    }

    /**
     * Retorna se a string informada representa uma data válida de acordo com a
     * localidade informada.
     * @param s String com a data
     * @param locale Localidade. Caso este valor seja nulo, utiliza a localidade
     * default.
     * @return boolean
     * @deprecated Será removido em favor do <b>commons-lang3</b>.
     */
    public static boolean isDate(String s, Locale locale) {
        return formatDate(s, locale) != null;
    }

    /**
     * Retorna se os dias de duas datas são iguais.
     * @param d1 Data 1
     * @param d2 Data 2
     * @return <code>boolean</code>
     * @deprecated Será removido em favor do <b>commons-lang3</b>.
     */
    public static boolean isDayEquals(Date d1, Date d2) {
    	return getTimeField(d1, Calendar.DAY_OF_MONTH) == getTimeField(d2, Calendar.DAY_OF_MONTH);
    }

    /**
     * Retorna se os meses de duas datas são iguais.
     * @param d1 Data 1
     * @param d2 Data 2
     * @return <code>boolean</code>
     * @deprecated Será removido em favor do <b>commons-lang3</b>.
     */
    public static boolean isMonthEquals(Date d1, Date d2) {
        return getTimeField(d1, Calendar.MONTH) == getTimeField(d2, Calendar.MONTH);
    }

    /**
     * Retorna se os anos de duas datas são iguais.
     * @param d1 Data 1
     * @param d2 Data 2
     * @return <code>boolean</code>
     * @deprecated Será removido em favor do <b>commons-lang3</b>.
     */
    public static boolean isYearEquals(Date d1, Date d2) {
        return getTimeField(d1, Calendar.YEAR) == getTimeField(d2, Calendar.YEAR);
    }

    /**
     * Retorna se as datas informadas possuem mesmo dia, mês e ano.
     * @param d1 Data 1
     * @param d2 Data 2
     * @return <code>boolean</code>
     * @deprecated Será removido em favor do <b>commons-lang3</b>.
     */
    public static boolean isDMYEquals(Date d1, Date d2) {
    	return isDayEquals(d1, d2) && isMonthEquals(d1, d2) && isYearEquals(d1, d2);
    }

    /**
     * Retorna se a string informada representa uma data válida de acordo com a
     * localidade padrão.
     * @param s String com a data
     * @return boolean
     * @deprecated Será removido em favor do <b>commons-lang3</b>.
     */
    public static boolean isDate(String s) {
        return formatDate(s) != null;
    }

    /**
     * <p>Retorna um <i>long</i> representando o intervalo de tempo especificado
     * na string. Nenhum objeto de manipulação de datas e horas (<code>Date</code> ou
     * <code>Calendar</code>) será utilizado para esta operação, sendo que o resultado
     * é apenas a soma do número de milissegundos de cada componente (dia, hora,
     * minuto e segundo) do tempo especificado.</p>
     * @param time String com a representação de tempo. Por exemplo: 3d2h10m15s
     * (três dias, duas horas, 10 minutos e 15 segundos).
     * @return O resultado retornado será um <code><b>long</b></code> com o número de
     * milissegundos do intervalo informado, que será obtido pela soma do número de
     * milissegundos de cada componente do intervalo informado.
     */
    public static long timeStr2ms(String time) {
        long t = 0;
        int i;

        if ((i = time.indexOf("d")) > -1) {
            t += Long.parseLong(time.substring(0, i)) * DateUtils.MILLIS_PER_DAY;
            time = time.substring(i + 1);
        }
        if ((i = time.indexOf("h")) > -1) {
            t += Long.parseLong(time.substring(0, i)) *  DateUtils.MILLIS_PER_HOUR;
            time = time.substring(i + 1);
        }
        if ((i = time.indexOf("m")) > -1) {
            t += Long.parseLong(time.substring(0, i)) * DateUtils.MILLIS_PER_MINUTE;
            time = time.substring(i + 1);
        }
        if ((i = time.indexOf("s")) > -1) {
            t += Long.parseLong(time.substring(0, i)) * DateUtils.MILLIS_PER_SECOND;
        }
        if (time.matches("^\\d+$")) {
            t = Long.parseLong(time);
        }

        return t;
    }

    /**
     * Retorna um <code>long</code> com o número de milissegundos do intervalo de tempo
     * especificado.
     * @param t String com a representação do tempo. Deverá estar no formado
     * <code>hh:mm[:ss]</code>.
     * @return Retorna um <code>long</code> com o número de milissegundos do intervalo
     * de tempo especificado, sendo que o resultado será obtido através da soma do
     * número de milissegundos de cada parte do tempo (hora, minuto e segundo).
     */
    public static long time2ms(String t) {
        String[] s = t.split(":");
        long ms = Long.parseLong(s[0]) * DateUtils.MILLIS_PER_HOUR;
        ms += Long.parseLong(s[1]) * DateUtils.MILLIS_PER_MINUTE;
        if (s.length > 2)
        	ms += Long.parseLong(s[2]) * DateUtils.MILLIS_PER_SECOND;
        return ms;
    }

    /**
     * Converte uma string contendo data e hora num formato específico para um objeto
     * do tipo <code>{@link Date}</code>.
     * @param dateTimeStr String contendo as informações de data e hora no formato
     * &quot;&lt;data&gt &lt;hora&gt;&quot;. A data deverá estar no formato padrão da localidade
     * informada (ex.: pt_BR = dd/mm/yyyy) e a hora no formado <code>hh:mm[:ss]</code>.
     * Caso a porção da hora seja omitida, será retornada uma data representando a hora inicial
     * do dia. A porção da data é obrigatória.
     * @return <code>{@link Date}</code>
     */
    public static Date dateTimeStr2Date(String dateTimeStr, Locale locale) {
    	try {
	        String[] p = dateTimeStr.split(" +");
	        Date d = DateUtils.parseDate(p[0], new String[] {"dd/MM/yyyy"});
	        if (p.length > 1) {
	            long ms = time2ms(p[1]);
	            d.setTime(d.getTime() + ms);
	        }
	        return d;
    	} catch (Exception e) {
    		return null;
    	}
    }

    /**
     * Método de conveniência para invocar o método
     * <code>{@link #dateTimeStr2Date(String, Locale)}</code> com a localidade padrão.
     * @see #dateTimeStr2Date(String, Locale)
     */
    public static Date dateTimeStr2Date(String dateTimeStr) {
        return dateTimeStr2Date(dateTimeStr, Locale.getDefault());
    }

    /**
     * Retorna um objeto de data criado à partir dos dados informados.
     * @param year Ano
     * @param month Mês
     * @param day Dia
     * @return <code>{@link Date}</code>
     */
    public static Date createDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * Retorna um objeto do tipo <i>java.sql.Date</i> a partir da string informada.
     * @param date String com a data a ser processada.
     * @return Retorna um <i>java.sql.Date</i> obtido da conversão da string ou <b>null</b>
     * caso a data especificada na string não seja válida.
     */
    public static java.sql.Date createDate(String date) {
        Date d = formatDate(date);
        if (d != null) {
            return new java.sql.Date(d.getTime());
        }
        return null;
    }

    /**
     * Retorna um objeto do tipo <code>java.sql.Time</code> a partir da string
     * informada.
     * @param time String representando a hora. Pode estar nos formatos <b>hh:mm:ss</b>
     * ou <b>hh:mm</b>. Caso os segundos não sejam informados será assumido <b>zero</b>.
     * @return Retorna um objeto do tipo <code>java.sql.Time</code> convertido a partir
     * da string ou <b>null</b> caso a hora informada não seja válida.
     */
    public static Time createTime(String time) {
        if (time != null) {
            try {
                Calendar c = Calendar.getInstance();
                String[] t = time.split(":");
                int h = Integer.parseInt(t[0]);
                int m = Integer.parseInt(t[1]);
                int s = t.length > 2 ? Integer.parseInt(t[2]) : 0;

                if (inRange(c, h, Calendar.HOUR_OF_DAY)
                        && inRange(c, m, Calendar.MINUTE)
                        && inRange(c, s, Calendar.SECOND)) {
                    c.clear();
                    c.set(Calendar.SECOND, s);
                    c.set(Calendar.MINUTE, m);
                    c.set(Calendar.HOUR_OF_DAY, h);
                    return new Time(c.getTimeInMillis());
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * Processa as strings de data e hora retornando sua representação de tempo
     * em milissegundos.
     * @param date String com a data no formado especificado no método
     * <code>{@link #formatDate(String) formatDate}</code>
     * @param time String com a hora no formato especificado no método
     * <code>{@link #time2ms(String) time2ms}</code>
     * @return O valor retornado será um <code>long</code> calculado à partir do
     * número de milissegundos da data acrescido ao número de milissegundos da hora.
     */
    public static long dateTime2ms(String date, String time) {
        long d = formatDate(date).getTime();
        long t = time2ms(time);
        return d + t;
    }

    /**
     * Método de conveniência para invocar o
     * <code>{@link #dateTime2ms(String) dateTime2ms}</code> informando a data e a
     * hora separados.
     * @param dt String de data e hora
     * @return <code>long</code>
     */
    public static long dateTime2ms(String dt) {
        String[] s = dt.split(" +");
        return dateTime2ms(s[0], s[1]);
    }

    /**
     * Seta o calendário para o primeiro dia do mês.
     */
    public static void inicioMes(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Seta o calendário para o último dia do mês corrente;
     */
    public static void finalMes(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Seta o calendário para o primeiro dia do ano.
     */
    public static void inicioAno(Calendar c) {
    	inicioMes(c);
    	c.set(Calendar.MONTH, c.getActualMinimum(Calendar.MONTH));
    }

    /**
     * Seta o calendário para o último dia do ano.
     */
    public static void finalAno(Calendar c) {
    	inicioMes(c);
    	c.set(Calendar.MONTH, c.getActualMaximum(Calendar.MONTH));
    	finalMes(c);
    }

    /**
     * Seta a data do calendário para o primeiro dia da semana anterior ou igual ao
     * início do mês.
     * @param c Calendário que será modificado
     */
    public static void inicioSemana(Calendar c) {
        inicioMes(c);
        int d = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_MONTH, -d + 1);
    }

    /**
     * Seta a data de um calendário para o último dia da semana posterior ou igual ao
     * final do mês.
     * @param c Calendário que será modificado
     */
    public static void finalSemana(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        int d = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_MONTH, 7 - d);
    }

    /**
     * Retorna o valor de um campo de data.
     * @param date Data de onde a informação será obtida
     * @param field Campo que deve ser retornado (constantes da classe
     * <code>java.util.Calendar</code>).
     * @return int
     */
    public static int getTimeField(Date date, int field) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(field);
    }

    /**
     * Altera o valor de um campo de data.
     * @param date Data que será modificada
     * @param field Campo que deve ser retornado (constantes da classe
     * <code>java.util.Calendar</code>).
     * @param value Novo valor
     */
    public static void setTimeField(Date date, int field, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(field, value);
        date.setTime(c.getTime().getTime());
    }

    /**
     * Retorna a diferenãa em anos entre duas datas considerando os meses e dias.
     * Este método é especialmente útil para cálculo de idades.
     * @param d1 Primeira data
     * @param d2 Segunda data
     * @return <code>int</code>
     */
    public static int deltaYears(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        int d = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        if (c2.get(Calendar.MONTH) < c1.get(Calendar.MONTH)) {
            d--;
        } else if (c2.get(Calendar.MONTH) == c1.get(Calendar.MONTH)) {
            if (c2.get(Calendar.DAY_OF_MONTH) < c1.get(Calendar.DAY_OF_MONTH)) {
                d--;
            }
        }
        return d;
    }

    /**
     * Retorna a diferença entre a data atual e a data informada utilizando
     * o método {@link #deltaYears(Date, Date) deltaYears}.
     * @param d Data base para o cálculo
     * @return <code>int</code>
     */
    public static int ageInYears(Date d) {
        return deltaYears(d, new Date());
    }

    /**
     * Idêntico ao método {@link #ageInYears(Date) ageInYears}.
     * @param birthday Data
     * @return <code>int</code>
     */
    public static int age(Date birthday) {
        return ageInYears(birthday);
    }

    /**
     * Retorna a diferença em meses entre duas datas considerando os dias do mês.
     * @param d1 Primeira data
     * @param d2 Segunda data
     * @return <code>int</code>
     */
    public static int deltaMonths(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        int d =
            ((c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12) +
            (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH));
        if (c2.get(Calendar.DAY_OF_MONTH) < c1.get(Calendar.DAY_OF_MONTH)) {
            d--;
        }

        return d;
    }

    /**
     * Retorna a diferença em dias entre as duas representações de tempo em
     * milissegundos.
     * @param t1 Representação inicial do tempo
     * @param t2 Representação final do tempo
     * @param diasInteiros Especifica se serão considerados apenas dias inteiros
     * @return <code>int</code>
     */
    public static int deltaDays(long t1, long t2, boolean diasInteiros) {
        long delta;
        if (t1 > t2)
            delta = t1 - t2;
        else
            delta = t2 - t1;
        long r = delta / DateToys.MILLIS_PER_DAY;
        if (!diasInteiros)
            if (delta % DateToys.MILLIS_PER_DAY > 0)
                r++;
        return (int)r;
    }

    /**
     * Método de conveniência para invocar o método
     * {@link #deltaDays(long, long) deltaDays(long, long)} passando parâmetros
     * do tipo <code>java.util.Date</code>.
     * @param d1 Data inicial
     * @param d2 Data final
     * @param diasInteiros Especifica se serão considerados apenas dias inteiros
     * @return <code>int</code>
     */
    public static int deltaDays(Date d1, Date d2, boolean diasInteiros) {
        return deltaDays(d1.getTime(), d2.getTime(), diasInteiros);
    }

    /**
     * Retorna a diferença em meses entre a data atual e a data fornecida utilizando
     * o método {@link #deltaMonths(Date, Date) deltaMonths}.
     * @param d Data base para o cálculo
     * @return <code>int</code>
     */
    public static int ageInMonths(Date d) {
        return deltaMonths(d, new Date());
    }

    /**
     * Seta os campos de hora do calendário para o início do dia.
     * @param c Calendário que será modificado.
     */
    public static void inicioDia(Calendar c) {
        if (c == null)
        	return;
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
    }

    /**
     * Seta a hora da data informada para o início do dia.
     * @param d Data a ser modificada
     * @deprecated Utilizar {@link DateUtils#truncate(Date, int)}
     */
    public static void inicioDia(Date d) {
        if (d == null)
            return;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        inicioDia(c);
        d.setTime(c.getTimeInMillis());
    }

    /**
     * Seta os campos de hora do calendário informado para o final do dia.
     * @param c Calendário a ser modificado
     */
    public static void terminoDia(Calendar c) {
        if (c == null)
        	return;
        c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
        c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
    }

    /**
     * Seta os campos de hora da data para o término do dia.
     * @param d Data a ser modificada.
     * @deprecated Utilizar {@link DateUtils#ceiling(Date, int)}
     */
    public static void terminoDia(Date d) {
    	if (d == null)
    		return;
    	Calendar c = Calendar.getInstance();
    	c.setTime(d);
    	terminoDia(c);
    	d.setTime(c.getTimeInMillis());
    }

    /**
     * Torna iguais os campos de milisegundos de duas datas.
     * @param reference Data que será tomada como referência
     * @param target Data que será modificada
     */
    public static void normalizeMilliseconds(Date reference, Date target) {
        setTimeField(
            target,
            Calendar.MILLISECOND,
            getTimeField(reference, Calendar.MILLISECOND)
        );
    }

    /**
     * Torna iguais os campos de segundos e milisegundos de duas datas.
     * @param reference Data que será tomada como referência
     * @param target Data que será modificada
     */
    public static void normalizeSeconds(Date reference, Date target) {
		setTimeField(target, Calendar.SECOND, getTimeField(reference, Calendar.SECOND));
		normalizeMilliseconds(reference, target);
    }

    /**
     * Retorna, a partir de uma string com uma data, um objeto do tipo
     * <code>java.sql.Timestamp</code> correspondendo à data informada com os campos
     * de hora ajustados para o início do dia.
     * @param date String de data para ser convertida
     * @return Retorna um {@link Timestamp Timestamp} ou <b>null</b> caso a data
     * fornecida não seja válida
     */
    public static Timestamp createDayBeginningTimestamp(String date) {
        Date d = formatDate(date);
        if (d != null) {
            inicioDia(d);
            return new Timestamp(d.getTime());
        } else {
            return null;
        }
    }

    /**
     * Retorna, a partir de uma string com uma data, um objeto do tipo
     * <code>java.sql.Timestamp</code> correspondendo à data informada com os campos
     * de hora ajustados para final do dia.
     * @param date Data a ser convertida
     * @return Retorna um {@link Timestamp Timestamp} ou <b>null</b> caso a data
     * fornecida não seja válida
     */
    public static Timestamp createDayEndingTimestamp(String date) {
        Date d = formatDate(date);
        if (d != null) {
            terminoDia(d);
            return new Timestamp(d.getTime());
        } else {
            return null;
        }
    }

    /**
     * Verifica se o valor informado está na faixa permitida de valores do campo de
     * data informado baseando-se no calendário informado.
     * @param c Instância do calendário a ser utilizada na verificação
     * @param v Valor a ser verificado
     * @param field Campo que será utilizado para comparação. Deve-se utilizar uma
     * das constantes da classe <code>{@link Calendar Calendar}</code>.
     * @return Retorna <code>true</code> se o valor estiver entre os valores
     * mínimos e máximos do campo solicitado, do contrário retorna <code>false</code>.
     */
    public static boolean inRange(Calendar c, int v, int field) {
        return NumberToys.inRange(v, c.getActualMinimum(field),
            c.getActualMaximum(field));
    }

    /**
     * Copia os campos de hora do segundo calendário para o primeiro.
     * @param c1 Primeiro calendário
     * @param c2 Segundo calendário
     */
    public static void setTimeFields(Calendar c1, Calendar c2) {
        c1.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
        c1.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));
        c1.set(Calendar.SECOND, c2.get(Calendar.SECOND));
        c1.set(Calendar.MILLISECOND, c2.get(Calendar.MILLISECOND));
    }

    /**
     * Seta os campos de hora de uma data de acordo com a hora informada.
     * @param date Data que será modificada
     * @param time Hora
     */
    public static void setTimeFields(Date date, Date time) {
        Calendar dc = Calendar.getInstance();
        Calendar tc = Calendar.getInstance();
        dc.setTime(date);
        tc.setTime(time);
        setTimeFields(dc, tc);
        date.setTime(dc.getTimeInMillis());
    }

    /**
     * Cria um <code>java.sql.Timestamp</code> a partir de uma data e hora informados.
     * @param date Data que será modificada. Se o valor for nulo o método retornará
     * um valor nulo também.
     * @param time Hora que será adicionada à data.
     * @return Retorna um <code>{@link Timestamp Timestamp}</code> com o resultado da
     * soma da data e a hora
     */
    public static Timestamp createTimestamp(Date date, Date time) {
        if (date != null) {
            Timestamp t = new Timestamp(date.getTime());
            if (time != null)
                setTimeFields(t, time);
            else
                inicioDia(t);
            return t;
        } else {
            return null;
        }
    }

    /**
     * Cria e retorna um {@link Timestamp} a partir da data e hora informados como string.
     * @param date Representação string da data. Caso a conversão deste campo resulte
     * num valor nulo, o método também retornará um valor nulo.
     * @param time Representação string da hora. Caso a conversão deste campo resulte
     * num valor nulo, será considerada a hora <b>00:00:00.000</b>.
     * @return <code>{@link Timestamp Timestamp}</code>
     */
    public static Timestamp createTimestamp(String date, String time) {
        Date d = formatDate(date);
        Time t = createTime(time);
        if (d != null) {
            return createTimestamp(d, t);
        } else {
            return null;
        }
    }

    /**
     * Cria e retorna um {@link Timestamp} a partir da data e da string de hora informados.
     * @param d Data. Caso o valor informado seja nulo, retorna um valor nulo.
     * @param time String com a representação da hora no formado especificado no método {@link #time2ms(String)}.
     * Caso o valor informado seja nulo, o timestamp retornado será a hora do início do dia.
     * @return {@link Timestamp}
     */
    public static Timestamp createTimestamp(Date d, String time) {
    	if (d == null)
    		return null;
    	inicioDia(d);
    	long t = d.getTime();
    	if (time != null)
    		t += time2ms(time);
    	return new Timestamp(t);
    }

    /**
     * Retorna o número de dias nos milissegundos informados.
     * @param ms Milissegundos
     * @return <code>int</code>
     */
    public static int toDays(long ms) {
        if (ms >= DateToys.MILLIS_PER_DAY) {
            return (int)(ms / DateToys.MILLIS_PER_DAY);
        } else {
            return 0;
        }
    }

    /**
     * Retorna o número de horas nos milissegundos informados.
     * @param ms Milissegundos
     * @return <code>int</code>
     */
    public static int toHours(long ms) {
        if (ms >= DateToys.MILLIS_PER_HOUR) {
            return (int)(ms / DateToys.MILLIS_PER_HOUR);
        } else {
            return 0;
        }
    }

    /**
     * Retorna o número de minutos nos milissegundos informados. Os milissegundos
     * excedentes serão somados como um minuto inteiro. Ou seja, caso os milissegundos
     * representem <b>1 minuto e 10 segundos</b>, o método retornará <b>2 minutos</b>.
     * @param ms Milissegundos
     * @return <code>int</code>
     */
    public static int toMinutes(long ms) {
        if (ms >= DateToys.MILLIS_PER_MINUTE) {
            return (int)(ms / DateToys.MILLIS_PER_MINUTE);
        } else {
            return 0;
        }
    }

    /**
     * Retorna uma string no formato <b>HHH:mm:ss.SSS</b> a partir do número de
     * milissegundos informado. Para isso este método verifica quantas horas,
     * minutos e segundos existem dentro do número de milissegundos informado.
     * @param ms Número de milissegundos. Este parâmetro representa um espaço de
     * tempo, e não um momento determinado assim como trabalha a classe
     * @param pattern Um número indicando o formato que será utilizado para o
     * tempo de acordo com a seguinte convenção:
     * <ul>
     * 	<li><b>0</b> - HH:mm</b></li>
     * 	<li><b>1</b> - HH:mm:ss</li>
     * 	<li><b>2</b> - HH:mm:ss.SSS</li>
     * </ul>
     * @return <code>String</code>
     */
    public static String formatTime(long ms, int pattern) {
        int h = (int)(ms / DateToys.MILLIS_PER_HOUR);
        ms = ms % DateToys.MILLIS_PER_HOUR;
        int m = (int)(ms / DateToys.MILLIS_PER_MINUTE);
        ms = ms % DateToys.MILLIS_PER_MINUTE;
        int s = (int)(ms / DateToys.MILLIS_PER_SECOND);
        ms = ms % DateToys.MILLIS_PER_SECOND;
        switch (pattern) {
		case 0: return String.format("%02d:%02d", h, m);
		case 1: return String.format("%02d:%02d:%02d", h, m, s);
		case 3: return String.format("%02d:%02d:%02d.%03d", h, m, s, ms);
		default: return "";
		}
    }

    /**
     * Retorna se o dia, mês e ano das duas datas fornecidas são iguais.
     * @param d1 Data 1
     * @param d2 Data 2
     * @return <code>boolean</code>
     */
    public static boolean sameDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return
            (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
            (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) &&
            (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Retorna uma data convertida em string utilizando o formato da localidade informada.
     * @param d Data a ser convertida.
     * @param locale Localidade para determinar o formato da data.
     * @return <code>String</code>
     */
    public static String toString(Date d, Locale locale) {
        String format = null;
        if (locale.equals(LocaleUtils.BRAZIL)) format = "%1$td/%1$tm/%1$tY"; else
        if (locale.equals(Locale.ENGLISH)) format = "%1$tm/%1$td/%1$tY";
        else format = "%1$tY-%1$tm-%1$td";
        return String.format(format, d);
    }

    /**
     * Retorna uma data convertida em string utilizando o formato da localidade default.
     * @param d Data a ser convertida.
     * @return <code>String</code>
     */
    public static String toString(Date d) {
        return toString(d, Locale.getDefault());
    }

    /**
     * Retorna se uma data está contida entre o início e o término especificados.
     * @param date Data de referência
     * @param start Data inicial da faixa
     * @param end Data final da faixa
     * @return <code>boolean</code>
     */
    public static boolean isBetween(Date date, Date start, Date end) {
        return
            ((date.getTime() >= start.getTime())) &&
            ((date.getTime() <= end.getTime()));
    }


    /**
     * Retorna uma coleção de nomes de dias da semana a partir do locale informado.
     * @param locale Locale para obtenção dos nomes. Caso este parâmetro seja nulo, utiliza o
     * locale default.
     * @return <code>Vector&lt;String&gt;</code>
     */
    public static Vector<String> getWeekdays(Locale locale) {
        DateFormatSymbols dfs = locale != null ? DateFormatSymbols.getInstance(locale) :
            DateFormatSymbols.getInstance();
        String[] s = dfs.getWeekdays();
        Vector<String> v = new Vector<String>();
        v.add(s[Calendar.SUNDAY]);
        v.add(s[Calendar.MONDAY]);
        v.add(s[Calendar.TUESDAY]);
        v.add(s[Calendar.WEDNESDAY]);
        v.add(s[Calendar.THURSDAY]);
        v.add(s[Calendar.FRIDAY]);
        v.add(s[Calendar.SATURDAY]);
        return v;
    }

    /**
     * Método de conveniência para obter uma lista dos dias da semana com o locale default.
     * @see #getWeekdays(Locale)
     * @return <code>Vector&lt;String&gt;</code>
     */
    public static Vector<String> getWeekdays() {
        return getWeekdays(null);
    }

    /**
     * Retorna uma relação de nomes de meses utilizando o locale informado.
     * @param locale Locale para obtenção dos nomes de meses. Caso este valor seja
     * nulo utiliza o locale default.
     * @return <code>Vector&lt;String&gt;</code>
     */
    public static Vector<String> getMonths(Locale locale) {
        DateFormatSymbols dfs = locale != null ? DateFormatSymbols.getInstance(locale) :
            DateFormatSymbols.getInstance();
        String[] s = dfs.getMonths();
        Vector<String> v = new Vector<String>();
        v.add(s[Calendar.JANUARY]);
        v.add(s[Calendar.FEBRUARY]);
        v.add(s[Calendar.MARCH]);
        v.add(s[Calendar.APRIL]);
        v.add(s[Calendar.MAY]);
        v.add(s[Calendar.JUNE]);
        v.add(s[Calendar.JULY]);
        v.add(s[Calendar.AUGUST]);
        v.add(s[Calendar.SEPTEMBER]);
        v.add(s[Calendar.OCTOBER]);
        v.add(s[Calendar.NOVEMBER]);
        v.add(s[Calendar.DECEMBER]);
        return v;
    }

    /**
     * Método de conveniência para retornar uma relação de nomes de meses utilizando
     * o locale default.
     * @see #getMonths(Locale)
     * @return <code>Vector&lt;String&gt;</code>
     */
    public static Vector<String> getMonths() {
        return getMonths(null);
    }

    /**
     * Formata uma string representando um horário, que esteja no formato HHMMSSsss utilizando
     * o formato especificado. Nenhuma verificação é feita no horário.
     * @param plain String de horário sem separadores.
     * @param pattern Padrão que será utilizado na formatação. 0 considera apenas horas e minutos;
     * 	1 considera horas, minutos e segundos; 2 considera horas, minutos, segundos e milissegundos.
     * @return <code>String</code>
     */
    public static String formatPlainTime(String plain, int pattern) {
    	if (plain == null || !plain.matches("^\\d{4}\\d{0,5}$"))
    		return "";
    	StringBuilder sb = new StringBuilder();
    	sb.append(plain.substring(0, 2)).append(":").append(plain.substring(2, 4));
    	if (pattern >= 1 && plain.length() >= 6) {
    		sb.append(":").append(plain.substring(4, 6));
    		if (pattern == 2 && plain.length() == 9)
    			sb.append(".").append(plain.substring(6, 9));
    	}
    	return sb.toString();

    }

    /**
     * Retorna se as duas faixas de datas possuem uma interseção.
     * @param f1d1 Data 1 da primeira faixa.
     * @param f1d2 Data 2 da primeira faixa.
     * @param f2d1 Data 1 da segunda faixa.
     * @param f2d2 Data 2 da segunda faixa.
     * @return <code>boolean</code>
     */
    public static boolean intersecao(Date f1d1, Date f1d2, Date f2d1, Date f2d2) {
    	return
    		NumberToys.inRange(f1d1.getTime(), f2d1.getTime(), f2d2.getTime()) ||
    		NumberToys.inRange(f1d2.getTime(), f2d1.getTime(), f2d2.getTime()) ||
    		NumberToys.inRange(f2d1.getTime(), f1d1.getTime(), f1d2.getTime()) ||
    		NumberToys.inRange(f2d2.getTime(), f1d1.getTime(), f1d2.getTime());
    }

    /**
     * Retorna se a primeira faixa de datas está contida na segunda.
     * @param f1d1 Primeira data da primeira faixa.
     * @param f1d2 Segunda data da primeira faixa.
     * @param f2d1 Primeira data da segunda faixa.
     * @param f2d2 Segunda data da segunda faixa.
     * @return <code>boolean</code>
     */
    public static boolean contido(Date f1d1, Date f1d2, Date f2d1, Date f2d2) {
    	return
    		NumberToys.inRange(f1d1.getTime(), f2d1.getTime(), f2d2.getTime()) &&
    		NumberToys.inRange(f1d2.getTime(), f2d1.getTime(), f2d2.getTime());
    }

    /**
     * Retorna se a data informada está contida dentro da faixa de datas.
     * @param d Data a ser verificada.
     * @param i Data inicial da faixa. Caso o valor seja nulo a verificação não será realizada para este parâmetro.
     * @param f Data final da faixa. Caso o valor seja nulo a verificação não será realizada para este parâmetro.
     * @return <code>boolean</code>
     */
    public static boolean contido(Date d, Date i, Date f) {
    	boolean contido = true;
    	if (i != null)
    		contido &= d.getTime() >= i.getTime();
    	if (f != null)
    		contido &= d.getTime() <= f.getTime();
    	return contido;
    }

    /**
     * Retorna a string convertida para <code>Timestamp</code> utilizando o método {@link Timestamp#valueOf(String)},
     * mas sem criar um erro no caso da string não ester no formato correto.
     * @param ts String com o timestamp
     * @return {@link Timestamp}
     */
    public static Timestamp toTimestamp(String ts) {
    	try {
    		return Timestamp.valueOf(ts);
    	} catch (Exception e) {
    		return null;
    	}
    }

}