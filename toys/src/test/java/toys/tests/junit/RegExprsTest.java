package toys.tests.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static toys.ToysConsts.*;


class RegExprsTest {

	@Test
	void testCep() {
		assertTrue("16035-460".matches(RE_ZIP));
		assertTrue("16035460".matches(RE_ZIP));
		assertFalse("82713".matches(RE_ZIP));
		assertFalse("2abdc34".matches(RE_ZIP));
		assertFalse("123456789".matches(RE_ZIP));
	}

	@Test
	void testEmail() {
		assertTrue("alguem@dominio.com.br".matches(RE_EMAIL));
		assertTrue("alguem@dominio.br".matches(RE_EMAIL));
		assertTrue("alguem.comsobrenome@dominio.com.br".matches(RE_EMAIL));
		assertTrue("alguem_comsobrenome@dominio.com.br".matches(RE_EMAIL));
		assertTrue("alguem@_dominio.com".matches(RE_EMAIL));
		assertTrue("alguem@dominio_composto.com".matches(RE_EMAIL));
		assertFalse("alguém@dominio.com.br".matches(RE_EMAIL));
		assertFalse("alguem@dominio".matches(RE_EMAIL));
		assertFalse(".alguem@dominio.com.br".matches(RE_EMAIL));
		assertFalse("alguem.@dominio.com.br".matches(RE_EMAIL));
		assertFalse("alguem@.dominio.com.br".matches(RE_EMAIL));
		assertFalse("alguem@dominio.com.".matches(RE_EMAIL));
		assertFalse("alguem com sobrenome@dominio.com.br".matches(RE_EMAIL));
	}

	@Test
	void testPhoneNumber() {
	    assertTrue("18981191920".matches(RE_PHONE_NUMBER));
        assertTrue("1836367000".matches(RE_PHONE_NUMBER));
        assertTrue("18 36367000".matches(RE_PHONE_NUMBER));
        assertTrue("18 3636-7000".matches(RE_PHONE_NUMBER));
        assertTrue("(18) 3636-7000".matches(RE_PHONE_NUMBER));
    }

}
