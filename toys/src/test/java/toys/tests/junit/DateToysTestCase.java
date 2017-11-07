/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 * Criado em 07/06/2005 às 10:34:10
 */

package toys.tests.junit;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.lang3.time.DateUtils;

import toys.utils.DateToys;
import toys.utils.LocaleToys;

public class DateToysTestCase extends TestCase {

    public DateToysTestCase(String name) {
        super(name);
        Locale.setDefault(LocaleToys.BRAZIL);
    }

    public static Test suite() {
        return new TestSuite(DateToysTestCase.class);
    }

    public final void testCreateTime() {
        assertEquals("10:15:15", DateToys.createTime("10:15:15").toString());
        assertEquals("10:15:00", DateToys.createTime("10:15").toString());
        assertNull(DateToys.createTime("238:00:00"));
        assertNull(DateToys.createTime("sdjkfhasjkdfhjk"));
    }

    public final void testTime2Ms() {
        assertEquals(36000000l, DateToys.time2ms("10:00:00"));
        assertEquals(36000000l, DateToys.time2ms("10:00"));
    }

    public final void testSetTimeFields() {
        Timestamp d = Timestamp.valueOf("1972-07-07 00:00:00.0");
        Time t = Time.valueOf("10:13:00");
        DateToys.setTimeFields(d, t);
        assertEquals("1972-07-07 10:13:00.0", d.toString());
    }

    public final void testDeltaDays() {
        Date d1 = Date.valueOf("1972-07-07");
        Date d2 = Date.valueOf("1972-07-27");
        assertEquals(20, DateToys.deltaDays(d1, d2, true));

        d2 = Date.valueOf("1973-07-07");
        assertEquals(365, DateToys.deltaDays(d1, d2, false));

        d1 = Date.valueOf("2004-01-01");
        d2 = Date.valueOf("2005-01-01");
        assertEquals(366, DateToys.deltaDays(d1, d2, false));

        d1 = Date.valueOf("1972-07-07");
        d2 = Date.valueOf("1972-07-01");
        assertEquals(6, DateToys.deltaDays(d1, d2, false));
    }

    public final void testToDays() {
        assertEquals(0, DateToys.toDays(10000));
        assertEquals(1, DateToys.toDays(DateUtils.MILLIS_PER_DAY));
        assertEquals(1, DateToys.toDays(DateUtils.MILLIS_PER_DAY + 10000));
    }

    public final void testToHours() {
        assertEquals(0, DateToys.toHours(10000));
        assertEquals(1, DateToys.toHours(DateUtils.MILLIS_PER_HOUR));
        assertEquals(1, DateToys.toHours(DateUtils.MILLIS_PER_HOUR + 10000));
    }

    public final void testToMinutes() {
        assertEquals(0, DateToys.toMinutes(1000));
        assertEquals(1, DateToys.toMinutes(DateUtils.MILLIS_PER_MINUTE));
        assertEquals(1, DateToys.toMinutes(DateUtils.MILLIS_PER_MINUTE + 1000));
    }

    public final void testDates() {
        Date d = Date.valueOf("1972-07-07");
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        assertEquals(1972, c.get(Calendar.YEAR));
        assertEquals(6, c.get(Calendar.MONTH));
        assertEquals(7, c.get(Calendar.DAY_OF_MONTH));
    }

    public final void testFormatPlainTime() {
        assertEquals("", DateToys.formatPlainTime("1111111111", 0));
        assertEquals("12:30", DateToys.formatPlainTime("123023987", 0));
        assertEquals("12:30:23", DateToys.formatPlainTime("123023987", 1));
        assertEquals("12:30:23.987", DateToys.formatPlainTime("123023987", 2));
        assertEquals("12:30", DateToys.formatPlainTime("1230", 2));
    }

    public final void testIntersecao() {
        Date f1d1 = Date.valueOf("1972-07-07");
        Date f1d2 = Date.valueOf("1972-07-31");
        assertFalse(DateToys.intersecao(f1d1, f1d2, Date.valueOf("1972-07-01"), Date.valueOf("1972-07-05")));
        assertTrue(DateToys.intersecao(f1d1, f1d2, Date.valueOf("1972-07-01"), Date.valueOf("1972-07-07")));
        assertTrue(DateToys.intersecao(f1d1, f1d2, Date.valueOf("1972-07-07"), Date.valueOf("1972-07-09")));
        assertTrue(DateToys.intersecao(f1d1, f1d2, Date.valueOf("1972-07-20"), Date.valueOf("1972-08-25")));
        assertFalse(DateToys.intersecao(f1d1, f1d2, Date.valueOf("1972-08-01"), Date.valueOf("1972-08-25")));
    }

    public final void testContido() {
        Date f1d1 = Date.valueOf("1972-07-07");
        Date f1d2 = Date.valueOf("1972-07-31");
        assertFalse(DateToys.contido(Date.valueOf("1972-07-01"), Date.valueOf("1972-07-05"), f1d1, f1d2));
        assertFalse(DateToys.contido(Date.valueOf("1972-07-01"), Date.valueOf("1972-07-07"), f1d1, f1d2));
        assertTrue(DateToys.contido(Date.valueOf("1972-07-07"), Date.valueOf("1972-07-09"), f1d1, f1d2));
        assertFalse(DateToys.contido(Date.valueOf("1972-07-20"), Date.valueOf("1972-08-25"), f1d1, f1d2));
        assertFalse(DateToys.contido(Date.valueOf("1972-08-01"), Date.valueOf("1972-08-25"), f1d1, f1d2));
    }

}
