package toys.tests.junit;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Esta classe executa todos os testes do pacote atual.
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Todos os testes");
		suite.addTest(FileUtilsTest.suite());
		suite.addTest(RegExprsTest.suite());
		suite.addTest(StringToysTestCase.suite());
		return suite;
	}

}