package toys.tests.junit;

import org.junit.Before;
import org.junit.Test;
import toys.CollectionToys;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CollectionToysTest {
    private List<Integer> lista;

    @Before
    public void setUp() {
        lista = Arrays.asList(0, 1, 2, 3, 4, 5);
    }

    @Test
    public void testFragmentar() {
        List<List<Integer>> listas = CollectionToys.fragmentar(lista, 3);
        assertEquals(2, listas.size());
        assertEquals(3, listas.get(0).size());
        assertEquals(3, listas.get(1).size());
    }

    @Test
    public void testToStringList() {
        assertEquals("0, 1, 2, 3, 4, 5", CollectionToys.asString(lista));
        assertEquals("0-1-2-3-4-5", CollectionToys.asString(lista, "-"));
        assertEquals("'0'-'1'-'2'-'3'-'4'-'5'", CollectionToys.asString(lista, "-", "'"));
    }

}
