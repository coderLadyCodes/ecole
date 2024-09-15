package com.samia.ecole.websocket;

import com.samia.ecole.entities.Jwt;
import com.samia.ecole.security.JwtService;
import com.samia.ecole.services.UserService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    private final UserService userService;

    public JwtChannelInterceptor(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(accessor != null && accessor.getCommand() != null){
            String authToken = accessor.getFirstNativeHeader("Authorization");

            if(authToken != null && authToken.startsWith("Bearer ")){
                authToken = authToken.substring(7);
                try{
                    String username = jwtService.extractUsername(authToken);
                    Jwt tokenInDb = jwtService.tokenByValue(authToken);
                    boolean isTokenExpired = jwtService.isTokenExpired(authToken);

                    if(!isTokenExpired && tokenInDb.getUser().getEmail().equals(username)){
                        UserDetails userDetails = userService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, true, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                } catch( Exception e) {
                    SecurityContextHolder.clearContext();
                }
            }
        }
        return message;
    }
}
