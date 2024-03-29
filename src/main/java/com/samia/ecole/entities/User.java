package com.samia.ecole.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@NotEmpty
    //@Size(min = 4, message = "name must be min of 4 characters")
    @Column(name="name", nullable = false)
    private String name;
    //@Email(message = "Email is not valid")
    @Column(name="email",unique = true, nullable = false)
    private String email;

    @Column(name="phone")
    //@NotEmpty
    //@Pattern(regexp = "\\d{10}", message = "Phone number is not valid")
    private String phone;
    //@NotEmpty
    //@Size(min = 8, message = "password must be greater than 8 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password")
    private String password;

    @Column(name="profile_image")
    private String profileImage;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList=new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> studentList = new ArrayList<>();
    public User() {
    }

    public User(String name, String email, String phone, String password, String profileImage, Role role, List<Post> postList, List<Student> studentList) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.postList = postList;
        this.studentList = studentList;
    }

    public User(Long id, String name, String email, String phone, String password, String profileImage, Role role, List<Post> postList, List<Student> studentList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.postList = postList;
        this.studentList = studentList;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", role=" + role +
                ", postList=" + postList +
                ", studentList=" + studentList +
                '}';
    }
}
