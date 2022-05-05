package toys.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

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
   * Retorna uma relação das autoridades à partir da autenticação informada.
   *
   * @param authentication Objeto com os dados da autenticação.
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
