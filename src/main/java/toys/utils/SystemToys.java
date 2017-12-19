/*
 * Criado em 06/06/2007 09:44:30
 */

package toys.utils;

/**
 * Esta classe possui métodos utilitários para interagir com aspectos relacionados ao ambiente
 * no qual uma aplicação está sendo executada.
 * @author Iran
 */
public class SystemToys {

    private SystemToys() {
        super();
    }

    /**
     * Remove o caractere separador de caminhos caso a string termina com ele.
     * @param value Caminho
     * @return <code>String</code>
     */
    private static String removerSeparadorFinal(String value) {
        return value.endsWith("/") || value.endsWith("\\") ? value.substring(0, value.length() - 1) : value;
    }

    /**
     * Retorna o caractere utilizado como separador de caminhos.
     * @return <code>String</code>
     */
    public static String getPathSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * Retorna a codificação default da plataforma.
     * @return <code>String</code>
     */
    public static String getFileEncoding() {
        return System.getProperty("file.encoding");
    }

    /**
     * Retorna o separador de linha do sistema.
     * @return <code>String</code>
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * Retorna o diretório do usuário.
     * @return <code>String</code>
     */
    public static String getUserHome() {
        return removerSeparadorFinal(System.getProperty("user.home"));
    }

    /**
     * Retorna o caminho do diretório temporário do sistema.
     * @return <code>String</code>
     */
    public static String getTempDir() {
        return removerSeparadorFinal(System.getProperty("java.io.tmpdir"));
    }

}
