/*
 * Criado em 31/08/2009 17:29:23
 */

package toys.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Esta classe permite loops em objetos de data.
 * @author Iran
 */
public class TimeIterator {
	private int tipo;
	private int salto;
	private Date inicio;
	private Date fim;

	public TimeIterator() {
		super();
		tipo = Calendar.DAY_OF_MONTH;
		salto = 1;
		inicio = new Date();
		fim = new Date();
	}

	/**
	 * Instancia este objeto inicializando as propriedades
	 * @param inicio Data inicial
	 * @param fim Data final
	 * @param tipo Qualquer uma das constantes utilizadas na classe {@link Calendar}
	 */
	public TimeIterator(Date inicio, Date fim, int tipo) {
		this();
		this.tipo = tipo;
		this.inicio.setTime(inicio.getTime());
		this.fim.setTime(fim.getTime());
	}

	public void iterate(TimeIteratorPredicate predicate) {
		Date d = new Date(inicio.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		while (d.before(fim)) {
			predicate.execute(d, this);
			c.add(tipo, salto);
			d.setTime(c.getTimeInMillis());
		}
	}

	public void setInicio(Date inicio) {
		this.inicio.setTime(inicio.getTime());
	}

	public void setFim(Date fim) {
		this.fim.setTime(fim.getTime());
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
