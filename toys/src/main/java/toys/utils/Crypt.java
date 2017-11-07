package toys.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Fornece métodos utilitários para criptografia.
 * @author Iran Marcius
 */
public final class Crypt {
    private static final char CPOS = 'a';
    private static final char CNEG = 'b';
    private static final char HLPOS = 'c';
    private static final char HLNEG = 'd';

    private Crypt() {
        super();
    }

    /**
     * Método utilitário para retornar uma string criptografada com seus bytes
     * convertidos para na representação hexadecimal.
     * @param s String a ser criptografada
     * @param algorithm Algorítmo
     * @return <code>String</code>
     * @see #encrypt(String, String)
     */
    public static String digest(String s, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] b = md.digest(s.getBytes());
        return StringToys.str2hex(b);
    }

    /**
     * Gera uma chave à partir da string a ser criptografada.
     * @param s String
     * @return <code>short</int>
     */
    private static short makeKey(String s) {
        int um = 1;
        short k = 0;
        char[] chars = s.toCharArray();
        for (char c: chars) {
            k += (short)c * um;
            um *= -1;
        }
        return k;
    }

    /**
     * Codifica uma string utilizando o algorítmo proprietário &quot;gorgonzola&quot;.
     * @param string String a ser codificada
     * @param chave Palavra chave
     * @return <code>String</code>
     */
    public static String gorgonzolaEnc(String string, String chave) {
        int r = new Random(System.currentTimeMillis()).nextInt(253) + 2;
        int k = makeKey(chave) - r;
        char[] chars = string.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c: chars) {
            short cs = (short)c;
            cs += k;
            if (NumberToys.inRange(cs, 48, 57) ||
                NumberToys.inRange(cs, 65, 90) ||
                NumberToys.inRange(cs, 101, 122)) {
                sb.append((char)cs);
            } else {
                int ca = Math.abs(cs);
                if (NumberToys.inRange(ca, 0, 255)) {
                    sb.append(cs < 0 ? CNEG : CPOS).append(String.format("%02x", ca));
                } else {
                    int h = ca / 256;
                    int l = ca % 256;
                    sb
                        .append(cs < 0 ? HLNEG : HLPOS)
                        .append(String.format("%02x", h))
                        .append(String.format("%02x", l));
                }
            }
        }

        sb.insert(0, String.format("%x", r / 16));
        sb.append(String.format("%x", r % 16));
        return sb.toString();
    }

    /**
     * Decodifica uma string previamente codificada com o algorítmo proprietário
     * &quot;gorgonzola&quot;.
     * @param string String a ser decodificada
     * @param chave Chave para decodificação
     * @return <code>String</code>
     */
    public static String gorgonzolaDec(String string, String chave) {
        char[] chars = string.toCharArray();
        String rs = String.format("%c%c", chars[0], chars[chars.length - 1]);
        int r = Integer.valueOf(rs, 16);
        int k = makeKey(chave) - r;
        StringBuilder sb = new StringBuilder();
        int i = 1;
        while (i < chars.length - 1) {
            short c = 0;
            if (chars[i] == HLNEG || chars[i] == HLPOS) {
                String hs = String.format("%c%c", chars[i + 2], chars[i + 3]);
                String ls = String.format("%c%c", chars[i + 4], chars[i + 5]);
                short h = Short.valueOf(hs, 16);
                short l = Short.valueOf(ls, 16);
                c = (short)((h * 256) + l);
                if (chars[i + 1] == HLNEG) c *= -1;
                i += 5;
            } else if ((chars[i] == CNEG) || (chars[i] == CPOS)) {
                String hexa = String.format("%c%c", chars[i + 1], chars[i + 2]);
                c = Short.valueOf(hexa, 16);
                if (chars[i] == CNEG) c *= -1;
                i += 2;
            } else {
                c = (short)chars[i];
            }
            c -= k;
            sb.append(Character.toString((char)c));
            i++;
        }
        return sb.toString();
    }

    /**
     * Criotograma uma string utilizando a chave informada e retorna o resultado codificado em Base64.
     * @param valor Valor a ser codificado.
     * @param key Chave que será utilizada na codificação.
     * @return <code>String</code>
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encode(String valor, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(valor.getBytes("UTF-8")));
    }

    /**
     * Descriptograva um valor codificado em Base64 utilizando a chave informada.
     * @param base64 Valor codificado em base64.
     * @param key Chave que será utilizada na decodificação.
     * @return <code>String</code>
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public static String decode(String base64, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(base64)), "UTF-8");
    }

}
