package toys.utils;

import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * Programa para geração de chaves secretas.
 * @author Iran
 */
public class KeyGen {

    private KeyGen() {
        super();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, ParseException {

        Options options = new Options();
        options.addOption("a", "algorithm", true, "Algoritmo");
        options.addOption("s", "size", true, "Tamanho da chave");
        options.addOption("e", "encode", false, "Indica se a chave deve ser codificade em Base64");
        options.addOption("o", "output", true, "Caminho do arquivo no qual a chave será armazenada");
        options.addOption("h", "help", false, "Exibe instrucoes de uso");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (!cmd.hasOption("a") || !cmd.hasOption("s") || cmd.hasOption("h")) {
            HelpFormatter fmt = new HelpFormatter();
            fmt.printHelp("-a <algorítmo> -s <tamanho> [-e] [-o <arquivo de saída>]", options);

            Set<String> algotithms = Security.getAlgorithms("Cipher");
            System.out.println();
            System.out.println("Algorítmos disponíveis:");
            for (String a: algotithms)
                System.out.printf("\t%s%n", a);
            System.exit(0);
        }

        String algotithm = cmd.getOptionValue("a");
        Integer size = Integer.valueOf(cmd.getOptionValue("s"));
        boolean encode = cmd.hasOption("e");
        String output = cmd.getOptionValue("o");

        System.out.println("Gerando chave:");
        System.out.printf("\tAlgorítmo: %s%n", algotithm);
        System.out.printf("\tTamanho: %d%n", size);

        KeyGenerator kg = KeyGenerator.getInstance(algotithm);
        kg.init(size);
        SecretKey key = kg.generateKey();
        byte[] keyBytes = key.getEncoded();
        System.out.print("Chave gerada: ");
        StringBuilder sb = new StringBuilder();
        for (byte b: keyBytes)
            sb.append(b).append(", ");
        sb.setLength(sb.length() - 2);
        System.out.println(sb);


        String base64 = null;
        if (encode) {
            base64 = Base64.getEncoder().encodeToString(keyBytes);
            System.out.printf("Chave codificada: %s%n", base64);
        }

        // Se foi fornecido o caminho de um arquivo de armazenamento, grava a chave nesse arquivo.
        if (StringUtils.isNotBlank(output)) {
            try (FileOutputStream out = new FileOutputStream(output)) {
                byte[] b = base64 == null ? keyBytes : base64.getBytes();
                out.write(b);
                System.out.printf("Chave salva no arquivo %s%n", output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
