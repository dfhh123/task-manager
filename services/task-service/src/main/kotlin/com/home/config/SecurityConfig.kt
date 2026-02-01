package com.home.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.oauth2.jwt.Jwt
import kotlin.collections.emptyList

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(filterChain: HttpSecurity): SecurityFilterChain  {
        filterChain
            .authorizeHttpRequests { auth -> auth
                .anyRequest().authenticated()
            }
        return filterChain.build()
    }

    @Bean
    fun keycloakJwtAuthConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthoritiesFromJwt);
        return converter;
    }

    private fun extractAuthoritiesFromJwt(jwt: Jwt): Collection<GrantedAuthority> {
        val realmRoles = (jwt.getClaim<Map<String, Any>?>("realm_access")
            ?.get("roles") as? List<String>)
            ?.map { role -> SimpleGrantedAuthority("ROLE_${role.uppercase()}") }
            ?: emptyList()

        val clientRoles = (jwt.getClaim<Map<String, Any>?>("resource_access")
            ?.values
            ?.mapNotNull { it as? Map<String, Any> }
            ?.flatMap { client ->
                (client["roles"] as? List<String>)?.map { role ->
                    SimpleGrantedAuthority("ROLE_${role.uppercase()}")
                } ?: emptyList()
            }) ?: emptyList()

        val authorities = realmRoles + clientRoles
        return authorities
    }


}