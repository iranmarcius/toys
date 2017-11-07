package toys.tests.junit;

import junit.framework.*;

import toys.constants.RegExprConsts;

public class RegExprsTest extends TestCase {

	public RegExprsTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(RegExprsTest.class);
		return suite;
	}

	public void testCep() {
		assertTrue("16035-460".matches(RegExprConsts.ZIP));
		assertTrue("16035460".matches(RegExprConsts.ZIP));
		assertFalse("82713".matches(RegExprConsts.ZIP));
		assertFalse("2abdc34".matches(RegExprConsts.ZIP));
		assertFalse("123456789".matches(RegExprConsts.ZIP));
	}

	public void testEmail() {
		assertTrue("iran@isic.com.br".matches(RegExprConsts.EMAIL));
		assertTrue("iran.marcius@isic.com.br".matches(RegExprConsts.EMAIL));
		assertTrue("iran_marcius@isic.com.br".matches(RegExprConsts.EMAIL));
		assertFalse("iranguimarães@isic.com.br".matches(RegExprConsts.EMAIL));
		assertFalse("iran@isic".matches(RegExprConsts.EMAIL));
		assertFalse(".iran@isic.com.br".matches(RegExprConsts.EMAIL));
		assertFalse("iran.@isic.com.br".matches(RegExprConsts.EMAIL));
		assertFalse("iran@.isic.com.br".matches(RegExprConsts.EMAIL));
		assertFalse("iran@isic.com.".matches(RegExprConsts.EMAIL));
	}

}
