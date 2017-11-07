package toys.application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Altera o classpath da aplicação em tempo de execução.
 * @author Iran
 */
public class ClassPathHacker {

	public void addFile(String s) throws IOException {
		addFile(new File(s));
	}

	public void addFile(File f) throws IOException {
		addURL(f.toURI().toURL());
	}

	private void addURL(URL url) throws IOException {
		URLClassLoader sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> sysClass = URLClassLoader.class;
		try {
			Method method = sysClass.getDeclaredMethod("addURL", new Class[] {URL.class});
			method.setAccessible(true);
			method.invoke(sysLoader, new Object[] {url});
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}
}
