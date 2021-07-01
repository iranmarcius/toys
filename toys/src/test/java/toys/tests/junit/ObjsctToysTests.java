package toys.tests.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import toys.ObjectToys;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjsctToysTests {

  @Test
  void testDefaultValue() {
    assertEquals(10, ObjectToys.defaultValue(null, 10));
    assertEquals(10d, ObjectToys.defaultValue(null, 10d));
    assertEquals("a", ObjectToys.defaultValue(null, "a"));
  }

}
