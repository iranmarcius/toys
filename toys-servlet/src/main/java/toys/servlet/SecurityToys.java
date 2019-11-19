package toys.servlet;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;

/**
 * Métodos utilitários para implementações de segurança.
 *
 * @author Iran
 * @since 19/11/2018
 */
public class SecurityToys {

    private SecurityToys() {
        super();
    }

    /**
     * Extrai o token JWT do cabeçalho informado. O método retorna o valor logo após a palavra <b>Bearer</b>.
     * Caso a palavra não seja encontrada será retornado um valor nulo.
     *
     * @param header Valor do cabeçalho.
     * @return Retorna uma string representando o token JWT ou nulo caso não haja um token.
     */
    public static String getJWTFromHeader(String header) {
        if (header != null && header.startsWith("Bearer") && header.length() > 7)
            return header.substring(7);
        else
            return null;
    }

    /**
     * Método de conveniência para invocar o {@link #getJWTFromHeader(String)} passando o valor obtido do cabeçalho
     * <b>Authorization</b>.
     *
     * @param request Objeto contendo as informações da requisição.
     * @return Retorna o token encontrado ou nulo.
     */
    public static String getJWT(HttpServletRequest request) {
        return getJWTFromHeader(request.getHeader("Authorization"));
    }

    /**
     * Decodifica e retorna as claims contidas no token informado.
     *
     * @param token Token JWT a ser processado.
     * @param key   Chave utilizada na decodificação do token.
     * @return Retorna uma relação de claims ou nulo.
     */
    public static Claims getClaims(String token, Key key) {
        if (token != null)
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        else
            return null;
    }

    /**
     * Método de conveniência para invocar o {@link #getClaims(String, Key)} extraindo o token da requisição
     * utilizando o método {@link #getJWT(HttpServletRequest)}.
     *
     * @param request Objeto contendo as informações da requisição.
     * @param key     Chave utilizada para assinatura do token.
     * @return Retorna as claims do token ou nulo.
     */
    public static Claims getClaims(HttpServletRequest request, Key key) {
        return getClaims(getJWT(request), key);
    }

}
