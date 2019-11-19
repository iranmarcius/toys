package toys;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;

import java.nio.charset.StandardCharsets;

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
	 * Cria um e-mail html utilizando os parâmetros informados.
	 * @param hostname Host SMTP que será utilizado para envio do e-mail.
	 * @param from Nome e e-mai do remetente.
	 * @param to Email do destinatário.
	 * @param toName Nome do destinatário. Não é obrigatório.
	 * @param subject Assunto da mensagem.
	 * @param content Conteúdo textual da mensagem.
	 */
    public static synchronized HtmlEmail criarEmailHtml(String hostname, String from, String to, String toName, String subject, String content) throws EmailException {
    	HtmlEmail email = new HtmlEmail();
		email.setHostName(hostname);
		if (StringUtils.isNotBlank(toName))
			email.addTo(to, toName, StandardCharsets.UTF_8.toString());
		else
			email.addTo(to);
		email.setFrom(from);
		email.setCharset(StandardCharsets.UTF_8.toString());
		email.setSubject(subject);
		email.setHtmlMsg(content);
		return email;
	}


    /**
     * Método de conveniência para criar e enviar um e-mail html com os parâmetros informados retornando se houve sucesso na operação ou não.
	 * @see #criarEmailHtml(String, String, String, String, String, String)
     */
    public static synchronized boolean enviarEmailHtml(String hostname, String from, String to, String toName, String subject, String content) {
        try {
            HtmlEmail email = criarEmailHtml(hostname, from, to, toName, subject, content);
            email.send();
            LogManager.getFormatterLogger(EmailUtils.class).debug("Email enviado para %s (%s).", to, toName);
            return true;
        } catch (Exception e) {
            LogManager.getFormatterLogger(EmailUtils.class).fatal("Erro enviando email para %s, (%s).", to, toName, e);
            return false;
        }
    }

}
