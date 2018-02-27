package toys.tests.junit;

import junit.framework.*;

import toys.ToysConsts;

public class RegExprsTest extends TestCase {

	public RegExprsTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(RegExprsTest.class);
		return suite;
	}

	public void testCep() {
		assertTrue("16035-460".matches(ToysConsts.RE_ZIP));
		assertTrue("16035460".matches(ToysConsts.RE_ZIP));
		assertFalse("82713".matches(ToysConsts.RE_ZIP));
		assertFalse("2abdc34".matches(ToysConsts.RE_ZIP));
		assertFalse("123456789".matches(ToysConsts.RE_ZIP));
	}

	public void testEmail() {
		assertTrue("iran@isic.com.br".matches(ToysConsts.RE_EMAIL));
		assertTrue("iran.marcius@isic.com.br".matches(ToysConsts.RE_EMAIL));
		assertTrue("iran_marcius@isic.com.br".matches(ToysConsts.RE_EMAIL));
		assertFalse("iranguimarães@isic.com.br".matches(ToysConsts.RE_EMAIL));
		assertFalse("iran@isic".matches(ToysConsts.RE_EMAIL));
		assertFalse(".iran@isic.com.br".matches(ToysConsts.RE_EMAIL));
		assertFalse("iran.@isic.com.br".matches(ToysConsts.RE_EMAIL));
		assertFalse("iran@.isic.com.br".matches(ToysConsts.RE_EMAIL));
		assertFalse("iran@isic.com.".matches(ToysConsts.RE_EMAIL));
	}

}
