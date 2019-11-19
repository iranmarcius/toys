package toys.jsf;

import toys.LDAPUtils;
import toys.ToysMessages;
import toys.servlet.WebAppUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Métodos utilitários para aplicações web construídas com a tecnologia Java Server Faces.
 * @author Iran
 */
public class JSFUtils {

    private JSFUtils() {
        super();
    }

    /**
     * Retorna uma mensagem de log no padrão para as aplicações utilizando o método
     * {@link WebAppUtils#logMsg(HttpServletRequest, boolean, String, Object...)}.
     */
    public static String log(boolean detalhes, String mensagem, Object... params){
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc != null ?
                WebAppUtils.logMsg((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(), detalhes, mensagem, params) :
                String.format(mensagem,  params);
    }

    /**
     * Método de conveniência para não incluir os detalhes da requisição nas mensagens do log.
     * @see JSFUtils#log(boolean, String, Object...)
     */
    public static String log(String mensagem, Object... params) {
        return log(false, mensagem, params);
    }

    /**
     * Retorna a URL base da requisição.
     */
    public static String getBaseURL() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80)
            url += ":" + request.getServerPort();
        url += request.getContextPath();
        return url;
    }

    /**
     * Adiciona uma mensagem às mensagens do JSF.
     * @param clientId Id do client.
     * @param severity Severidade.
     * @param message Mensagem ou título.
     * @param details Detalhes.
     */
    public static void addMessage(String clientId, FacesMessage.Severity severity, String message, String details) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, message, details));
    }

    /**
     * Adiciona uma mensagem com o clientId default.
     * @param severity Severidade.
     * @param message Mensagem.
     * @param details Detalhes.
     */
    public static void addMessage(FacesMessage.Severity severity, String message, String details) {
        addMessage(null, severity, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_INFO}.
     * @param clientId Id do client.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addInfoMsg(String clientId, String message, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_INFO, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_INFO}.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addInfoMsg(String message, String details) {
        addInfoMsg(null, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_WARN}.
     * @param clientId Id do client.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addWarnMsg(String clientId, String message, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_WARN, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_WARN}.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addWarnMsg(String message, String details) {
        addWarnMsg(null, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_ERROR}.
     * @param clientId Id do client.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addErrorMsg(String clientId, String message, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_ERROR, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_ERROR}.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addErrorMsg(String message, String details) {
        addErrorMsg(null, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_FATAL}.
     * @param clientId Id do client.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addFatalMsg(String clientId, String message, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_FATAL, message, details);
    }

    /**
     * Adiciona uma mensagem com a severidade {@link javax.faces.application.FacesMessage.Severity#SEVERITY_FATAL}.
     * @param message Mensagem
     * @param details Detalhes.
     */
    public static void addFatalMsg(String message, String details) {
        addFatalMsg(null, message, details);
    }

    /**
     * Retorna a instância do {@link LDAPUtils} armazenada no contexto da aplicação.
     */
    public static synchronized LDAPUtils getLDAPUtils() {
        return (LDAPUtils)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(LDAPUtils.class.getName());
    }

    /**
     * Cria uma mensagem padrão de erro.
     */
    public static void erroInterno() {
        addErrorMsg(ToysMessages.erroInterno(), "Ocorreu um erro interno.");
    }

}
