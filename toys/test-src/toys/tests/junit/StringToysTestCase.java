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

	public void testPrimeiraLetra() {
		assertEquals(StringToys.primeiraLetraSemAcento("Âasdhfdks"), "A");
		assertEquals(StringToys.primeiraLetraSemAcento("Áasdhfdks"), "A");
		assertEquals(StringToys.primeiraLetraSemAcento("Àasdhfdks"), "A");
		assertEquals(StringToys.primeiraLetraSemAcento("Ãasdhfdks"), "A");
		assertEquals(StringToys.primeiraLetraSemAcento("Éasdhfdks"), "E");
		assertEquals(StringToys.primeiraLetraSemAcento("Èasdhfdks"), "E");
		assertEquals(StringToys.primeiraLetraSemAcento("Êasdhfdks"), "E");
		assertEquals(StringToys.primeiraLetraSemAcento("Íasdhfdks"), "I");
		assertEquals(StringToys.primeiraLetraSemAcento("Ìasdhfdks"), "I");
		assertEquals(StringToys.primeiraLetraSemAcento("Îasdhfdks"), "I");
		assertEquals(StringToys.primeiraLetraSemAcento("Óasdhfdks"), "O");
		assertEquals(StringToys.primeiraLetraSemAcento("Òasdhfdks"), "O");
		assertEquals(StringToys.primeiraLetraSemAcento("Õasdhfdks"), "O");
		assertEquals(StringToys.primeiraLetraSemAcento("Ôasdhfdks"), "O");
		assertEquals(StringToys.primeiraLetraSemAcento("Úasdhfdks"), "U");
		assertEquals(StringToys.primeiraLetraSemAcento("Ùasdhfdks"), "U");
		assertEquals(StringToys.primeiraLetraSemAcento("Ûasdhfdks"), "U");
	}

	public void testSpacesRight() {
		assertEquals(StringToys.spacesRight("teste", 10), "teste     ");
		assertEquals(StringToys.spacesRight("teste", 5), "teste");
		assertEquals(StringToys.zerosLeft(2.3, 5, 2), "00230");
		assertEquals(StringToys.zerosLeft(2, 5), "00002");
	}

}
