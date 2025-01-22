package org.pollub.campusmate.utilities.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.authorization.AuthenticatedAuthorizationManager.anonymous;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST ,"/api/auth/login").permitAll()
                        .requestMatchers("/api/schedule/**").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/logout").hasAnyRole("STUDENT", "ADMIN", "LECTURER")
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers("/2fa/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/user/{userId}").hasAnyRole("STUDENT","LECTURER", "ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/role/{role}").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.GET, "/api/user").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.POST, "api/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/user/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/user/{userId}").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers(HttpMethod.PUT, "/api/user/change-password/{userId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/user/grades/{userId}").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/user/events/{userId}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/event").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/team/{teamId}").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/team").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.POST, "/api/team/{teamId}/addUsers").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.DELETE, "/api/team/{teamId}/removeUser/{userId}").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.DELETE, "/api/team/{teamId}").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.PATCH, "/api/team/{teamId}").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.GET, "/api/team/{teamId}/users").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        //.requestMatchers(HttpMethod.GET, "/api/team/{teamId}/grades").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.GET, "/api/team/{teamId}/events").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/team/{teamId}/posts").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/post/{postId}").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/post").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.DELETE, "/api/post/{postId}").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.DELETE, "api/post/{postId}").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.PATCH, "/api/post/{postId}").hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers(HttpMethod.GET, "/api/post").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/calendar/schedule").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080")); // Dodaj swoje adresy
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*", "Content-Type", "Authorization")); // Zmień z Collections.singletonList na Arrays.asList
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
