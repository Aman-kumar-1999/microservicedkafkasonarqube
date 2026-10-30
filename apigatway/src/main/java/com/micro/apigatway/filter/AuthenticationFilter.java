package com.micro.apigatway.filter;

import com.micro.apigatway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<Object> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    // Constructor injection ensures dependencies are never null
    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Object.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {

                // 1. Check if Authorization header is missing
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Access Denied: Missing Authorization header.", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                // 2. Validate Bearer token format
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return onError(exchange, "Invalid Authorization format. Token must start with 'Bearer '.", HttpStatus.UNAUTHORIZED);
                }

                String token = authHeader.substring(7);

                try {
                    // 3. Validate JWT token
                    jwtUtil.validateToken(token);

                } catch (Exception e) {
                    System.out.println("Authentication error: " + e.getMessage());
                    return onError(exchange, "Unauthorized Access: Your session is invalid or has expired.", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    /**
     * Helper method to return a structured JSON error response with correct HTTP 401.
     */
    private Mono<Void> onError(org.springframework.web.server.ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String errorJsonBody = String.format(
                "{\"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                status.value(),
                status.getReasonPhrase(),
                message,
                exchange.getRequest().getPath()
        );

        DataBuffer buffer = response.bufferFactory().wrap(errorJsonBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}


//package com.micro.apigatway.filter;
//
//
//import com.micro.apigatway.util.JwtUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
//
//@Component
//@Slf4j
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//    private RouteValidator validator;
//
//    private JwtUtil jwtUtil;
//
//
//    public AuthenticationFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            if (validator.isSecured.test(exchange.getRequest())) {
//
//                // 1. Check if Authorization header is missing
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    return onError(exchange, "Access Denied: Missing Authorization header.", HttpStatus.UNAUTHORIZED);
//                }
//
//                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//                // 2. Validate Bearer token format
//                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                    return onError(exchange, "Invalid Authorization format. Token must start with 'Bearer '.", HttpStatus.UNAUTHORIZED);
//                }
//
//                String token = authHeader.substring(7);
//
//                try {
//                    // 3. Validate JWT token
//                    jwtUtil.validateToken(token);
//
//                } catch (Exception e) {
//                    log.info("Authentication error: " + e.getMessage());
//                    return onError(exchange, "Unauthorized Access: Your session is invalid or has expired.", HttpStatus.UNAUTHORIZED);
//                }
//            }
//            return chain.filter(exchange);
//        };
//    }
//
//    /**
//     * Helper method to return a clean, structured JSON error response.
//     */
//    private Mono<Void> onError(org.springframework.web.server.ServerWebExchange exchange, String message, HttpStatus status) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(status);
//        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//        // Constructing a structured JSON payload
//        String errorJsonBody = String.format(
//                "{\"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
//                status.value(),
//                status.getReasonPhrase(),
//                message,
//                exchange.getRequest().getPath()
//        );
//
//        DataBuffer buffer = response.bufferFactory().wrap(errorJsonBody.getBytes(StandardCharsets.UTF_8));
//        return response.writeWith(Mono.just(buffer));
//    }
//
//    public static class Config {
//
//    }
//}