package toys.tests.junit;

import org.junit.Test;
import toys.StringToys;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringToysTestCase {

    @Test
    public void testSpacesRight() {
        assertEquals(StringToys.spacesRight("teste1", 10), "teste1    ");
        assertEquals(StringToys.spacesRight("teste2", 5), "teste");
        assertEquals(StringToys.zerosLeft(2.3, 5, 2), "00230");
        assertEquals(StringToys.zerosLeft(2, 5), "00002");
    }

}
