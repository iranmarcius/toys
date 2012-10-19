package toys.tests;

import toys.collections.ArrayToys;

public class ArrayToysTest {

	public static void main(String[] args) {
		String[] a = new String[] {"a", "b", "c", "d", "e", "f", "g"};
		ArrayToys.shiftRight(a, 3);
		String[] b = new String[] {"a", "b", "c", "d", "e", "f", "g"};
		ArrayToys.shiftLeft(b, 3);
		System.out.println("Fim");
	}

}
