package toys.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
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
   * Retorna uma relação das autoridades à partir da autenticação informada.
   *
   * @param authentication Objeto com os dados da autnticação.
   * @return <code>List&lt;String&gt;</code>
   */
  public static Set<String> getAuthorities(Authentication authentication) {
    if (authentication != null) {
      var authorities = new HashSet<String>();
      for (GrantedAuthority ga : authentication.getAuthorities())
        authorities.add(ga.getAuthority());
      return authorities;
    } else {
      return Collections.emptySet();
    }
  }

}
