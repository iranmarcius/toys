
package toys.tests.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import toys.utils.NumberToys;

public class NumberToysTestCase {

	@Test
	public void testToDouble() {
		assertEquals(new Double(.34), NumberToys.toDouble(",34"));
		assertEquals(new Double(.34), NumberToys.toDouble(".34"));
		assertEquals(new Double(1000.34), NumberToys.toDouble("1000.34"));
		assertEquals(new Double(1000.34), NumberToys.toDouble("1.000,34"));
		assertEquals(new Double(12345.34), NumberToys.toDouble("12.345,34"));
	}

}
