package br.com.lecom.oauth.config.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
			RegisteredClientRepository registeredClientRepository, OAuth2AuthorizationService auth2AuthorizationService,
			OAuth2AuthorizationConsentService auth2AuthorizationConsentService) throws Exception {
		// @formatter:off
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults()) // Enable OpenID
				.authorizationService(auth2AuthorizationService)
				.authorizationConsentService(auth2AuthorizationConsentService)
				.registeredClientRepository(registeredClientRepository);
		// Connect 1.0
		http
				// Redirect to the login page when not authenticated from the
				// authorization endpoint
				.exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
						new LoginUrlAuthenticationEntryPoint("/login"),
						new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
				// Accept access tokens for User Info and/or Client Registration
				.oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
		// @formatter:on		

		return http.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				// Form login handles the redirect to the login page from the
				// authorization server filter chain
				.formLogin(Customizer.withDefaults());

		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return SecurityConfig.delegatingPasswordEncoder();
	}
	
	public static PasswordEncoder delegatingPasswordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

		return new DelegatingPasswordEncoder("argon2", encoders);
	}

	@Bean
	UserDetailsService userDetailsService(SecurityProperties properties) {

		UserDetails userDetails = User.withUsername(properties.getUser().getName())
				.password("{argon2}" + properties.getUser().getPassword())
				.authorities(properties.getUser().getRoles().stream().map(SimpleGrantedAuthority::new).toList())
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	OAuth2AuthorizationService auth2AuthorizationService(DataSource dataSource,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(new JdbcTemplate(dataSource), registeredClientRepository);
	}

	@Bean
	OAuth2AuthorizationConsentService auth2AuthorizationConsentService(DataSource dataSource,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(new JdbcTemplate(dataSource), registeredClientRepository);
	}

	@Bean
	RegisteredClientRepository registeredClientRepository(DataSource dataSource) {
//		RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString()).clientId("oidc-client")
//				.clientSecret("{noop}secret").clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
//				.postLogoutRedirectUri("http://127.0.0.1:8080/").scope(OidcScopes.OPENID).scope(OidcScopes.PROFILE).scope("read")
//				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();

//		JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(
//				new JdbcTemplate(dataSource));
//		jdbcRegisteredClientRepository.save(oidcClient);

		return new JdbcRegisteredClientRepository(new JdbcTemplate(dataSource));
//		return new InMemoryRegisteredClientRepository(oidcClient);
	}

	@Bean
	JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	@Bean
	JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

}
