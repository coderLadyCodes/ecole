package com.samia.ecole.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class PostDTO {
    private Long id;
    private String title;
    private String postContent;
    private String imagePost;
    @JsonProperty("local_date_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime localDateTime;
    private Long userId;
    private Long classroomId;

    public PostDTO() {
    }

    public PostDTO(String title, String postContent, String imagePost, LocalDateTime localDateTime, Long userId, Long classroomId) {
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
        this.userId = userId;
        this.classroomId = classroomId;
    }

    public PostDTO(Long id, String title, String postContent, String imagePost, LocalDateTime localDateTime, Long userId, Long classroomId) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
        this.userId = userId;
        this.classroomId = classroomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(String imagePost) {
        this.imagePost = imagePost;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postContent='" + postContent + '\'' +
                ", imagePost='" + imagePost + '\'' +
                ", localDateTime=" + localDateTime +
                ", userId=" + userId +
                ", classroomId=" + classroomId +
                '}';
    }
}
