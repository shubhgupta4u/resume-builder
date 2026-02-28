package com.auth.config;

// import com.auth.entity.OAuthClient;
// import com.auth.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import com.auth.models.ClientDTO;
import com.auth.services.AdminClientService;

// import com.auth.config.AuthServerProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    @Value("${auth.token.expiry}")
    private int expiryDuration;

    // ================= AUTH SERVER FILTER CHAIN =================
    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http
            .securityMatcher("/oauth2/**", "/.well-known/**")
            .csrf(csrf -> csrf.ignoringRequestMatchers("/oauth2/**"))
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, config ->
                config.oidc(Customizer.withDefaults())
            )
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()));

        return http.build();
    }

    // ================= DEFAULT FILTER CHAIN =================
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/**", "/actuator/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/health", "/actuator/**").permitAll()
                        //.requestMatchers("/api/admin/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()));
                //.formLogin(Customizer.withDefaults());

        return http.build();
    }

    // ================= CLIENT REPOSITORY =================
    @Bean
    public RegisteredClientRepository registeredClientRepository(
            AdminClientService adminClientService,
            PasswordEncoder passwordEncoder) {

        return new RegisteredClientRepository() {

            @Override
            public void save(RegisteredClient registeredClient) {
                throw new UnsupportedOperationException();
            }

            @Override
            public RegisteredClient findById(String id) {
                return null;
            }

            @Override
            public RegisteredClient findByClientId(String clientId) {
                System.out.println("Looking for client: " + clientId);

                ClientDTO client = adminClientService.loadClient(clientId);

                System.out.println("Found client in Admin Service: " + client.clientId());
                System.out.println("Secret in DB: " + client.clientSecret());
                System.out.println("Scopes: " + client.scopes());

                return RegisteredClient.withId(client.id())
                        .clientId(client.clientId())
                        .clientSecret(client.clientSecret())
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofSeconds(expiryDuration))
                                .build())
                        .scopes(scopes -> scopes.addAll(client.scopes()))
                        .build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Value("${auth.issuer}")
    private String issuer;

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(issuer)
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}