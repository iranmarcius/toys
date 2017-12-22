package toys.tests.junit;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import toys.utils.DateToys;
import toys.utils.LocaleToys;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class DateToysTest {

    @BeforeClass
    public static void before() {
        Locale.setDefault(LocaleToys.BRAZIL);
    }

    @Test
    public void testCreateTime() {
        assertEquals("10:15:15", DateToys.createTime("10:15:15").toString());
        assertEquals("10:15:00", DateToys.createTime("10:15").toString());
        assertNull(DateToys.createTime("238:00:00"));
        assertNull(DateToys.createTime("sdjkfhasjkdfhjk"));
    }

    @Test
    public void testTime2Ms() {
        assertEquals(36000000l, DateToys.time2ms("10:00:00"));
        assertEquals(36000000l, DateToys.time2ms("10:00"));
    }

    @Test
    public void testSetTimeFields() {
        Timestamp d = Timestamp.valueOf("1972-07-07 00:00:00.0");
        Time t = Time.valueOf("10:13:00");
        DateToys.setTimeFields(d, t);
        assertEquals("1972-07-07 10:13:00.0", d.toString());
    }

    @Test
    public void testDeltaDays() {
        Date d1 = java.sql.Timestamp.valueOf("1972-07-07 00:00:00.000");
        Date d2 = java.sql.Timestamp.valueOf("1972-07-27 12:00:00.000");
        assertEquals(20, DateToys.deltaDays(d1, d2, true));
        assertEquals(21, DateToys.deltaDays(d1, d2, false));

        d2 = java.sql.Date.valueOf("1973-07-07");
        assertEquals(365, DateToys.deltaDays(d1, d2, false));

        d1 = java.sql.Date.valueOf("2004-01-01");
        d2 = java.sql.Date.valueOf("2005-01-01");
        assertEquals(366, DateToys.deltaDays(d1, d2, false));

        d1 = java.sql.Date.valueOf("1972-07-07");
        d2 = java.sql.Date.valueOf("1972-07-01");
        assertEquals(6, DateToys.deltaDays(d1, d2, false));
    }

    @Test
    public void testToDays() {
        assertEquals(0, DateToys.toDays(10000));
        assertEquals(1, DateToys.toDays(DateUtils.MILLIS_PER_DAY));
        assertEquals(1, DateToys.toDays(DateUtils.MILLIS_PER_DAY + 10000));
    }

    @Test
    public void testToHours() {
        assertEquals(0, DateToys.toHours(10000));
        assertEquals(1, DateToys.toHours(DateUtils.MILLIS_PER_HOUR));
        assertEquals(1, DateToys.toHours(DateUtils.MILLIS_PER_HOUR + 10000));
    }

    @Test
    public void testToMinutes() {
        assertEquals(0, DateToys.toMinutes(1000));
        assertEquals(1, DateToys.toMinutes(DateUtils.MILLIS_PER_MINUTE));
        assertEquals(1, DateToys.toMinutes(DateUtils.MILLIS_PER_MINUTE + 1000));
    }

    @Test
    public void testFormatPlainTime() {
        assertEquals("", DateToys.formatPlainTime("1111111111", 0));
        assertEquals("12:30", DateToys.formatPlainTime("123023987", 0));
        assertEquals("12:30:23", DateToys.formatPlainTime("123023987", 1));
        assertEquals("12:30:23.987", DateToys.formatPlainTime("123023987", 2));
        assertEquals("12:30", DateToys.formatPlainTime("1230", 2));
    }

    @Test
    public void testInterseccao() {
        Date f1d1 = java.sql.Date.valueOf("1972-07-07");
        Date f1d2 = java.sql.Date.valueOf("1972-07-31");
        assertFalse(DateToys.interseccao(f1d1, f1d2, java.sql.Date.valueOf("1972-07-01"), java.sql.Date.valueOf("1972-07-05")));
        assertTrue(DateToys.interseccao(f1d1, f1d2, java.sql.Date.valueOf("1972-07-01"), java.sql.Date.valueOf("1972-07-07")));
        assertTrue(DateToys.interseccao(f1d1, f1d2, java.sql.Date.valueOf("1972-07-07"), java.sql.Date.valueOf("1972-07-09")));
        assertTrue(DateToys.interseccao(f1d1, f1d2, java.sql.Date.valueOf("1972-07-20"), java.sql.Date.valueOf("1972-08-25")));
        assertFalse(DateToys.interseccao(f1d1, f1d2, java.sql.Date.valueOf("1972-08-01"), java.sql.Date.valueOf("1972-08-25")));
    }

    @Test
    public void testContido() {
        Date f1d1 = java.sql.Date.valueOf("1972-07-07");
        Date f1d2 = java.sql.Date.valueOf("1972-07-31");
        assertFalse(DateToys.contido(java.sql.Date.valueOf("1972-07-01"), java.sql.Date.valueOf("1972-07-05"), f1d1, f1d2));
        assertFalse(DateToys.contido(java.sql.Date.valueOf("1972-07-01"), java.sql.Date.valueOf("1972-07-07"), f1d1, f1d2));
        assertTrue(DateToys.contido(java.sql.Date.valueOf("1972-07-07"), java.sql.Date.valueOf("1972-07-09"), f1d1, f1d2));
        assertFalse(DateToys.contido(java.sql.Date.valueOf("1972-07-20"), java.sql.Date.valueOf("1972-08-25"), f1d1, f1d2));
        assertFalse(DateToys.contido(java.sql.Date.valueOf("1972-08-01"), java.sql.Date.valueOf("1972-08-25"), f1d1, f1d2));
    }

}
