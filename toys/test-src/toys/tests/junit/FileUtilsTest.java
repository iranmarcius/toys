package toys.tests.junit;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import toys.fs.FileToys;

public class FileUtilsTest extends TestCase {
	private static File f = new File("build.xml");

	public FileUtilsTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(FileUtilsTest.class);
	}

	public void testExtractFilenameWithoutExt() {
		assertEquals("build", FileToys.extractFilenameNoExt(f));
	}

	public void testExtractExtension() {
		assertEquals(".xml", FileToys.extractExtension(f));
	}

	public void testStrSize2bytes() {
		assertEquals(512, FileToys.strSize2bytes("512"));
		assertEquals(1024, FileToys.strSize2bytes("1k"));
		assertEquals(1024 * 1024 * 2, FileToys.strSize2bytes("2m"));
		assertEquals(1024 * 1024 * 1024 * 3, (int)FileToys.strSize2bytes("3g"));
		assertEquals(1024 + 512, FileToys.strSize2bytes("1,5k"));
		assertEquals((long)(1024 * 1.6), FileToys.strSize2bytes("1.6k"));
	}
}