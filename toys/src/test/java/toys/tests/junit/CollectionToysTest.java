package toys.tests.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import toys.CollectionToys;

import java.util.Arrays;
import java.util.List;

class CollectionToysTest {
    private static List<Integer> lista;

    @BeforeAll
    public static void setUp() {
        lista = Arrays.asList(0, 1, 2, 3, 4, 5);
    }

    @Test
    void testFragmentar() {
        List<List<Integer>> listas = CollectionToys.subLists(lista, 3);
        Assertions.assertEquals(2, listas.size());
        Assertions.assertEquals(3, listas.get(0).size());
        Assertions.assertEquals(3, listas.get(1).size());
    }

    @Test
    void testToStringList() {
        Assertions.assertEquals("0, 1, 2, 3, 4, 5", CollectionToys.asString(lista));
        Assertions.assertEquals("0-1-2-3-4-5", CollectionToys.asString(lista, "-"));
        Assertions.assertEquals("'0'-'1'-'2'-'3'-'4'-'5'", CollectionToys.asString(lista, "-", "'"));
    }

}
