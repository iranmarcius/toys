package toys.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import toys.servlet.WebAppUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Métodos utilitários para trabalhar com o Spring.
 *
 * @author Iran
 * @since 10/2018
 */
public class ToysSpringUtils {

  private ToysSpringUtils() {
  }

  /**
   * Retorna o usuário autenticado.
   *
   * @return {@link User}
   */
  public static synchronized User getPrincipal() {
    SecurityContext sc = SecurityContextHolder.getContext();
    if (sc != null) {
      Object principal = sc.getAuthentication().getPrincipal();
      return principal instanceof User user ? user : null;
    }
    return null;
  }

  /**
   * Retorna o nome do usuário autenticado se houver algum, do contrário retorna nulo.
   *
   * @return <code>String</code>
   */
  public static synchronized String getPrincipalName() {
    SecurityContext sc = SecurityContextHolder.getContext();
    if (sc != null) {
      Authentication auth = sc.getAuthentication();
      if (auth != null)
        return auth.getName();
    }
    return null;
  }

  /**
   * Retorna uma relação das autorizações do usuário logado no contexto.
   *
   * @return <code>List&lt;String&gt;</code>
   */
  public static Set<String> getAuthorities() {
    SecurityContext sc = SecurityContextHolder.getContext();
    if (sc != null) {
      Authentication auth = sc.getAuthentication();
      if (auth != null) {
        var authorities = new HashSet<String>();
        for (GrantedAuthority ga : auth.getAuthorities())
          authorities.add(ga.getAuthority());
        return authorities;
      }
    }
    return Collections.emptySet();
  }

  /**
   * Método de conveniência para gerar uma string de log utilizando o principal do contexto se segurança do Spring Security.
   *
   * @see WebAppUtils#logMsg(HttpServletRequest, String, boolean, String, Object...)
   */
  public static synchronized String logMsg(HttpServletRequest request, boolean detalhes, String msg, Object... params) {
    return WebAppUtils.logMsg(request, getPrincipalName(), detalhes, msg, params);
  }

}
