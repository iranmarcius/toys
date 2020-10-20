package toys.tests.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import toys.ArrayToys;

class ArrayToysTest {
    private static Integer[] array;

    @BeforeAll
    public static void setUp() {
        array = new Integer[] {0, 1, 2, 3, 4, 5};
    }

    @Test
    void testToString() {
        Assertions.assertEquals("0, 1, 2, 3, 4, 5", ArrayToys.toString(array));
        Assertions.assertEquals("0-1-2-3-4-5", ArrayToys.toString(array, "-"));
        Assertions.assertEquals("'0'-'1'-'2'-'3'-'4'-'5'", ArrayToys.toString(array, "-", "'"));
    }

}
