/*
 * Criado em 25/11/2008
 */
package toys;

import toys.exceptions.ToysException;

import java.io.File;
import java.io.FileFilter;

/**
 * Esta classe possui uma implementação básica para navegação recursiva por subdiretórios a partir de
 * um diretório raiz informado.
 *
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
        directoryFilter = File::isDirectory;
    }

    /**
     * Construtor com inicialização de propriedades.
     *
     * @param rootDir Diretório inicial.
     */
    public DirCrawler(String rootDir) {
        this();
        this.rootDir = rootDir;
    }

    /**
     * Navega pelos diretórios a partir do diretório raiz.
     */
    protected void crawl() throws ToysException {
        crawl(rootDir);
    }

    /**
     * Seta o diretório raiz e navega todos os subdiretórios a partir dele.
     *
     * @param rootDir Caminho do diretório raiz para a operação.
     */
    protected void crawl(String rootDir) throws ToysException {
        this.rootDir = rootDir;
        recurseProcess(new File(rootDir), 0);
    }

    /**
     * Processa o diretório e todos os seus subdiretórios.
     *
     * @param dir   Referência para o diretório inicial.
     * @param level Nível do diretório que está sendo processado.
     */
    private void recurseProcess(File dir, int level) throws ToysException {
        if (dir.isDirectory()) {
            process(dir, level);
            File[] subDirs = dir.listFiles(directoryFilter);
            if (subDirs != null) {
                for (File d : subDirs)
                    recurseProcess(d, level + 1);
            }
        }
    }

    /**
     * Método para o processamento do diretório.
     *
     * @param dir   Referência para o diretório a ser processado.
     * @param level Nível de profundidade do diretório que está sendo processado.
     */
    protected abstract void process(File dir, int level) throws ToysException;

}
