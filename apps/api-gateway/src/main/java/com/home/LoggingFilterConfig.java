package com.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

@Configuration
public class LoggingFilterConfig {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilterConfig.class);

    @Bean
    public GlobalFilter loggingFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Request: {} {}", request.getMethod(), request.getPath());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Response status: {}",
                        exchange.getResponse().getStatusCode());
            }));
        };
    }
}