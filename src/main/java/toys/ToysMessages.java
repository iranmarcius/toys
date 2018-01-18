package toys;

import java.util.ResourceBundle;

/**
 * Acesso a mensagens.
 */
public final class ToysMessages {
    private static final ResourceBundle res = ResourceBundle.getBundle("messages");

    private ToysMessages() {
        super();
    }

    public static String erro() {
        return res.getString("message.erro");
    }

    public static String erroInterno() {
        return res.getString("message.erroInterno");
    }

    public static String aviso() {
        return res.getString("message.aviso");
    }

    public static String validacao() {
        return res.getString("message.validacao");
    }

    public static String sucesso() {
        return res.getString("message.sucesso");
    }

    public static String falhaAutenticacao() {
        return res.getString("message.falhaAutenticacao");
    }

    public static String informacao() {
        return res.getString("message.informacao");
    }

}
