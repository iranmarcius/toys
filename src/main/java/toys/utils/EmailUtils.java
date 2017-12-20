package toys.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;

/**
 * Classe com métodos utilitários e constantes utilizadas no envio de e-mails.
 * @author Iran
 */
public final class EmailUtils {
    public static final String STORE_PROTOCOL = "mail.store.protocol";
    public static final String TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String HOST = "mail.host";
    public static final String USER = "mail.user";
    public static final String FROM = "mail.from";
    public static final String DEBUG = "mail.debug";
    public static final String AUTH_SMTP = "mail.smtp.auth.login.disable";

    private EmailUtils() {
        super();
    }

    /**
     * Método utilitário par aenvio de emails de uma forma padronizada.
     * @param from Remetente.
     * @param to Email do destinatário.
     * @param toName Nome do destinatário. Não é obrigatório.
     * @param subject Assunto da mensagem.
     * @param content Conteúdo textual da mensagem.
     */
    public static synchronized boolean enviarEmailHtml(String from, String to, String toName, String subject, String content) {
        try {
            HtmlEmail email = new HtmlEmail();
            if (StringUtils.isNotBlank(toName))
                email.addTo(to, toName, "iso-8859-1");
            else
                email.addTo(to);
            email.setFrom(from);
            email.setCharset("utf-8");
            email.setSubject(subject);
            email.setHtmlMsg(content);
            email.setMailSessionFromJNDI(JNDIToys.DEFAULT_MAIL_SESSION_PATH);
            email.send();
            LogManager.getFormatterLogger().debug("Email enviado para %s (%s).", to, toName);
            return true;
        } catch (Exception e) {
            LogManager.getFormatterLogger().fatal("Erro enviando email para %s, (%s).", to, toName, e);
            return false;
        }
    }

}
