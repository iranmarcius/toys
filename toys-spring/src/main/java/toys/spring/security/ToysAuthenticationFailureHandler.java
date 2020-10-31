package toys.spring.security;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import toys.ToysConsts;
import toys.servlet.RequestDetailsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Gerenciador de falhas de autenticaçãao que simplesmente gera uma entrada no log informando a falha.
 *
 * @author Iran Marcius
 * @since 10/2020
 */
public class ToysAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LoggerFactory.getLogger(ToysConsts.LOGGER_AUTH).info("Falha de autenticacao. {}",
            RequestDetailsBuilder.builder(request)
                .withAll()
                .withRequestParam("username")
                .build(),
            exception
        );
        super.onAuthenticationFailure(request, response, exception);
    }

}
