//package com.micro.apigatway.config;
//
//
//
//
//import com.micro.apigatway.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public JwtAuthenticationFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//
//            if (!request.getHeaders().containsHeader(HttpHeaders.AUTHORIZATION)) {
//                return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
//            }
//
//            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                return onError(exchange, "Invalid Authorization Header Format", HttpStatus.UNAUTHORIZED);
//            }
//
//            String token = authHeader.substring(7);
//
//            if (!jwtUtil.validateToken(token)) {
//                return onError(exchange, "Expired or Invalid JWT Token", HttpStatus.UNAUTHORIZED);
//            }
//
//            // Extract claims and pass them downstream via headers
//            String username = jwtUtil.extractUsername(token);
//            String role = jwtUtil.extractAllClaims(token).get("role", String.class);
//
//            ServerHttpRequest modifiedRequest = request.mutate()
//                    .header("X-User-Name", username)
//                    .header("X-User-Role", role)
//                    .build();
//
//            return chain.filter(exchange.mutate().request(modifiedRequest).build());
//        };
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(httpStatus);
//        return response.setComplete();
//    }
//
//    public static class Config {
//        // Configuration properties if needed
//    }
//}
//
//
//
////
////import com.micro.apigatway.security.JwtUtil;
////import jakarta.servlet.FilterChain;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import jakarta.ws.rs.core.HttpHeaders;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
////import org.springframework.stereotype.Component;
////import org.springframework.web.filter.OncePerRequestFilter;
////
////
////import java.io.IOException;
////import java.util.ArrayList;
////
////@Component
////public class JwtAuthenticationFilter extends OncePerRequestFilter {
////
////    @Autowired
////    private JwtUtil jwtUtil;
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////            throws ServletException, IOException {
////
////        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
////
////        if (authHeader != null && authHeader.startsWith("Bearer ")) {
////            String token = authHeader.substring(7);
////
////            if (jwtUtil.validateToken(token)) {
////                String username = jwtUtil.extractUsername(token);
////
////                // Set authentication in Spring Security context so WebMVC Gateway allows the request through
////                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
////                        username, null, new ArrayList<>()
////                );
////                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////                SecurityContextHolder.getContext().setAuthentication(authToken);
////            }
////        }
////
////        filterChain.doFilter(request, response);
////    }
////}