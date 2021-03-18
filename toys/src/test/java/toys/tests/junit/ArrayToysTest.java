package toys.tests.junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import toys.ArrayToys;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayToysTest {
    private static Integer[] array;

    @BeforeAll
    public static void setUp() {
        array = new Integer[] {0, 1, 2, 3, 4, 5};
    }

    @Test
    void testToString() {
        assertEquals("0, 1, 2, 3, 4, 5", ArrayToys.toString(array));
        assertEquals("0-1-2-3-4-5", ArrayToys.toString(array, "-"));
        assertEquals("'0'-'1'-'2'-'3'-'4'-'5'", ArrayToys.toString(array, "-", "'"));
    }

    @Test
    void testToIntArray() {
        assertArrayEquals(new int[] {1, 2, 3, 4}, ArrayToys.toIntArray(new String[] {"1", "2", "3", "4"}));
    }

}
