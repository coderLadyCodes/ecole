package com.samia.ecole.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="posts")
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="post_content", nullable = false)
    private String postContent;
    @Column(name="image_post")
    private String imagePost;
    @JsonProperty("local_date_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name="local_date_time")
    private LocalDateTime localDateTime;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "user")
    //@JsonManagedReference
    //@JsonIgnore
    //@JsonBackReference
    @Transient
    private User user;
    @OneToMany(mappedBy ="post",cascade = CascadeType.ALL)
    //@JsonManagedReference
    @Transient
    //@JsonBackReference
    private List<Comment> commentList;

    public Post() {
    }

    public Post(String title, String postContent, String imagePost, LocalDateTime localDateTime, User user, List<Comment> commentList) {
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
        this.user = user;
        this.commentList = commentList;
    }

    public Post(Long id, String title, String postContent, String imagePost, LocalDateTime localDateTime, User user, List<Comment> commentList) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
        this.user = user;
        this.commentList = commentList;
    }

    public Post(Long id, String title, String postContent, String imagePost, LocalDateTime localDateTime) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postContent='" + postContent + '\'' +
                ", imagePost='" + imagePost + '\'' +
                ", localDateTime=" + localDateTime +
                ", user=" + user +
                ", commentList=" + commentList +
                '}';
    }
}
