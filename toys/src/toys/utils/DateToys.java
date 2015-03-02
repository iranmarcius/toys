/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 */

package toys.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang3.time.DateUtils;

import toys.application.LocaleUtils;

/**
 * Provê diversos métodos para manipulação e conversão datas e horas.
 * @author Iran Marcius
 */
public final class DateToys {
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
        long r = delta / DateUtils.MILLIS_PER_DAY;
        if (!diasInteiros)
            if (delta % DateUtils.MILLIS_PER_DAY > 0)
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
     * Retorna o número de dias nos milissegundos informados.
     * @param ms Milissegundos
     * @return <code>int</code>
     */
    public static int toDays(long ms) {
        if (ms >= DateUtils.MILLIS_PER_DAY) {
            return (int)(ms / DateUtils.MILLIS_PER_DAY);
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
        if (ms >= DateUtils.MILLIS_PER_HOUR) {
            return (int)(ms / DateUtils.MILLIS_PER_HOUR);
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
        if (ms >= DateUtils.MILLIS_PER_MINUTE) {
            return (int)(ms / DateUtils.MILLIS_PER_MINUTE);
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
        int h = (int)(ms / DateUtils.MILLIS_PER_HOUR);
        ms = ms % DateUtils.MILLIS_PER_HOUR;
        int m = (int)(ms / DateUtils.MILLIS_PER_MINUTE);
        ms = ms % DateUtils.MILLIS_PER_MINUTE;
        int s = (int)(ms / DateUtils.MILLIS_PER_SECOND);
        ms = ms % DateUtils.MILLIS_PER_SECOND;
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
     * @param d Data de referência
     * @param d1 Data inicial da faixa
     * @param d2 Data final da faixa
     * @return <code>boolean</code>
     */
    public static boolean estaEntre(Date d, Date d1, Date d2) {
        return
        		d != null &&
        		d1 != null &&
        		d1.getTime() <= d.getTime() &&
        		d2 != null &&
        		d2.getTime() >= d.getTime();
    }

    /**
     * Retorna se o momento atual está entre as duas datas/horas informadas.
     * @param d1 Data e hora iniciais.
     * @param d2 Data e hora finais.
     * @return Retorna TRUE se o valor retornado por {@link System#currentTimeMillis()} estiver contido
     * entre as duas datas.
     */
    public static boolean estaEntre(Date d1, Date d2) {
    	long agora = System.currentTimeMillis();
    	return
    			d1 != null &&
    			d1.getTime() <= agora &&
    			d2 != null &&
    			d2.getTime() >= agora;
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