
package toys.tests.junit;

import org.junit.Test;
import toys.NumberToys;

import static org.junit.Assert.assertEquals;

public class NumberToysTestCase {

	@Test
	public void testToDouble() {
		assertEquals(Double.valueOf(.34d), NumberToys.toDouble(",34"));
		assertEquals(Double.valueOf(.34), NumberToys.toDouble(".34"));
		assertEquals(Double.valueOf(1000.34), NumberToys.toDouble("1000.34"));
		assertEquals(Double.valueOf(1000.34), NumberToys.toDouble("1.000,34"));
		assertEquals(Double.valueOf(12345.34), NumberToys.toDouble("12.345,34"));
	}

}
