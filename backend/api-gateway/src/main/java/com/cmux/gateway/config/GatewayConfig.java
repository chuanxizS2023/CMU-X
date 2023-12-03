package com.cmux.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/user/**")
                    .uri("http://user-service:5002"))
            .route("auth-service", r -> r.path("/auth/**")
                    .uri("http://user-service:5002"))
            .route("ws-chat-service", r -> r.path("/ws-chat/**")
                    .uri("ws://chat-service:8080"))
            .route("ws-post-service", r -> r.path("/ws-communitypost/**")
                    .uri("ws://post-service:9000"))
            .build();
    }
}
