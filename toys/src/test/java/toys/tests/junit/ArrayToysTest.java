package toys.tests.junit;

import org.junit.Before;
import org.junit.Test;
import toys.ArrayToys;

import static org.junit.Assert.assertEquals;

public class ArrayToysTest {
    private Integer[] array;

    @Before
    public void setUp() throws Exception {
        array = new Integer[] {0, 1, 2, 3, 4, 5};
    }

    @Test
    public void testToString() {
        assertEquals(ArrayToys.toString(array), "0, 1, 2, 3, 4, 5");
        assertEquals(ArrayToys.toString(array, "-"), "0-1-2-3-4-5");
        assertEquals(ArrayToys.toString(array, "-", "'"), "'0'-'1'-'2'-'3'-'4'-'5'");
    }

}
