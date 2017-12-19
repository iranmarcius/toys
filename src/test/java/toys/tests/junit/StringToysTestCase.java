package toys.tests.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import toys.utils.StringToys;

public class StringToysTestCase extends TestCase {

    public static Test suite() {
        return new TestSuite(StringToysTestCase.class);
    }

    public void testSpacesRight() {
        assertEquals(StringToys.spacesRight("teste1", 10), "teste1    ");
        assertEquals(StringToys.spacesRight("teste2", 5), "teste");
        assertEquals(StringToys.zerosLeft(2.3, 5, 2), "00230");
        assertEquals(StringToys.zerosLeft(2, 5), "00002");
    }

}
