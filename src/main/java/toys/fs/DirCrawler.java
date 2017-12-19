/*
 * Criado em 25/11/2008
 */
package toys.fs;

import java.io.File;
import java.io.FileFilter;

/**
 * Esta classe possui uma implementação básica para navegação recursiva por subdiretórios a partir de
 * um diretório raiz informado.
 * @author Iran
 */
public abstract class DirCrawler {
	protected String rootDir;
	private FileFilter directoryFilter;

	/**
	 * Construtor padrão.
	 */
	public DirCrawler() {
		super();
		directoryFilter = new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory();
			}
		};
	}

	/**
	 * Construtor com inicialização de propriedades.
	 * @param rootDir Diretório inicial.
	 */
	public DirCrawler(String rootDir) {
		this();
		this.rootDir = rootDir;
	}

	/**
	 * Navega pelos diretórios a partir do diretório raiz.
	 * @throws Exception
	 */
	protected void crawl() throws Exception {
		crawl(rootDir);
	}

	/**
	 * Seta o diretório raiz e navega todos os subdiretórios a partir dele.
	 * @param rootDir
	 * @throws Exception
	 */
	protected void crawl(String rootDir) throws Exception {
		this.rootDir = rootDir;
		recurseProcess(new File(rootDir), 0);
	}

	/**
	 * Processa o diretório e todos os seus subdiretórios.
	 * @param dir Referência para o diretório inicial.
	 * @param level Nível do diretório que está sendo processado.
	 * @throws Exception
	 */
	private void recurseProcess(File dir, int level) throws Exception {
		if (dir.isDirectory()) {
			process(dir, level);
			File[] subDirs = dir.listFiles(directoryFilter);
			for (File d: subDirs)
				recurseProcess(d, level + 1);
		}
	}

	/**
	 * Método para o processamento do diretório.
	 * @param dir Referência para o diretório a ser processado.
	 * @param level Nível de profundidade do diretório que está sendo processado.
	 */
	protected abstract void process(File dir, int level) throws Exception;

}
