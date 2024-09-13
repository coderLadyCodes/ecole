package com.samia.ecole.websocket;

import com.samia.ecole.entities.User;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class ChatController {
    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/classroom/{classroomId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage,
                                   @Header("simpSessionAttributes") Map<String, Object> attributes,
                                   @DestinationVariable String classroomId) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.getClassroomId().equals(chatMessage.getClassroomId())){
            throw new AccessDeniedException("You don't have access to this classroom");
        }
        chatMessage.setLocalDateTime(LocalDateTime.now());
        chatMessage.setUser(user);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/classroom/{classroomId}")
    public  ChatMessage addUser(@Payload ChatMessage chatMessage,
                                @Header("simpSessionAttributes") Map<String, Object> attributes){
        chatMessage.setLocalDateTime(LocalDateTime.now());
        chatMessage.setTypeMessage(TypeMessage.JOIN);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }
}
