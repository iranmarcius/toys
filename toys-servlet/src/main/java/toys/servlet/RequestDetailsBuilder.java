package toys.servlet;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static toys.ToysConsts.*;

/**
 * classe utilitária para construir umaa string com os detalhes de uma requisição.
 *
 * @author Iran Marcius
 * @since 10/2020
 */
public class RequestDetailsBuilder {
    private final HttpServletRequest request;
    private String principal;
    private String userAgent;
    private String referer;
    private String xForwardedFor;
    private Map<String, Object> params;

    public RequestDetailsBuilder(HttpServletRequest request) {
        this.request = request;
    }

    public RequestDetailsBuilder withPrincipal(String principalName) {
        principal = principalName;
        return this;
    }

    public RequestDetailsBuilder withPrincipal() {
        var userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null)
            return withPrincipal(userPrincipal.getName());
        else
            return this;
    }

    public RequestDetailsBuilder withUserAgent() {
        userAgent = request.getHeader(HTTP_HEADER_USER_AGENT);
        return this;
    }

    public RequestDetailsBuilder withReferer() {
        referer = request.getHeader(HTTP_HEADER_REFERER);
        return this;
    }

    public RequestDetailsBuilder withXForwardedFor() {
        xForwardedFor = request.getHeader(HTTP_HEADER_X_FORWARDED_FOR);
        return this;
    }

    public RequestDetailsBuilder withRequestParam(String paramName) {
        if (params == null)
            params = new HashMap<>();
        params.put(paramName, request.getParameter(paramName));
        return this;
    }

    public RequestDetailsBuilder withAll(String principalName) {
        return withPrincipal(principalName)
            .withReferer()
            .withUserAgent()
            .withXForwardedFor();
    }

    public RequestDetailsBuilder withAll() {
        return withPrincipal()
            .withReferer()
            .withUserAgent()
            .withXForwardedFor();
    }

    public String build() {
        var sb = new StringBuilder("Requisicao [");
        appendDetail(sb, "principal", principal);
        appendParams(sb);
        appendDetail(sb, "remoteAddress", request.getRemoteAddr());
        appendDetail(sb, "requestUri", request.getRequestURI());
        appendDetail(sb, "referer", referer);
        appendDetail(sb, "xForwardedFor", xForwardedFor);
        appendDetail(sb, "userAgent", userAgent);
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    private void appendDetail(StringBuilder sb, String name, String value) {
        if (StringUtils.isNotBlank(value))
            sb.append(name).append("=").append(value).append(", ");
    }

    private void appendParams(StringBuilder sb) {
        if (params == null || params.isEmpty())
            return;
        sb.append("params={");
        for (Map.Entry<String, Object> entry: params.entrySet())
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        sb.setLength(sb.length() - 2);
        sb.append("}, ");
    }

    public static RequestDetailsBuilder builder(HttpServletRequest request) {
        return new RequestDetailsBuilder(request);
    }

}
