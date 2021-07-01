package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.NumberToys;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberToysTests {

  @Test
  void testToDouble() {
    assertEquals(Double.valueOf(.34d), NumberToys.toDouble(",34"));
    assertEquals(Double.valueOf(.34), NumberToys.toDouble(".34"));
    assertEquals(Double.valueOf(1000.34), NumberToys.toDouble("1000.34"));
    assertEquals(Double.valueOf(1000.34), NumberToys.toDouble("1.000,34"));
    assertEquals(Double.valueOf(12345.34), NumberToys.toDouble("12.345,34"));
  }

  @Test
  void testSum() {
    assertEquals(10, NumberToys.sum(5, 5));
    assertEquals(10, NumberToys.sum(10, null));
    assertEquals(10, NumberToys.sum(null, 10));
    assertEquals(0, NumberToys.sum((Integer) null, null));
    assertEquals(10d, NumberToys.sum(5d, 5d));
    assertEquals(10d, NumberToys.sum(10d, null));
    assertEquals(10d, NumberToys.sum(null, 10d));
    assertEquals(0d, NumberToys.sum((Double) null, null));
  }

}
