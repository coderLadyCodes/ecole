package com.samia.ecole.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Table(name="posts")
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="post_content", nullable = false, columnDefinition = "TEXT")
    private String postContent;
    @Column(name="image_post")
    private String imagePost;
    @JsonProperty("local_date_time")
    @Column(name="local_date_time")
    private LocalDateTime localDateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private Long classroomId;
//    @OneToMany(mappedBy ="post",cascade = CascadeType.ALL)
//    //@JsonManagedReference
//    @Transient
//    //@JsonBackReference
//    private List<Comment> commentList;

    public Post() {
    }

    public Post(String title, String postContent, String imagePost, LocalDateTime localDateTime, User user, Long classroomId) {
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
        this.user = user;
        this.classroomId = classroomId;
    }

    public Post(Long id, String title, String postContent, String imagePost, LocalDateTime localDateTime, User user, Long classroomId) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.imagePost = imagePost;
        this.localDateTime = localDateTime;
        this.user = user;
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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postContent='" + postContent + '\'' +
                ", imagePost='" + imagePost + '\'' +
                ", localDateTime=" + localDateTime +
                ", user=" + user +
                ", classroomId=" + classroomId +
                '}';
    }
}
