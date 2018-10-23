package toys.security.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Métodos utilitários para trabalhar com o Spring Security.
 * @author Iran
 * @since 23/10/2018
 */
public class SpringSecurityToys {

    private SpringSecurityToys() {
        super();
    }

    /**
     * Retorna o nome do usuário autenticado se houver algum, do contrário retorna nulo.
     * @return <code>String</code>
     */
    public static String getPrincipalName() {
        SecurityContext sc = SecurityContextHolder.getContext();
        if (sc != null) {
            Authentication auth = sc.getAuthentication();
            if (auth != null)
                return auth.getName();
        }
        return null;
    }

}
