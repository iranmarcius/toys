package toys.tests.junit;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import toys.utils.StringToys;

public class StringToysTestCase extends TestCase {

	public static Test suite() {
		return new TestSuite(StringToysTestCase.class);
	}

	public void testReplacePropertiesStringProperties() {
		String s= "${name} - ${name} - ${year}";
		Properties p = new Properties();
		p.put("name", "Garibaldi");
		p.put("year", "1972");
		s = StringToys.replaceProperties(s, p);
		assertEquals("Garibaldi - Garibaldi - 1972", s);
	}

	public void testSpacesRight() {
		assertEquals(StringToys.spacesRight("teste", 10), "teste     ");
		assertEquals(StringToys.spacesRight("teste", 5), "teste");
		assertEquals(StringToys.zerosLeft(2.3, 5, 2), "00230");
		assertEquals(StringToys.zerosLeft(2, 5), "00002");
	}

}
