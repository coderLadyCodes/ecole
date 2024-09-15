package com.samia.ecole.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;

    public WebSocketConfig(WebSocketHandshakeInterceptor webSocketHandshakeInterceptor) {
        this.webSocketHandshakeInterceptor = webSocketHandshakeInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint("/ws")
              .addInterceptors(webSocketHandshakeInterceptor)
              .setAllowedOriginPatterns("*")
              .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
      registry.setApplicationDestinationPrefixes("/app");
      registry.enableSimpleBroker("/topic");
    }

//    @Override
//   public void configureClientInboundChannel(ChannelRegistration registration) {
//       registration.interceptors(jwtChannelInterceptor);
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//                if(StompCommand.CONNECT.equals(accessor.getCommand())){
//                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                    if(auth != null && auth.isAuthenticated()){
//                        accessor.setUser(auth);
//                    }
//                }
//                return message;
//            }
//        });
//   }

}
