package com.home.userservice.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Stream;

@Configuration
@Slf4j
@EnableMethodSecurity
public class SecurityConfig {

    private static final String DEFAULT_CRUD_URL_PATTERN = "/api/rest/*/users/**";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/rest/*/users/registration").anonymous()
                        .requestMatchers("/api/rest/*/users/me").hasRole(USER_ROLE)
                        .requestMatchers(DEFAULT_CRUD_URL_PATTERN).hasRole(ADMIN_ROLE)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakJwtAuthConverter()))
                );

        return http.build();
    }

    @Bean
    JwtAuthenticationConverter keycloakJwtAuthConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthoritiesFromJwt);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthoritiesFromJwt(Jwt jwt) {
        Stream<GrantedAuthority> realmRoles = Optional.ofNullable((Map<String, Object>) jwt.getClaim("realm_access"))
                .map(claimMap -> (List<String>) claimMap.getOrDefault("roles", List.of()))
                .stream()
                .flatMap(Collection::stream)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

        Stream<GrantedAuthority> clientRoles = Optional.ofNullable((Map<String, Object>) jwt.getClaim("resource_access"))
                .orElse(Map.of())
                .values().stream()
                .map(obj -> (Map<String, Object>) obj)
                .map(client -> (List<String>) client.getOrDefault("roles", List.of()))
                .flatMap(Collection::stream)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

        Collection<GrantedAuthority> authorities = Stream.concat(realmRoles, clientRoles).toList();
        log.debug("Extracted authorities from JWT: {}", authorities);
        return authorities;
    }
}