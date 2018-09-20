package toys.web.jsf.services;

import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Serviço utilizado para registro de logs para exibição no frontend.
 * @author Iran
 */
@Named
@ApplicationScoped
public class LogService implements Serializable {
    enum Nivel {
        INFO, WARN, ERROR, FATAL
    }
    private static final long serialVersionUID = -2954240079487065325L;
    private StringBuilder log;

    public LogService() {
        super();
        log = new StringBuilder();
    }

    public void init() {
        log.setLength(0);
    }

    public void log(Nivel nivel, boolean dateTime, String texto, Object... params) {
        if (StringUtils.isBlank(texto))
            return;
        String style = "color: black";
        if (nivel.equals(Nivel.WARN))
            style = "color: orange;";
        else if (nivel.equals(Nivel.ERROR))
            style = "color: red;";
        else if (nivel.equals(Nivel.FATAL))
            style = "font-weight: bold; color: red;";

        log.append("<span style=\"").append(style).append("\">");
        if (dateTime)
            log.append(String.format("%1$td/%1$tm/%1$tY - %1$tH:%1$tM - ", new Date()));
        log.append(String.format(texto, params)).append("</span><br/>");
    }

    public void info(String texto, Object... params) {
        log(Nivel.INFO, true, texto, params);
    }

    public void warn(String texto, Object... params) {
        log(Nivel.WARN, true, texto, params);
    }

    public void error(String texto, Object... params) {
        log(Nivel.ERROR, true, texto, params);
    }

    public void fatal(String texto, Object... params) {
        log(Nivel.FATAL, true, texto, params);
    }

    public String getLog() {
        return log != null ? log.toString() : "";
    }

}
