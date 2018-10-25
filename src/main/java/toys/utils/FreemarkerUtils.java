package toys.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toys.pojos.FreemarkerTemplatePojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitária para gerenciamento de templates.
 * @author Iran
 */
public final class FreemarkerUtils {
    private static final Logger logger = LogManager.getFormatterLogger(FreemarkerUtils.class);
    private static Configuration cfg;

    private FreemarkerUtils() {
        super();
    }

    /**
     * Retorna um template. Caso a configuração do Freemarker não esteja inicializada, ela será criada como um {@link ClassTemplateLoader}
     * configurado para buscar os templates no pacote /freemarker.
     * @param name Nome do template sem a extensão.
     */
    public static synchronized Template getTemplate(String name) throws IOException {
        if (name == null)
            throw new NullPointerException("Nome do template nao foi informado.");
        if (cfg == null) {
            logger.debug("Inicializando configuracao do Freemarker.");
            cfg =  new Configuration(Configuration.VERSION_2_3_22);
            cfg.setDefaultEncoding("utf-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerUtils.class, "/freemarker"));
            logger.debug("Freemarker inicializado.");
        }
        return cfg.getTemplate(name + ".ftl");
    }

    /**
     * Retorna o conteúdo de um template de e-mail processado com as informações passadas. Caso seja necessário, o template de e-mail
     * poderá conter alguns cabeçalhos que poderão ser utilizados para envio de e-mails e esses cabeçalhos estarão no pojo
     * retornado pelo método. Para isso basta fornecer os cabeçalhos na forma de comentários do Freemarker <b>a partir da primeira
     * linha do template</b>. Segue abaixo um exemplo:
     * <blockquote>
     *  <code>
     *          &lt;#--<br>
     *          From: <i>Nome e e-mail do remetente no formado padrão</i><br>
     *          Subject: <i>Assunto da mensagem</i><br>
     *          --&gt;<br>
     *          <i>Corpo da mensagem...</i>
     *  </code>
     * </blockquote>
     * @param name Nome do template sem a extensão.
     * @param subjectParams Parâmetros opcionais que serão utilizados na formatação do assunto.
     * @return {@link FreemarkerTemplatePojo}
     * @throws IOException Caso não seja possível obter o template.
     * @see #getTemplate(String)
     */
    public static synchronized FreemarkerTemplatePojo getEmailTemplate(String name, Object... subjectParams) throws IOException {
        Template t = getTemplate(name);

        // Determina a sessão de cabeçalhos
        int li = -1;
        int ci = -1;
        int lf = -1;
        int cf = -1;
        StringWriter tplSource = new StringWriter();
        t.dump(tplSource);
        try (BufferedReader reader = new BufferedReader(new StringReader(tplSource.toString()))) {
            String linha;
            int l = 1;
            int i;
            boolean inHeaders = false;
            while ((linha = reader.readLine()) != null) {

                if (!inHeaders) {
                    i = linha.indexOf("<#--");
                    if (i > -1) {
                        inHeaders = true;
                        li = l;
                        ci = i + 5;
                    }
                }

                if (inHeaders) {
                    i = linha.indexOf("-->");
                    if (i > -1) {
                        lf = l;
                        cf = i;
                        break;
                    }
                }

                l++;
            }
        }

        // Se a seção de cabeçalhos foi determinada, extrai os cabeçalhos.
        Map<String, String> headers = new HashMap<>();
        if (li != -1 && ci != -1 && lf != -1 && cf != -1) {
            String linha;
            String secaoCabecalhos = t.getSource(ci, li, cf, lf);
            try (BufferedReader reader = new BufferedReader(new StringReader(secaoCabecalhos))) {
                while ((linha = reader.readLine()) != null) {
                    if (linha.matches("^[a-zA-Z0-9]+:.+$")) {
                        String[] ss = linha.split(":");
                        String key = ss[0].trim();
                        String value = ss[1].trim();
                        if (key.equals("Subject") && subjectParams != null && subjectParams.length > 0)
                            value = String.format(value, subjectParams);
                        headers.put(key, value);
                    }
                }
            }
        }

        return new FreemarkerTemplatePojo(t, headers);
    }

    /**
     * Envia um email HTML utilizando o template e os dados informados.
     * @param hostname Nome do host de SMTP para envio do e-mail.
     * @param templateId Identificador do template. O template será obtido através da função {@link #getEmailTemplate(String, Object...)}.
     * @param data dados que serão utilizados na criação do conteúdo do e-mail.
     * @param emailDestinatario Email do destinatário.
     * @param nomeDestinatario Nome do destinatário. Este parâmetro é opcional.
     * @param subjectParams Parâmetros do assunto do email caso haja necessidade. Este parâmetro é utilizado na chamada do método
     * {@link #getEmailTemplate(String, Object...)}.
     */
    public static synchronized void enviarEmailHtml(String hostname, String templateId, Map<String, Object> data, String emailDestinatario, String nomeDestinatario, Object... subjectParams)
            throws TemplateException, IOException {
        FreemarkerTemplatePojo t = getEmailTemplate(templateId, subjectParams);
        StringWriter sw = new StringWriter();
        t.getTemplate().process(data, sw);
        EmailUtils.enviarEmailHtml(
                hostname,
                t.getHeaders().get("From"),
                emailDestinatario,
                nomeDestinatario,
                t.getHeaders().get("Subject"),
                sw.toString());
    }

}
