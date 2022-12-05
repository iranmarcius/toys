package toys.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.security.Principal;
import java.util.*;

/**
 * Um pojo que simplesmente copia e armazena alguns dados de uma requisição.
 *
 * @author Iran Marcius
 * @since 11/2020
 */
public class HttpServletRequestPojo implements HttpServletRequest {
  private final Principal principal;
  private final String contextPath;
  private final String queryString;
  private final String requestURI;
  private final StringBuffer requestURL;
  private final String remoteAddr;
  private final String remoteHost;
  private final Map<String, String> headers;
  private final Map<String, String[]> params;

  public HttpServletRequestPojo(HttpServletRequest request) {
    principal = request.getUserPrincipal();
    contextPath = request.getContextPath();
    queryString = request.getQueryString();
    requestURI = request.getRequestURI();
    requestURL = request.getRequestURL();
    remoteAddr = request.getRemoteAddr();
    remoteHost = request.getRemoteHost();
    headers = new HashMap<>();
    request
      .getHeaderNames()
      .asIterator()
      .forEachRemaining(name -> headers.put(name, request.getHeader(name)));
    params = new HashMap<>();
    request
      .getParameterNames()
      .asIterator()
      .forEachRemaining(name -> params.put(name, request.getParameterValues(name)));
  }

  @Override
  public Principal getUserPrincipal() {
    return principal;
  }

  @Override
  public String getHeader(String name) {
    return headers.get(name.toLowerCase());
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    return null;
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    return null;
  }

  @Override
  public String getAuthType() {
    return null;
  }

  @Override
  public Cookie[] getCookies() {
    return new Cookie[0];
  }

  @Override
  public long getDateHeader(String name) {
    return 0;
  }

  @Override
  public int getIntHeader(String name) {
    return 0;
  }

  @Override
  public String getMethod() {
    return null;
  }

  @Override
  public String getPathInfo() {
    return null;
  }

  @Override
  public String getPathTranslated() {
    return null;
  }

  @Override
  public String getContextPath() {
    return contextPath;
  }

  @Override
  public String getQueryString() {
    return queryString;
  }

  @Override
  public String getRemoteUser() {
    return null;
  }

  @Override
  public boolean isUserInRole(String role) {
    return false;
  }

  @Override
  public String getRequestedSessionId() {
    return null;
  }

  @Override
  public String getRequestURI() {
    return requestURI;
  }

  @Override
  public StringBuffer getRequestURL() {
    return requestURL;
  }

  @Override
  public String getServletPath() {
    return null;
  }

  @Override
  public HttpSession getSession(boolean create) {
    return null;
  }

  @Override
  public HttpSession getSession() {
    return null;
  }

  @Override
  public String changeSessionId() {
    return null;
  }

  @Override
  public boolean isRequestedSessionIdValid() {
    return false;
  }

  @Override
  public boolean isRequestedSessionIdFromCookie() {
    return false;
  }

  @Override
  public boolean isRequestedSessionIdFromURL() {
    return false;
  }

  @Override
  public boolean isRequestedSessionIdFromUrl() {
    return false;
  }

  @Override
  public boolean authenticate(HttpServletResponse response) {
    return false;
  }

  @Override
  public void login(String username, String password) {
    // Nenhuma implementação
  }

  @Override
  public void logout() {
    // Nenhuma implementação
  }

  @Override
  public Collection<Part> getParts() {
    return null;
  }

  @Override
  public Part getPart(String name) {
    return null;
  }

  @Override
  public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) {
    return null;
  }

  @Override
  public Object getAttribute(String name) {
    return null;
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return null;
  }

  @Override
  public String getCharacterEncoding() {
    return null;
  }

  @Override
  public void setCharacterEncoding(String env) {
    // Nenhuma implementação
  }

  @Override
  public int getContentLength() {
    return 0;
  }

  @Override
  public long getContentLengthLong() {
    return 0;
  }

  @Override
  public String getContentType() {
    return null;
  }

  @Override
  public ServletInputStream getInputStream() {
    return null;
  }

  @Override
  public String getParameter(String name) {
    return params.containsKey(name) ? params.get(name)[0] : null;
  }

  @Override
  public Enumeration<String> getParameterNames() {
    return Collections.enumeration(params.keySet());
  }

  @Override
  public String[] getParameterValues(String name) {
    return params.getOrDefault(name, null);
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    return params;
  }

  @Override
  public String getProtocol() {
    return null;
  }

  @Override
  public String getScheme() {
    return null;
  }

  @Override
  public String getServerName() {
    return null;
  }

  @Override
  public int getServerPort() {
    return 0;
  }

  @Override
  public BufferedReader getReader() {
    return null;
  }

  @Override
  public String getRemoteAddr() {
    return remoteAddr;
  }

  @Override
  public String getRemoteHost() {
    return remoteHost;
  }

  @Override
  public void setAttribute(String name, Object o) {
    // Nenhuma implementação
  }

  @Override
  public void removeAttribute(String name) {
    // Nenhuma implementação
  }

  @Override
  public Locale getLocale() {
    return null;
  }

  @Override
  public Enumeration<Locale> getLocales() {
    return null;
  }

  @Override
  public boolean isSecure() {
    return false;
  }

  @Override
  public RequestDispatcher getRequestDispatcher(String path) {
    return null;
  }

  @Override
  public String getRealPath(String path) {
    return null;
  }

  @Override
  public int getRemotePort() {
    return 0;
  }

  @Override
  public String getLocalName() {
    return null;
  }

  @Override
  public String getLocalAddr() {
    return null;
  }

  @Override
  public int getLocalPort() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return null;
  }

  @Override
  public AsyncContext startAsync() {
    return null;
  }

  @Override
  public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
    return null;
  }

  @Override
  public boolean isAsyncStarted() {
    return false;
  }

  @Override
  public boolean isAsyncSupported() {
    return false;
  }

  @Override
  public AsyncContext getAsyncContext() {
    return null;
  }

  @Override
  public DispatcherType getDispatcherType() {
    return null;
  }
}
