package toys.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import toys.CollectionToys;
import toys.Crypt;
import toys.servlet.WebAppUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
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
            return principal instanceof User ? (User) principal : null;
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
    public static synchronized Set<String> getAuthorities() {
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
     * Converte a relação de privilégios em uma string e a criptografa com a chave fornecida.
     *
     * @param authorities Coleção de privilégios.
     * @param key         Chave de codificação.
     * @return String
     */
    public static synchronized String encodeAuthorities(Set<String> authorities, Key key) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        var expr = CollectionToys.asString(authorities, ";");
        return Crypt.encode(expr, key);
    }

    /**
     * Método de conveniência para gerar uma string de detalhes da requisição utilizando o principal do contexto
     * de securança do Spring Security.
     *
     * @see WebAppUtils#requestDetails(HttpServletRequest, String, boolean, boolean)
     */
    public static synchronized String requestDetails(HttpServletRequest request, boolean incluirUri, boolean incluirReferer) {
        return WebAppUtils.requestDetails(request, getPrincipalName(), incluirUri, incluirReferer);
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
