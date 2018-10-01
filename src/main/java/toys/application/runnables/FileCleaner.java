package toys.application.runnables;

import java.io.File;
import java.io.FileFilter;

/**
 * Esta classe apaga apaga arquivos de tempos em tempos nos locais específicos.
 * Ela deve ser utilizada dentro de uma <i>thread</i> para que seja executada em background
 * na aplicação
 */
public final class FileCleaner extends ToyRunnable {

	/**
	 * Especifica uma expressão regular que definirá quais arquivos serão analisados.
	 */
	private String fileSpec;

	/**
	 * Idade limite do arquivo ou diretório. Caso o arquivo seja mais antigo que o tempo indicado
	 * nesta propriedade, ele será apagado. O valor padrão é de 1800000ms (meia hora).
	 */
	private int maxAge = 1800000;

	/**
	 * Especifica o diretório base a partir do qual os arquivos serão analisados.
	 */
	private String baseDir;

	/**
	 * Cria nova instância desta classe inicializando algumas propriedades.
	 * @param baseDir Diretório base
	 * @param fileSpec Especificação dos arquivos
	 */
	public FileCleaner(String baseDir, String fileSpec) {
		super();
		this.baseDir = baseDir;
		this.fileSpec = fileSpec;
	}

	/**
	 * Cria uma nova instância da classe inicializando algumas propriedades.
	 * @param baseDir Diretório base
	 * @param fileSpec Especificação dos arquivos
	 * @param interval Intervalo de execução
	 */
	public FileCleaner(String baseDir, String fileSpec, int interval) {
		this(baseDir, fileSpec);
		this.intervalo = interval;
	}

	/**
	 * Cria uma nova instância da classe inicializando algumas propriedades.
	 * @param baseDir Diretório base
	 * @param fileSpec Especificação dos arquivos
	 * @param interval Intervalo de execução
	 * @param maxAge Idade máxima dos arquivos
	 */
	public FileCleaner(String baseDir, String fileSpec, int interval, int maxAge) {
		this(baseDir, fileSpec, interval);
		this.maxAge = maxAge;
	}

	/**
	 * @see toys.application.runnables.ToyRunnable#execute()
	 */
	@Override
	protected void execute() {
		clean();
	}

	/**
	 * Verifica e apaga os arquivos e diretórios que devem ser apagados
	 */
	private void clean() {
		File base = new File(baseDir);
		if (!base.exists() || !base.isDirectory()) return;

		// obtém uma lista dos arquivos que atendem aos critérios especificados
		File[] f = base.listFiles(
			new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getAbsolutePath().matches(fileSpec);
				}
			}
		);

		// verifica quais dos arquivos da lista ultrapassa a idade limite.
		for (int i = 0; i < f.length; i++)
			if ((System.currentTimeMillis() - f[i].lastModified()) > maxAge) remove(f[i]);
	}

	/**
	 * Remove o arquivo informado. Caso seja um diretório, remove todos os arquivos existentes nele e
	 * depois o próprio diretório.
	 * @param f Arquivo
	 * @return <code>boolean</code>
	 */
	private boolean remove(File f) {
		if (f.isDirectory()) {
			File[] a = f.listFiles();
			for (int i = 0; i < a.length; i++) remove(a[i]);
		}
		return f.delete();
	}

}
