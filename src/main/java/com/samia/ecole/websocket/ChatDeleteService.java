package com.samia.ecole.websocket;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ChatDeleteService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatDeleteService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    // Scheduled to run once a day at midnight
    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    public void deleteOldMessages(){
        LocalDateTime deleteDate =  LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        chatMessageRepository.deleteByLocalDateTimeBefore(deleteDate);
        System.out.println("Deleted messages older than " + deleteDate);
    }
}
