package com.samia.ecole.DTOs;

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
    private LocalDateTime localDateTime;

    public PostDTO() {
    }

    public PostDTO(String title, String postContent, String imagePost, LocalDateTime localDateTime) {
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
    }

    public PostDTO(Long id, String title, String postContent, String imagePost, LocalDateTime localDateTime) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
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

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postContent='" + postContent + '\'' +
                ", imagePost='" + imagePost + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
