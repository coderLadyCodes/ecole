package com.samia.ecole.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="comment_content")
    private String commentContent;
    @Column(name="local_date_time")
    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime localDateTime;
    @ManyToOne
    @JoinColumn(name = "post")
    //@JsonBackReference
    //@JsonIgnore
    private Post post;

    @OneToOne
    @JoinColumn(name = "user")
    //@JsonBackReference
    //@JsonIgnore
    //@JsonManagedReference
    private User user;

    public Comment() {
    }

    public Comment(String commentContent, LocalDateTime localDateTime, Post post, User user) {
        this.commentContent = commentContent;
        this.localDateTime = localDateTime;
        this.post = post;
        this.user = user;
    }

    public Comment(Long id, String commentContent, LocalDateTime localDateTime, Post post, User user) {
        this.id = id;
        this.commentContent = commentContent;
        this.localDateTime = localDateTime;
        this.post = post;
        this.user = user;
    }

    public Comment(Long id, String commentContent, LocalDateTime localDateTime) {
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentContent='" + commentContent + '\'' +
                ", localDateTime=" + localDateTime +
                ", post=" + post +
                ", user=" + user +
                '}';
    }
}
