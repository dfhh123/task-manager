package com.home.userservice.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/actuator/prometheus").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/rest/v1/users/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/rest/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/rest/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/rest/v1/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakJwtAuthConverter()))
                );
        return http.build();
    }

    @Bean
    Converter<Jwt, ? extends Authentication> keycloakJwtAuthConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractKeycloakAuthorities);
        return converter;
    }


    private Collection<? extends GrantedAuthority> extractKeycloakAuthorities(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            Object roles = realmAccess.get("roles");
            if (roles instanceof List<?> list) {
                list.stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .map(role -> "ROLE_" + role.toUpperCase())
                        .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                        .forEach(authorities::add);
            }
        }

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            resourceAccess.values().forEach(clientObj -> {
                if (clientObj instanceof Map<?, ?> client && client.get("roles") instanceof List<?> list) {
                    list.stream()
                            .filter(String.class::isInstance)
                            .map(String.class::cast)
                            .map(role -> "ROLE_" + role.toUpperCase())
                            .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                            .forEach(authorities::add);
                }
            });
        }

        return authorities;
    }
}
