package toys.tests.junit;

import java.util.Calendar;
import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import toys.application.LocaleUtils;
import toys.utils.BeanToys;

public class ObjectUtilsTest extends TestCase {
	private java.sql.Date aDate;
	private java.sql.Time aTime;

	public ObjectUtilsTest(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(LocaleUtils.BRAZIL);
		junit.textui.TestRunner.run(suite());
	}
	
	public static Test suite() {
		return new TestSuite(ObjectUtilsTest.class);
	}
	
	public void setUp() {
		Calendar c = Calendar.getInstance();

		// inicializa o objeto de data para teste
		c.clear();
		c.set(Calendar.DAY_OF_MONTH, 7);
		c.set(Calendar.MONTH, 6);
		c.set(Calendar.YEAR, 1972);
		aDate = new java.sql.Date(c.getTime().getTime());

		// inicializa o objeto de hora
		c.clear();
		c.set(Calendar.HOUR_OF_DAY, 15);
		c.set(Calendar.MINUTE, 45);
		c.set(Calendar.SECOND, 22);
		aTime = new java.sql.Time(c.getTime().getTime());
	}
	
	public void testApplyModifier() {
		assertEquals(Integer.valueOf(10), BeanToys.modify(Integer.valueOf(5), "inc:5"));
		assertEquals(Integer.valueOf(-2), BeanToys.modify(Integer.valueOf(4), "inc:-6"));
		assertEquals(new Float(1.1), BeanToys.modify(new Float(1), "inc:0.1"));
		assertEquals(new Float(1), BeanToys.modify(new Float(1.1), "inc:-0.1"));
		assertEquals("ChuckWalla", BeanToys.modify("Chuck", "concat:Walla"));
		assertEquals("ChuckWalla", BeanToys.modify("Walla", "concatBefore:Chuck"));

		Calendar c = Calendar.getInstance();

		// testes com a data

		c.setTime(aDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		assertEquals(c.getTime(), BeanToys.modify(aDate, "timeIncDays:-6"));

		c.setTime(aDate);
		c.set(Calendar.MONTH, 10);
		assertEquals(c.getTime(), BeanToys.modify(aDate, "timeIncMonths:4"));

		c.setTime(aDate);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		assertEquals(c.getTime(), BeanToys.modify(aDate, "timeIncYears:-1"));

		// virando o m�s
		c.setTime(aDate);
		c.set(Calendar.DAY_OF_MONTH, 30);
		c.set(Calendar.MONTH, 5);
		assertEquals(c.getTime(), BeanToys.modify(aDate, "timeIncDays:-7"));

		// testes com a hora

		c.setTime(aTime);
		c.set(Calendar.SECOND, 0);
		assertEquals(c.getTime(), BeanToys.modify(aTime, "timeIncSeconds:-22"));

		c.setTime(aTime);
		c.set(Calendar.MINUTE, 50);
		assertEquals(c.getTime(), BeanToys.modify(aTime, "timeIncMinutes:5"));

		c.setTime(aTime);
		c.set(Calendar.HOUR_OF_DAY, 16);
		assertEquals(c.getTime(), BeanToys.modify(aTime, "timeIncHours:1"));
	}
	
	public void testFormatObject() {
		assertEquals("07/07/1972", BeanToys.formatObject(aDate, "dd/MM/yyyy"));
		assertEquals("15:45:22", BeanToys.formatObject(aTime, "HH:mm:ss"));
		assertEquals("01", BeanToys.formatObject(Integer.valueOf(1), "00"));
		assertEquals("1,1", BeanToys.formatObject(new Float(1.1), "0.0"));
	}
}