package toys.security.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * Retorna o usuário autenticado.
     * @return {@link User}
     */
    public static User getPrincipal() {
        SecurityContext sc = SecurityContextHolder.getContext();
        if (sc != null) {
            Object principal = sc.getAuthentication().getPrincipal();
            return principal instanceof User ? (User)principal : null;
        }
        return null;
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

    /**
     * Retorna uma relação das autorizações do usuário logado no contexto.
     * @return <code>List&lt;String&gt;</code>
     */
    public static List<String> getAuthorities() {
        SecurityContext sc = SecurityContextHolder.getContext();
        if (sc != null) {
            Authentication auth = sc.getAuthentication();
            if (auth != null) {
                List<String> authorities = new ArrayList<>();
                for (GrantedAuthority ga: auth.getAuthorities())
                    authorities.add(ga.getAuthority().substring(5));
                return authorities;
            }
        }
        return Collections.emptyList();
    }

}
