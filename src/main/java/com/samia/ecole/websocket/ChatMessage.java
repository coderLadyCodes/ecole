package com.samia.ecole.websocket;

import com.samia.ecole.entities.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message")
    private String message;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long classroomId;
    @Column(name = "local_date_time", nullable = false)
    private LocalDateTime localDateTime;
    private TypeMessage typeMessage;
    public ChatMessage(){}

    public ChatMessage(String message, User user, Long classroomId, LocalDateTime localDateTime, TypeMessage typeMessage) {
        this.message = message;
        this.user = user;
        this.classroomId = classroomId;
        this.localDateTime = localDateTime;
        this.typeMessage = typeMessage;
    }

    public ChatMessage(Long id, String message, User user, Long classroomId, LocalDateTime localDateTime, TypeMessage typeMessage) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.classroomId = classroomId;
        this.localDateTime = localDateTime;
        this.typeMessage = typeMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                //", user=" + user +
                ", classroomId=" + classroomId +
                ", localDateTime=" + localDateTime +
                ", typeMessage=" + typeMessage +
                '}';
    }
}
