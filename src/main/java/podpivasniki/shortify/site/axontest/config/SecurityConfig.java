package podpivasniki.shortify.site.axontest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configures the security filters for HTTP requests.
     * <p>
     * Override this method to customize the security filter chain safely.
     *
     * @param http the HTTP security configuration to modify
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http)
            throws Exception {
        return http
                .sessionManagement(s -> s.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
                            "/swagger-ui.html").permitAll();
                    request.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.disable())
                .cors(AbstractHttpConfigurer::disable)  // Отключение CORS
                .build();
    }

    /**
     * Configures the CORS configuration source.
     * <p>
     * Override this method to provide a customized CORS configuration source safely.
     *
     * @return the configured CORS configuration source
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
