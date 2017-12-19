package toys.tests.junit;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import toys.CollectionToys;

public class CollectionToysTest {
    private List<Integer> lista;

    @Before
    public void setUp() throws Exception {
        lista = Arrays.asList(0, 1, 2, 3, 4, 5);
    }

    @Test
    public void testFragmentar() {
        List<List<Integer>> listas = CollectionToys.fragmentar(lista, 3);
        assertEquals(listas.size(), 2);
        assertEquals(listas.get(0).size(), 3);
        assertEquals(listas.get(1).size(), 3);
    }

    @Test
    public void testToStringList() {
        assertEquals(CollectionToys.toString(lista), "0, 1, 2, 3, 4, 5");
        assertEquals(CollectionToys.toString(lista, "-"), "0-1-2-3-4-5");
        assertEquals(CollectionToys.toString(lista, "-", "'"), "'0'-'1'-'2'-'3'-'4'-'5'");
    }

}
