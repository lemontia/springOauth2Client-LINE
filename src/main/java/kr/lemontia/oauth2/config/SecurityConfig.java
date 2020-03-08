package kr.lemontia.oauth2.config;


import kr.lemontia.oauth2.config.oauth2.CustomOAuth2Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .oauth2Login()
                .defaultSuccessUrl("/loginSuccess")
                ;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        List<ClientRegistration> clientRegistrations = new ArrayList<>();


        String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

        ClientRegistration.Builder lineBuilder = ClientRegistration.withRegistrationId("line");
        lineBuilder.clientAuthenticationMethod(ClientAuthenticationMethod.POST);
        lineBuilder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        lineBuilder.redirectUriTemplate(DEFAULT_LOGIN_REDIRECT_URL);
        lineBuilder.authorizationUri("https://access.line.me/oauth2/v2.1/authorize");
        lineBuilder.tokenUri("https://api.line.me/oauth2/v2.1/token");
        lineBuilder.clientName("LINE");
        lineBuilder.scope("profile", "openid");
        lineBuilder.clientId("1653928900");
        lineBuilder.clientSecret("48b3367f319095fb37c7764e5cb293b6");
        lineBuilder.jwkSetUri("temp");
        lineBuilder.jwkSetUri("http://localhost:8080");

        clientRegistrations.add(lineBuilder.build());

        return new InMemoryClientRegistrationRepository(clientRegistrations);
    }


    @Bean
    public JwtDecoderFactory<ClientRegistration> idTokenecoderFactory() {
        OidcIdTokenDecoderFactory idTokenDecoderFactory = new OidcIdTokenDecoderFactory();
        idTokenDecoderFactory.setJwsAlgorithmResolver(clientRegistration -> MacAlgorithm.HS256);
        return idTokenDecoderFactory;
    }
}
