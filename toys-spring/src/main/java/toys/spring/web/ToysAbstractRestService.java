package toys.spring.web;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import toys.pojos.TextMessagePojo;
import toys.servlet.RequestDetailsBuilder;
import toys.spring.ToysSpringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Implementação básica para construção de serviços REST.
 *
 * @author Iran Marcius
 * @since 02/2022
 */
public abstract class ToysAbstractRestService {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Retorna uma exception de HTTP status 500 logando a mensagem de erro.
   *
   * @param msg       mensagem de erro.
   * @param arguments Parâmetros da mensagem.
   * @return ResponseStatusException
   */
  protected ResponseStatusException internalServerError(String msg, Object... arguments) {
    logger.error(MarkerFactory.getMarker("FATAL"), msg, arguments);
    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Retorna uma resposta com o status informado e uma lista de mensagens no corpo.
   *
   * @param status Status da resposta.
   * @param msgs   Lista de objetos do tipo {@link TextMessagePojo} com as mensagens que serão retornadas no corpo
   *               da resposta.
   * @return {@link ResponseEntity}&lt;List&lt;{@link TextMessagePojo}&gt;&gt;
   */
  protected ResponseEntity<Collection<TextMessagePojo>> messagesResponse(
    HttpStatus status,
    Collection<TextMessagePojo> msgs
  ) {
    return ResponseEntity.status(status).body(msgs);
  }

  /**
   * Método de conveniência para retornar uma resposta com status {@link HttpStatus#PRECONDITION_FAILED PRECONDITION_FAILED (412)}
   * contendo uma lista de mensagens no corpo.
   *
   * @param msgs Coleção de objetos do tipo {@link TextMessagePojo} com as mensagens.
   * @see #messagesResponse(HttpStatus, Collection)
   */
  protected ResponseEntity<Collection<TextMessagePojo>> preconditionFailedResponse(Collection<TextMessagePojo> msgs) {
    return messagesResponse(HttpStatus.PRECONDITION_FAILED, msgs);
  }

  /**
   * Método de conveniência para retornar uma resposta com status {@link HttpStatus#PRECONDITION_FAILED PRECONDITION_FAILED (412)}
   * contendo apenas uma mensagem.
   *
   * @param msg Objeto do tipo {@link TextMessagePojo} com as informações da mensagem.
   * @see #messagesResponse(HttpStatus, Collection)
   */
  protected ResponseEntity<Collection<TextMessagePojo>> preconditionFailedResponse(TextMessagePojo msg) {
    return preconditionFailedResponse(Collections.singleton(msg));
  }

  /**
   * Método de conveniência para retornar uma resposta com status {@link HttpStatus#EXPECTATION_FAILED EXPECTATION_FAILED (417)}
   * contendo uma lista de mensagens no corpo.
   *
   * @param msgs Coleção de objetos do tipo {@link TextMessagePojo} com as mensagens.
   * @see #messagesResponse(HttpStatus, Collection)
   */
  protected ResponseEntity<Collection<TextMessagePojo>> expectationFailedResponse(Collection<TextMessagePojo> msgs) {
    return messagesResponse(HttpStatus.EXPECTATION_FAILED, msgs);
  }

  /**
   * Método de conveniência para retornar uma resposta com status {@link HttpStatus#EXPECTATION_FAILED EXPECTATION_FAILED (417)}
   * contendo apenas uma mensagem.
   *
   * @param msg Objeto do tipo {@link TextMessagePojo} com as informações da mensagem.
   * @see #messagesResponse(HttpStatus, Collection)
   */
  protected ResponseEntity<Collection<TextMessagePojo>> expectationFailedResponse(TextMessagePojo msg) {
    return expectationFailedResponse(Collections.singleton(msg));
  }

  /**
   * Método de conveniência para retornar uma resposta com status {@link HttpStatus#EXPECTATION_FAILED EXPECTATION_FAILED (417)}
   * contendo apenas a mensagem informada.
   *
   * @param text Texto da mensagem.
   * @see #messagesResponse(HttpStatus, Collection)
   */
  protected ResponseEntity<Collection<TextMessagePojo>> expectationFailedResponse(String text) {
    return expectationFailedResponse(new TextMessagePojo(text));
  }

  /**
   * Método de conveniência para retornar uma resposta com o status {@link HttpStatus#FORBIDDEN FORBIDDEN (403)}.
   */
  @SuppressWarnings("rawtypes")
  protected ResponseEntity forbiddenResponse() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  /**
   * Método de conveniência para retornar uma resposta com o status {@link HttpStatus#NO_CONTENT NO_CONTENT (204)}
   */
  @SuppressWarnings("rawtypes")
  protected ResponseEntity noContentResponse() {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Cria e adiciona uma mensagem à coleção.
   *
   * @param msgs Coleção de objetos do tipo {@link TextMessagePojo} na qual a nova mensagem será adicionada.
   * @param id   Identificador.
   * @param text Texto da mensagem.
   */
  protected void addMessage(Collection<TextMessagePojo> msgs, String id, String text) {
    if (msgs != null)
      msgs.add(new TextMessagePojo(id, text));
  }

  /**
   * Cria um texto padrão para logs de operações de usuários.
   *
   * @param text    Texto da mensagem.
   * @param request Informações da requisição.
   * @param params  Parâmetros para formatação da mensagem.
   * @return Retorna uma string contendo o texto original, o usuário autenticado e as informações da requisição.
   */
  protected String usersLogText(String text, HttpServletRequest request, Object... params) {
    return String.format(text, params) + " - " +
      RequestDetailsBuilder.builder(request)
        .withPrincipal()
        .withURI()
        .withXForwardedFor()
        .withUserAgent()
        .build();
  }

  /**
   * Método de conveniência para gerar o texto de log de usuário sem o objeto da requisição.
   *
   * @see #usersLogText(String, HttpServletRequest, Object...)
   */
  protected String usersLogText(String text, Object... params) {
    var request = ((ServletRequestAttributes) Objects.requireNonNull(
      RequestContextHolder.getRequestAttributes())).getRequest();
    return usersLogText(text, request, params);
  }

  /**
   * Retorna a autenticação corrente do contexto possui alguma das autoridades informadas.
   *
   * @param authorities Relação de autoridades para verificação.
   * @return boolean
   */
  protected boolean hasAnyAuthority(String... authorities) {
    if (authorities == null || authorities.length == 0)
      return true;
    var sc = SecurityContextHolder.getContext();
    if (sc != null) {
      var userAuthorities = ToysSpringUtils.getAuthorities(sc.getAuthentication());
      for (String auth : userAuthorities)
        if (ArrayUtils.contains(authorities, auth))
          return true;
    }
    return false;
  }

}
