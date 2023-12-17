package com.samia.ecole.DTOsAndMappers;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String commentContent;
    private LocalDateTime localDateTime;

    public CommentDTO() {
    }

    public CommentDTO(String commentContent, LocalDateTime localDateTime) {
        this.commentContent = commentContent;
        this.localDateTime = localDateTime;
    }

    public CommentDTO(Long id, String commentContent, LocalDateTime localDateTime) {
        this.id = id;
        this.commentContent = commentContent;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", commentContent='" + commentContent + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
