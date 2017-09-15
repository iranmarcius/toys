package toys.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import toys.pojos.FreemarkerTemplatePojo;

/**
 * Classe utilitária para gerenciamento de templates.
 * @author Iran
 */
public final class FreemarkerUtils {
    private static Configuration cfg;

    private FreemarkerUtils() {
        super();
    }

    /**
     * Inicializa a configuração do Freemarker com o diretório de templates informado.
     */
    public static synchronized void init(String templatesDir) throws IOException {
        if (cfg == null) {
            LogManager.getFormatterLogger().info("Inicializando utilitario Freemarker. templatesDir=%s", templatesDir);
            cfg =  new Configuration(Configuration.VERSION_2_3_22);
            cfg.setTemplateLoader(new FileTemplateLoader(new File(templatesDir)));
            cfg.setDefaultEncoding("utf-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } else {
            LogManager.getLogger().warn("Utilitario Freemarker ja foi inicializado.");
        }
    }

    /**
     * Retorna um template.
     * @param name Nome do template sem a extensão.
     */
    public static synchronized Template getTemplate(String name) throws IOException {
        if (name == null)
            throw new NullPointerException("Nome do template nao foi informado.");
        if (cfg == null)
            throw new NullPointerException("Freemarker nao foi inicializado.");
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
     * @throws IOException
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
            String linha = null;
            int l = 1;
            int i = -1;
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
            String linha = null;
            String secaoCabecalhos = t.getSource(ci, li, cf, lf);
            try (BufferedReader reader = new BufferedReader(new StringReader(secaoCabecalhos))) {
                while ((linha = reader.readLine()) != null) {
                    if (linha.matches("^[a-zA-Z0-9]+\\:.+$")) {
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
     * @param templateId Identificador do template. O template será obtido através da função {@link getEmailTemplate}.
     * @param data dados que serão utilizados na criação do conteúdo do e-mail.
     * @param nomeDestinatario Nome do destinatário.
     * @param emailDestinatario Email do destinatário.
     * @param subjectParams Parâmetros do assunto do email caso haja necessidade. Este parâmetro é utilizado na chamada do método
     * {@link getEmailTemplate}.
     */
    public static synchronized void enviarEmailHtml(String templateId, Map<String, Object> data, String nomeDestinatario, String emailDestinatario, Object... subjectParams)
            throws TemplateException, IOException {
        FreemarkerTemplatePojo t = getEmailTemplate(templateId, subjectParams);
        StringWriter sw = new StringWriter();
        t.getTemplate().process(data, sw);
        EmailUtils.enviarEmailHtml(
                t.getHeaders().get("From"),
                emailDestinatario,
                nomeDestinatario,
                t.getHeaders().get("Subject"),
                sw.toString());
    }

}
