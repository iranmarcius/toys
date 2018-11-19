package toys.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;

/**
 * Métodos utilitários para implementações de segurança.
 * @author Iran
 * @since 19/11/2018
 */
public class SecurityToys {

    /**
     * Retorna o token JWT armazenado no cabeçalho <b>Authorization</b> da requisição. O esquema utilizado deverá ser o <b>Bearer</b>, do contrário
     * a informação do cabeçalho será ignorada.
     * @param request Objeto contendo as informações da requisição.
     * @return Retorna o token encontraro ou nulo.
     */
    public static String getJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer"))
            return authorizationHeader.substring(7);
        else
            return null;
    }

    /**
     * Extrai as claims de um token JWT utilizando o método {@link #getJWT(HttpServletRequest)}.
     * @param request Objeto contendo as informações da requisição.
     * @param key Chave utilizada para assinatura do token.
     * @return Caso um token seja encontrado retorna as claims, do contrário retorna nulo.
     */
    public static Claims getClaims(HttpServletRequest request, Key key) {
        String jwt = getJWT(request);
        if (jwt != null)
            return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        else
            return null;
    }

}
