package toys.fs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Métodos utilitários para operações envolvendo o sistema de arquivos.
 * @author Iran Marcius
 */
public class FileToys {

	/**
	 * Retorna o nome do arquivo sem a extensão.
	 * @param file Objeto de onde o nome do arquivo será obtido
	 * @return <code>String</code>
	 */
	public static String extractFilenameNoExt(File file) {
		String s = file.getName();
		int i = s.lastIndexOf('.');
		return i > -1 ? s.substring(0, i) : s;
	}

	/**
	 * @see #extractFilenameNoExt(File)
	 */
	public static String extractFilenameNoExt(String path) {
		return extractFilenameNoExt(new File(path));
	}

	/**
	 * Retorna a porção de um caminho que representa a extensão de um arquivo.
	 * @param file Objeto de arquivo de onde o nome será obtido
	 * @return <code>String</code>
	 */
	public static String extractExtension(File file) {
		String s = file.getName();
		int i = s.lastIndexOf('.');
		return i > -1 ? s.substring(i) : "";
	}

	/**
	 * @see #extractExtension(File)
	 */
	public static String extractExtension(String path) {
		return extractExtension(new File(path));
	}

	/**
	 * Retorna um array de bytes contruído a partir do arquivo especificado.
	 * @param f Arquivo que será lido
	 * @return <code>byte[]</code>
	 * @throws IOException
	 */
	public static byte[] readBytes(File f) throws IOException {
		byte[] b = null;
		FileInputStream in = new FileInputStream(f);
		try {
			b = new byte[in.available()];
			in.read(b);
		} finally {
			in.close();
		}
		return b;
	}

	/**
	 * @see #readBytes(File)
	 */
	public static byte[] readBytes(String filename) throws IOException {
		return readBytes(new File(filename));
	}

	/**
	 * Grava os bytes do array no arquivo informado.
	 * @param f Arquivo onde serão gravados os bytes
	 * @param data Array com os bytes que serão gravados
	 */
	public static void writeBytes(File f, byte[] data) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			out.write(data);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * Grava os bytes do array no arquivo informado.
	 * @param filename Nome do arquivo
	 * @param data Array de bytes que serão gravados
	 * @throws IOException
	 */
	public static void writeBytes(String filename, byte[] data) throws IOException {
		writeBytes(new File(filename), data);
	}

	/**
	 * Método de conveniência para retornar o conteúdo de um arquivo em forma
	 * de uma String.
	 * @param f Arquivo
	 * @return <code>String</code>
	 * @throws IOException
	 */
	public static String readText(File f) throws IOException {
		return new String(readBytes(f));
	}

	/**
	 * Método de conveniência para retornar o conteúdo de um arquivo como string levando em consideração
	 * a codificação do arquivo lido.
	 * @param filename Nome do arquivo.
	 * @param encoding Codificação.
	 * @return <code>String</code>
	 * @throws IOException
	 */
	public static String readText(String filename, String encoding) throws IOException {
		byte[] bytes = readBytes(filename);
		return encoding != null ? new String(bytes, encoding) : new String(bytes);
	}

	/**
	 * Método de conveniência para retornar o conteúdo de um arquivo como string.
	 * @param filename Nome do arquivo
	 * @return <code>String</code>
	 * @throws IOException
	 */
	public static String readText(String filename) throws IOException {
		return new String(readBytes(filename));
	}

