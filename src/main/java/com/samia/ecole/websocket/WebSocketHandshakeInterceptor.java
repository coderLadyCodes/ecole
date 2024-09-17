package com.samia.ecole.websocket;

import com.samia.ecole.entities.User;
import com.samia.ecole.security.JwtService;
import com.samia.ecole.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;
    private final UserService userService;

    public WebSocketHandshakeInterceptor(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //String token = request.getHeaders().getFirst("Authorization");
        HttpHeaders httpHeaders = request.getHeaders();
        String cookieHeader = httpHeaders.getFirst(HttpHeaders.COOKIE);
        
        if (cookieHeader != null) {
            String token = extractTokenFromCookies(cookieHeader);
            if (token != null && isValidToken(token)) {
                String username = extractUserFromToken(token);
                User user = userService.loadUserByUsername(username);
                attributes.put("user", user);
                return true;
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//            if (isValidToken(token)) {
//                attributes.put("user", extractUserFromToken(token));
//                return true;
//            } else {
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return false;
//            }
//        }
//      return false;
    }

    private String extractTokenFromCookies(String cookieHeader) {
        String[] cookies = cookieHeader.split(";");
        for (String cookie : cookies){
            if (cookie.startsWith("token=")) {
                return cookie.substring("token=".length());
            }
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if (exception != null) {
            exception.printStackTrace();
        }
    }
    private boolean isValidToken(String token) {
        return !jwtService.isTokenExpired(token);
    }

    private String extractUserFromToken(String token) {
        return jwtService.extractUsername(token);
    }
}
