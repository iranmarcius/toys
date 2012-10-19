package toys.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import toys.beans.pairs.StringPair;
import toys.fs.FileToys;

/**
 * Métodos utilitários para operações com e-mails.
 * @author Iran Marcius
 * @deprecated Utilizar commons-email.
 */
public class EmailUtils {
	private static Log log = LogFactory.getLog(EmailUtils.class);

	/**
	 * Retorna se é possível autenticar com um servidor de armazenamento de mensagens utilizando
	 * o nome de usuário e a senha informados.
	 * @param username Nome de usuário.
	 * @param password Senha da conta de e-mail.
	 * @param host Endereço do servidor de armazenamento de mensagens.
	 * @param protocol Protocolo a ser utilizado.
	 * @return <code>boolean</code>
	 */
	public static boolean authenticate(Session session, String username, String password, String host, String protocol) throws Exception {
		Store store = null;
		boolean exists = false;
		try {
			store = session.getStore(protocol);
			store.connect(host, username, password);
			exists = true;
		} catch (MessagingException e) {
			log.warn(String.format("Erro autenticando no servidor de e-mail. " +
					"username=%s, host=%s, protocol=%s", username, host, protocol), e);
		} finally {
			if (store != null)
				store.close();
		}
		return exists;
	}

	/**
	 * Converte o array de strings em um array de objetos do tipo {@link Address}.
	 * @param  a Array de strings com os endereços de e-mail
	 * @return {@link Address}[]
	 * @throws AddressException
	 */
	public static Address[] parseAddress(List<String> a) throws AddressException {
		if ((a == null) || a.isEmpty())
			return null;
		Address[] addresses = new Address[a.size()];
		for (int i = 0; i < a.size(); i++)
			addresses[i] = new InternetAddress(a.get(i));
		return addresses;
	}

	/**
	 * Envia um e-mail de texto simples.
	 * @param session Sessão de e-mail.
	 * @param from Remetente.
	 * @param to Destinatário.
	 * @param subject Assunto.
	 * @param body Corpo da mensagem.
	 * @param headers Cabeçalhos extras.
	 * @throws MessagingException
	 */
	public static void send(Session session, String from, String to, String subject, String body, StringPair... headers) throws MessagingException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(body);
		if (headers != null)
			for (StringPair p: headers)
				message.addHeader(p.getKey(), p.getValue());
		Transport.send(message);
	}

	/**
	 * Cria uma mensagem de e-mail.
	 * @param session Sessão de e-mail.
	 * @param subject Assunto
	 * @param body Corpo da mensagem. Pode ser um texto puro ou um código HTML.
	 * @param encoding Codificação da mensagem.
	 * @param mimeType Tipo MIME do corpo da mensagem. Caso se trate de uma mensagem no
	 * formato HTML, o corpo será processado e todas as imagens vinculadas serão
	 * anexadas à mensagem para visualização inline.
	 * @param attachments relação opcional de anexos. Caso não haja nenhum este parâmetro deve ser nulo.
	 * @throws MessagingException
	 */
	public static Message createMessage(Session session, String subject, String body, String encoding, String mimeType, List<String> attachments)
		throws MessagingException {

		MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap();
		MimeMultipart multipart = new MimeMultipart("related");
		if (mimeType == null)
			mimeType = "text/plain";

		if (body == null)
			body = "";

		log.debug(String.format("MimeType da mensagem: %s", mimeType));

		if (mimeType.equals("text/html")) {
			log.debug("Processando corpo da mensagem como HTML");

			// processa o corpo da mensagem em busca de tags de imagens
			StringBuffer oldBody = new StringBuffer(body);
			StringBuffer newBody = new StringBuffer();
			List<BodyPart> imgParts = new ArrayList<BodyPart>();
			int imgIndex = 0;
			while (oldBody.length() > 0) {
				int i = oldBody.indexOf("<img ");
				if (i < 0) {
					newBody.append(oldBody);
					oldBody.setLength(0);
				} else {
					newBody.append(oldBody.substring(0, i));
					oldBody.delete(0, i);
					i = oldBody.indexOf(">");
					String imgTag = oldBody.substring(0, i + 1);
					oldBody.delete(0, i + 1);

					// a imagem só será incorporada ao e-mail caso seu src não seja um endereço de internet
					String imgPath = StringToys.getTagAttribute(imgTag, "src");
					if (!imgPath.trim().startsWith("http://")) {
						String imgId = String.format("img%d", imgIndex);
						String imgType = mimeMap.getContentType(imgPath);
						if (!imgType.startsWith("image/"))
							imgType = "image/" + FileToys.extractExtension(imgPath).replaceFirst("\\.", "");

						newBody.append(imgTag.replaceFirst(imgPath, String.format("cid:%s", imgId)));

						// cria o body part correspondente à imagem
						BodyPart imgPart = new MimeBodyPart();
						DataSource ds = new FileDataSource(new File(imgPath));
						imgPart.setDataHandler(new DataHandler(ds));
						imgPart.addHeader("Content-ID", String.format("<%s>", imgId));
						imgPart.addHeader("Content-Type", String.format("%s; name=\"%s\"", imgType, imgId));

						imgParts.add(imgPart);

						log.debug(String.format("Imagem %s incorporada como %s (%s) na mensagem.", imgPath, imgId, imgType));

						imgIndex++;
					} else {
						newBody.append(imgTag);
					}
				}
			}

			// adiciona o conteúdo HTML
			String contentType = encoding != null ? String.format("%s; charset=%s", mimeType, encoding) : mimeType;
			BodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(newBody.toString(), contentType);
			multipart.addBodyPart(htmlPart);

			// adiciona as imagens
			for (BodyPart imgPart: imgParts)
				multipart.addBodyPart(imgPart);

		} else {
			log.debug("Processando corpo da mensagem como texto puro");
			String contentType = encoding != null ? String.format("%s; charset=%s", mimeType, encoding) : mimeType;
			BodyPart part = new MimeBodyPart();
			part.setContent(body, contentType);
			multipart.addBodyPart(part);
		}

		// adiciona os anexos
		for (String attPath: attachments) {
			File f = new File(attPath);
			FileDataSource ds = new FileDataSource(f);
			BodyPart part = new MimeBodyPart();
			part.setDataHandler(new DataHandler(ds));
			part.setFileName(f.getName());
			multipart.addBodyPart(part);
			log.debug(String.format("Arquivo anexado: %s", f.getAbsolutePath()));
		}

		MimeMessage message = new MimeMessage(session);
		if (encoding != null)
			message.setSubject(subject, encoding);
		else
			message.setSubject(subject);
		message.setContent(multipart);

		return message;
	}

}
