package org.pollub.campusmate.utilities.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/authenticate").permitAll()
                        .requestMatchers("/api/auth/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole("STUDENT", "LECTURER")
                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/change-password/**").hasAnyRole("STUDENT", "LECTURER", "ADMIN")
                        .requestMatchers("/api/user/grades/**").hasAnyRole("STUDENT", "LECTURER")
                        .requestMatchers("/api/user/calendar/**").hasAnyRole("STUDENT", "LECTURER")
                        .requestMatchers("/api/user/events/**").hasAnyRole("STUDENT", "LECTURER")
                        .requestMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/team/**").hasAnyRole("STUDENT", "LECTURER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/team/**").hasAnyRole("LECTURER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/team/**").hasAnyRole("LECTURER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/team/**").hasAnyRole("LECTURER", "ADMIN")

                        //todo: add endpoints


                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
