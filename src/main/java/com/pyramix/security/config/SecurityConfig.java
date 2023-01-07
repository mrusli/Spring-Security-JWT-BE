package com.pyramix.security.config;

import java.util.Arrays;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.pyramix.security.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl; 
	
	private RsaKeyProperties rsaKeys;
	
	public SecurityConfig(RsaKeyProperties rsaKeys) {
		super();
		this.rsaKeys = rsaKeys;
	}

	private static final Logger log = Logger.getLogger(SecurityConfig.class);
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	log.info("filterChain...");
    	
    	http
    		.cors().and().csrf().disable();
    	
    	http
    		.userDetailsService(getUserDetailsServiceImpl());
    	
    	http
    		.authenticationProvider(authenticationProvider());
    	
    	http
    		.authorizeHttpRequests(auth -> {
    			auth.antMatchers(HttpMethod.POST, "/jwtlogin").permitAll();
    			auth.anyRequest().authenticated();  
    		});
    	
		http
			.sessionManagement(session -> 
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http
			.oauth2ResourceServer(
				OAuth2ResourceServerConfigurer::jwt);
    	
        return http.build();
    }
	
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(getUserDetailsServiceImpl());
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authProvider;
    }

    // Used by Spring Security if CORS is enabled.
    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOriginPattern("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        // ref : https://stackoverflow.com/questions/37897523/axios-get-access-to-response-header-fields
        // This allow us to expose the headers
        config.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", 
        		"Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
        		"Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }       
    
    @Bean
    JwtDecoder jwtDecoder() {
    	return NimbusJwtDecoder.withPublicKey(getRsaKeys().getPublicKey()).build();
    }
    
    @Bean
    JwtEncoder jwtEncoder() {
    	JWK jwk = new RSAKey.Builder(getRsaKeys().getPublicKey()).privateKey(getRsaKeys().getPrivateKey()).build();
    	JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    	
    	return new NimbusJwtEncoder(jwks);
    }
    
	public UserDetailsServiceImpl getUserDetailsServiceImpl() {
		return userDetailsServiceImpl;
	}

	public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	public RsaKeyProperties getRsaKeys() {
		return rsaKeys;
	}

	public void setRsaKeys(RsaKeyProperties rsaKeys) {
		this.rsaKeys = rsaKeys;
	}
    
}
