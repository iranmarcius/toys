/*
 * Criado em 09/10/2008
 */

package toys.fs;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Implementação básica para classes que processam arquivos texto linha a linha.
 * @author Iran
 * @deprecated Utilizar classes do commons-io.
 */
public abstract class TextFileProcessor {
	protected String path;
	protected LineNumberReader reader;
	protected String encoding;

	/**
	 * Cria uma instância da classe setando o caminho para o arquivo que será lido.
	 * @param path Caminho do arquivo
	 */
	public TextFileProcessor(String path) {
		super();
		this.path = path;
	}

	public TextFileProcessor(String path, String encoding) {
		this(path);
		this.encoding = encoding;
	}

	/**
	 * Lê todas as linhas do arquivo texto invocando o método de processamento para cada uma.
	 * @param caminhoArquivo Caminho do arquivo a ser processado.
	 * @throws Exception
	 */
	public void processFile() throws Exception {
		if (encoding == null)
			reader = new LineNumberReader(new FileReader(path));
		else
			reader = new LineNumberReader(new InputStreamReader(new FileInputStream(path), encoding));

		try {
			String linha = null;
			while ((linha = reader.readLine()) != null)
				processLine(linha);
		} catch (Exception e) {
			System.err.printf("Erro processando a linha %d do arquivo %s.%n", reader.getLineNumber(), path);
			throw e;
		} finally {
			FileToys.closeQuiet(reader);
		}
	}

	/**
	 * Pula um determinado número de linhas.
	 * @param lines Número de linhas a serem ignoradas.
	 * @throws IOException
	 */
	protected void skip(int lines) throws IOException {
		for (int i = 1; i <= lines; i++)
			reader.readLine();
	}

	/**
	 * Meste método deve ser implementado o código específico do processamento da linha
	 * de acordo com o relatório que estiver sendo processado.
	 * @param linha String da linha a ser processada
	 * @throws Exception
	 */
	protected abstract void processLine(String linha) throws Exception;

}
