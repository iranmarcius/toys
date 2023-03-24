package toys.servlet;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import toys.CollectionToys;
import toys.Crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static toys.ToysConsts.JWT_CLAIM_AUTHORITIES;

/**
 * Métodos utilitários para implementações de segurança.
 *
 * @author Iran
 * @since 19/11/2018
 */
public class SecurityToys {

  private SecurityToys() {
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
      return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
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

  /**
   * Converte a relação de privilégios em uma string e a criptografa com a chave fornecida.
   *
   * @param authorities Coleção de privilégios.
   * @param key         Chave que será utilizada para codificação.
   * @return String
   */
  public static String encodeAuthorities(Set<String> authorities, SecretKey key) throws
    IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
    var expr = CollectionToys.asString(authorities, ";");
    return Base64.getEncoder().encodeToString(Crypt.encrypt(expr, key));
  }

  /**
   * Método de conveniência para padronização do armazenamento da claim de autoridades no token.
   *
   * @param builder     Referência para o builder utilizado na construção do token.
   * @param authorities Relação de autoridades.
   * @param key         Chave que será utilizada para codificação.
   */
  public static void setAuthorities(JwtBuilder builder, Set<String> authorities, SecretKey key) throws
    InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
    builder.claim(JWT_CLAIM_AUTHORITIES, encodeAuthorities(authorities, key));
  }

  /**
   * Decodifica e extrai as autoridades armazenadas em um token.
   *
   * @param claims Claims do token.
   * @param key    Chave para decodificação.
   * @return Retorna uma coleção de autoridades de um usuário.
   */
  public static Set<String> extractAuthorities(Claims claims, SecretKey key) throws
    IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

    if (claims == null || !claims.containsKey(JWT_CLAIM_AUTHORITIES))
      return Collections.emptySet();

    String encodedAuthorities = (String) claims.get(JWT_CLAIM_AUTHORITIES);
    String decodedAuthorities = Crypt.decrypt(Base64.getDecoder().decode(encodedAuthorities), key);
    return Arrays.stream(decodedAuthorities.split(";")).collect(Collectors.toSet());
  }

}
