package toys.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

/**
 * Esta classe encontra diferen�as entre dois diretórios.
 * @author Iran Marcius
 */
public class DirDiff {
	private File leftPath;
	private File rightPath;
	private List<String> leftFiles;
	private List<String> rightFiles;
	private boolean recursive;

	/**
	 * Construtor default.
	 */
	public DirDiff() {
		super();
		leftFiles = new ArrayList<String>();
		rightFiles = new ArrayList<String>();
		recursive = false;
	}

	/**
	 * Construtor.
	 */
	public DirDiff(File leftPath, File rightPath, boolean recursive) {
		this();
		setLeftPath(leftPath);
		setRightPath(rightPath);
		setRecursive(recursive);
		read();
	}

	/**
	 * Construtor.
	 */
	public DirDiff(File leftPath, File rightPath) {
		this(leftPath, rightPath, true);
	}

	/**
	 * Construtor.
	 */
	public DirDiff(String leftPath, String rightPath, boolean recursive) {
		this(new File(leftPath), new File(rightPath), recursive);
	}

	/**
	 * Construtor.
	 */
	public DirDiff(String leftPath, String rightPath) {
		this(new File(leftPath), new File(rightPath), true);
	}

	/**
	 * Lê os diretórios de origem e destino.
	 */
	public void read() {
		leftFiles.clear();
		readDir(leftPath, leftFiles);
		rightFiles.clear();
		readDir(rightPath, rightFiles);
	}

	/**
	 * Retorna os arquivos que existem na lista da direita e não existem na esquerda.
	 * @return <code>List</code>
	 */
	public List<?> getLeftExceeding() {
		List<?> l = subtract(leftFiles, rightFiles);
		final StringBuffer sb = new StringBuffer();
		CollectionUtils.transform(l, new Transformer() {
			public Object transform(Object input) {
				sb.setLength(0);
				sb.append(leftPath.getAbsolutePath()).append(input);
				return sb.toString();
			}
		});
		return l;
	}

	/**
	 * Preenche a lista informada com os nomes de arquivos do caminho especificados
	 * ordenados.
	 * @param path Especificação do caminho
	 * @param fl Referência para a lista de arquivos que será modificada
	 */
	private void readDir(File path, List<String> fl) {
		File[] f = path.listFiles();
		if (f == null) return;
		String pathName = path.getAbsolutePath();
		for (int i = 0; i < f.length; i++) {
			String fn = f[i].getAbsolutePath().substring(pathName.length());
			fl.add(fn);
			if (recursive && f[i].isDirectory()) {
				readDir(f[i], fl);
			}
		}
		Collections.sort(fl);
	}

	/**
	 * Retorna os nomes de arquivos existentes na primeira lista que não possuem
	 * equivalência na segunda.
	 * @param fl1 Primeira lista
	 * @param fl2 Segunda lista
	 * @return List&lt;String&gt;
	 */
	private List<String> subtract(List<String> fl1, List<String> fl2) {
		List<String> diff = new ArrayList<String>();
		for (int i = 0; i < fl1.size(); i++) {
			String s = fl1.get(i);
			int j = Collections.binarySearch(fl2, s);
			if (j < 0) {
				diff.add(s);
			}
		}
		return diff;
	}

	/**
	 * Retorna o caminho destino.
	 * @return String
	 */
	public File getRightPath() {
		return rightPath;
	}

	/**
	 * Retorna o caminho origem.
	 * @return String
	 */
	public File getLeftPath() {
		return leftPath;
	}

	/**
	 * Seta o caminho destino.
	 */
	public void setRightPath(File file) {
		rightPath = file;
	}

	/**
	 * Seta o caminho origem.
	 */
	public void setLeftPath(File file) {
		leftPath = file;
	}

	/**
	 * Retorna a lista de arquivos origem.
	 */
	public List<?> getLeftFiles() {
		return leftFiles;
	}

	/**
	 * Retorna a lista de arquivos destino.
	 * @return <code>{@link java.util.List java.util.List}</code>
	 */
	public List<?> getRightFiles() {
		return rightFiles;
	}

	/**
	 * Retorna se os processos de leitura de diret�rios ir�o agir recursivamente.
	 */
	public boolean isRecursive() {
		return recursive;
	}

	/**
	 * Seta a flag de recursividade para os processos de leitura de diret�rios.
	 */
	public void setRecursive(boolean b) {
		recursive = b;
	}

}