	/**
	 * Retorna todas as linhas de um arquivo na forma de uma lista de strings.
	 * @param filename Nome do arquivo
	 * @param charset charset do arquivo que será lido. Caso seja informado um valor nulo será
	 * utilizado o charset padrão.
	 * @return <code>List&lt;String&gt;</code>
	 * @throws IOException
	 * @deprecated Será removido em favor do <b>commons-io</b>.
	 */
	@SuppressWarnings("resource")
	public static List<String> readLines(String filename, String charset) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		InputStreamReader isr = charset != null ? new InputStreamReader(fis, charset) : new InputStreamReader(fis);
		BufferedReader reader = new BufferedReader(isr);
		List<String> l = new ArrayList<String>();
		try {
			String s = null;
			while ((s = reader.readLine()) != null)
				l.add(s);
		} finally {
			reader.close();
		}
		return l;
	}

	/**
	 * Grava no arquivo especificado as linhas informadas na lista de strings.
	 * @param filename Nome do arquivo que será criado ou atualizado.
	 * @param linhas Lista de strings.
	 * @param append Flag indicando se as linhas devem ser acrescentadas a um arquivo existente ou se
	 * um novo arquivo deve ser criado.
	 * @param charset Charset do arquivo. Caso seja informado um valor nulo será utilizado o charset padrão.
	 * @throws IOException
	 * @deprecated Será removido em favor do <b>commons-io</b>.
	 */
	@SuppressWarnings("resource")
	public static void writeLines(String filename, List<String> linhas, boolean append, String charset) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename, append);
		OutputStreamWriter osw = charset == null ? new OutputStreamWriter(fos) : new OutputStreamWriter(fos, charset);
		BufferedWriter writer = new BufferedWriter(osw);
		try {
			for (String s: linhas) {
				writer.write(s);
				writer.newLine();
			}
		} finally {
			writer.close();
		}
	}

	/**
	 * Grava o texto fornecido no arquivo.
	 * @param filename Arquivo onde será gravado o texto.
	 * @param text Texto a ser gravado no arquivo.
	 */
	public static void writeText(String filename, String text) throws IOException  {
		writeBytes(new File(filename), text.getBytes());
	}

	/**
	 * Converte para o valor numérico em bytes uma string representando um tamanho de
	 * arquivo. A string poderá ter os sufixos <b>K</b>, <b>M</b> e <b>G</b>, que
	 * serão considerados, respectivamente, valores especificados em <b>Kbytes</b>,
	 * </b>Megabytes</b> e <b>Gigabytes</b>.
	 * @return Retorna a string convertida ou, caso haja algum erro na conversão,
	 * o valor -1.
	 */
	public static long strSize2bytes(String s) {
		s = s.replace(',', '.').toUpperCase();
		char m = 0;
		if (s.matches("^\\d+(\\.\\d+)?[KMG]$")) {
			 m = s.charAt(s.length() - 1);
			 s = s.replaceAll("[KMG]", "");
		}
		try {
			double v = Double.parseDouble(s);
			switch (m) {
				case 'K': v = v * 1024; break;
				case 'M': v = v * 1024 * 1024; break;
				case 'G': v = v * 1024 * 1024 * 1024; break;
			}
			return (long)v;
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Retorna se um arquivo está com a data atualizada com relação a outro.
	 * @param origin Arquivo original cuja data de modificação servirá como referência para
	 * a verificação
	 * @param target Arquivo que será verificado
	 * @return <code>boolean</code>
	 */
	public static boolean isUpToDate(String origin, String target) {
		File o = new File(origin);
		File t = new File(target);
		if (!o.exists()) return false;
		if (!t.exists()) return false;
		return o.lastModified() <= t.lastModified();
	}

	/**
	 * Retorna ums string formatada com o tamanho de um arquivo.
	 * @param size Tamanho original do arquivo
	 * @return Retorna uma string formatada num do dos seguintes formados:
	 * <b><i>size</i>K</b>, <b><i>size</i>M</b> ou <b><i>size</i>G</b>, de acordo
	 * com o tamanho original informado.
	 * @return <code>String</code>
	 */
	public static String formatFileSize(long size) {
		if (size > 1024 * 1024 * 1024) {
			DecimalFormat df = new DecimalFormat("#0.#G");
			return df.format((double)size / 1024 / 1024 / 1024);
		} else if (size > 1024 * 1024) {
			DecimalFormat df = new DecimalFormat("#0.#M");
			return df.format((double)size / 1024 / 1024);
		} else if (size > 1024) {
			DecimalFormat df = new DecimalFormat("#,##0.#k");
			return df.format((double)size / 1024);
		} else {
			return Long.toString(size);
		}
	}

	/**
	 * Cria uma cópia do arquivo origem para o arquivo destino.
	 * @param origem Referência para o arquivo de origem
	 * @param destino Arquivo de cópia que será criado
	 * @throws IOException
	 */
	public static void copy(File origem, File destino) throws IOException {
		FileInputStream in = new FileInputStream(origem);
		FileOutputStream out = new FileOutputStream(destino);
		byte[] buffer = new byte[10240];
		try {
			while (in.available() > 0) {
				int i = in.read(buffer, 0, buffer.length);
				out.write(buffer, 0, i);
			}
		} finally {
			in.close();
			out.close();
		}
	}


	/**
	 * Método de conveniência para invocar o método {@link #copy(File, File)} passando apenas os
	 * nomes dos arquivos.
	 * @param origem Nome do arquivo a ser copiado
	 * @param destino Nome do arquivo de cópia que será criado
	 * @throws IOException
	 */
	public static void copy(String origem, String destino) throws IOException {
		copy(new File(origem), new File(destino));
	}

	/**
	 * Retorna uma relação de arquivos à partir da especificação.
	 * @param spec Especificação de arquivos
	 * @return <code>{@link File}[]</code>
	 */
	public static File[] listFiles(File spec) {
		File dir = spec.getParentFile();
		final String p = spec.getName()
			.replaceAll("\\.", "\\\\.")
			.replaceAll("\\*", ".*")
			.replaceAll("\\?", ".");
		return dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().matches(p);
			}
		});
	}

	/**
	 * Retorna uma relação de arquivos à partir da especificação.
	 * @param spec Especificação dos arquivos
	 * @return <code>{@link File}[]</code>
	 */
	public static File[] listFiles(String spec) {
		return listFiles(new File(spec));
	}

	/**
	 * Caso a referência para o reader não seja nula, invoca o método close evitando
	 * a exceção.
	 * @param reader Referência para o reader.
	 */
	public static void closeQuiet(Reader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Caso a referência para o writer não seja nula, invoca o método close evitando
	 * a exceção.
	 * @param writer Referência para o writer.
	 */
	public static void closeQuiet(Writer writer) {
		try {
			if (writer != null)
				writer.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Caso a referência para o stream não seja nula, invoca o método close evitando
	 * a exceção.
	 * @param out Referência para o stream.
	 */
	public static void closeQuiet(OutputStream out) {
		try {
			if (out != null)
				out.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Fecha um stream de saída prevenindo a ocorrência de erros.
	 * @param in Referência para o stream.
	 */
	public static void closeQuiet(InputStream in) {
		try {
			if (in != null)
				in.close();
		} catch (IOException e) {
		}
	}

}
