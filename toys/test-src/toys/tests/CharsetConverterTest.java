package toys.tests;

import java.io.IOException;

import toys.fs.FileToys;

public class CharsetConverterTest {

	public static void main(String[] args) throws IOException {
		String text = FileToys.readText("src/toys/tests/ISO-8859-1_file.txt");
		byte[] b = text.getBytes("UTF-8");
		FileToys.writeBytes("src/toys/tests/UTF-8_file.txt", b);
		System.out.println("Feito");
	}

}
