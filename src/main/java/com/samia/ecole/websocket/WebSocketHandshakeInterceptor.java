package com.samia.ecole.websocket;

import com.samia.ecole.security.JwtService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;

    public WebSocketHandshakeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String token = request.getHeaders().getFirst("Authorization");
        if (token != null && isValidToken(token)) {
            attributes.put("user", extractUserFromToken(token));
            return true;
        }
//        if (token != null && token.startsWith("Bearer ")){
//            token = token.substring(7);
//
//            boolean valid = !jwtService.isTokenExpired(token);
//            if(!valid){
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return false;
//            }
//        }
//        return true;
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
    private boolean isValidToken(String token) {
        return jwtService.isTokenExpired(token);
    }

    private String extractUserFromToken(String token) {
        return jwtService.extractUsername(token);
    }
}
