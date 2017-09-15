package toys.pojos;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;

public class FreemarkerTemplatePojo {
    private Template template;
    private Map<String, String> headers;

    public FreemarkerTemplatePojo(Template template, Map<String, String> headers) {
        super();
        this.template = template;
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
    }

    public Template getTemplate() {
        return template;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getFrom() {
        return headers.get("From");
    }

    public String getSubject() {
        return headers.get("Subject");
    }

}
