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

    @MessageMapping("/chat.sendMessage/{classroomId}")
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
        chatMessage.setClassroomId(Long.parseLong(classroomId));
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }
    @MessageMapping("/chat.addUser/{classroomId}")
    @SendTo("/topic/classroom/{classroomId}")
    public  ChatMessage addUser(@Payload ChatMessage chatMessage,
                                @Header("simpSessionAttributes") Map<String, Object> attributes,
                                @DestinationVariable String classroomId){
        chatMessage.setLocalDateTime(LocalDateTime.now());
        chatMessage.setTypeMessage(TypeMessage.JOIN);
        chatMessage.setClassroomId(Long.parseLong(classroomId));
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }
}
