package com.samia.ecole.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotEmpty
    @Size(min = 4, message = "name must be min of 4 characters")
    @Column(name="name", nullable = false)
    private String name;
    @Email(message = "Email is not valid")
    @Column(name="email",unique = true, nullable = false)
    private String email;
    @Email(message = "Phone number is not valid")
    @Column(name="phone",unique = true)
    @NotEmpty
    //@Pattern(regexp = "^\\d{10}$")
    private String phone;
    @NotEmpty
    @Size(min = 8, message = "password must be greater than 8 characters")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password", nullable = false)
    private String password;
    @NotEmpty
    @Column(name="profile_image")
    private String profileImage;
    @Column(name = "role")
    private Role role;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    //@JsonBackReference
    private List<Post> postList=new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "student")
    private Student student;
    public User() {
    }

    public User(String name, String email, String phone, String password, String profileImage, Role role, List<Post> postList, Student student) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.postList = postList;
        this.student = student;
    }

    public User(Long id, String name, String email, String phone, String password, String profileImage, Role role, List<Post> postList, Student student) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
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
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", role=" + role +
                ", postList=" + postList +
                ", student=" + student +
                '}';
    }
}
