package toys.tests.junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import toys.CollectionToys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

  @Test
  void testAdd() {
    var l = new ArrayList<Integer>();
    Comparator<Integer> comp = Comparator.comparingInt(v -> v);
    CollectionToys.add(7, l, comp);
    CollectionToys.add(5, l, comp);
    CollectionToys.add(10, l, comp);
    CollectionToys.add(6, l, comp);
    CollectionToys.add(6, l, comp);
    assertEquals(Arrays.asList(5, 6, 6, 7, 10), l);
  }

}
