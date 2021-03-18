package toys.tests.junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import toys.CollectionToys;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionToysTest {
    private static List<Integer> lista;

    @BeforeAll
    public static void setUp() {
        lista = Arrays.asList(0, 1, 2, 3, 4, 5);
    }

    @Test
    void testFragmentar() {
        List<List<Integer>> listas = CollectionToys.subLists(lista, 3);
        assertEquals(2, listas.size());
        assertEquals(3, listas.get(0).size());
        assertEquals(3, listas.get(1).size());
    }

    @Test
    void testToStringList() {
        assertEquals("0, 1, 2, 3, 4, 5", CollectionToys.asString(lista));
        assertEquals("0-1-2-3-4-5", CollectionToys.asString(lista, "-"));
        assertEquals("'0'-'1'-'2'-'3'-'4'-'5'", CollectionToys.asString(lista, "-", "'"));
    }

}
