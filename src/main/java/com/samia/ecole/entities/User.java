package com.samia.ecole.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    @NotEmpty
    @Size(min = 4, message = "name must be min of 4 characters")
    @Column(name="name", nullable = false)
    private String name;
    @Email(message = "Email is not valid")
    @Column(name="email",unique = true, nullable = false)
    private String email;
    @NotEmpty
    @Size(min = 4, max = 12, message = "password must be minimum of 4 chars and max of 12 chars")
    //@Pattern(regexp = )
    @Column(name="password", nullable = false)
    private String password;
    @NotEmpty
    @Column(name="profile_image")
    private String profileImage;
    @Column(name = "role")
    private Role role;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> postList=new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "student")
    private Student student;
    public User() {
    }

    public User(String name, String email, String password, String profileImage, Role role, List<Post> postList, Student student) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.postList = postList;
        this.student = student;
    }

    public User(Long id, String name, String email, String password, String profileImage, Role role, List<Post> postList, Student student) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.postList = postList;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", role=" + role +
                ", postList=" + postList +
                ", student=" + student +
                '}';
    }
}
