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

    public static String getErro() {
        return res.getString("message.erro");
    }

    public static String getTextoErroInterno() {
        return res.getString("message.erroInterno");
    }

    public static String getAviso() {
        return res.getString("message.aviso");
    }

    public static String getValidacao() {
        return res.getString("message.validacao");
    }

    public static String getSucesso() {
        return res.getString("message.sucesso");
    }

    public static String getFalhaAutenticacao() {
        return res.getString("message.falhaAutenticacao");
    }

}
