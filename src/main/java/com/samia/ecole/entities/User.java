package com.samia.ecole.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

    @Column(name="phone",unique = true)
    //@NotEmpty
    //@Pattern(regexp = "\\d{10}", message = "Phone number is not valid")
    private String phone;
    //@NotEmpty
    //@Size(min = 8, message = "password must be greater than 8 characters")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password", nullable = false)
    private String password;

    @Column(name="profile_image")
    private String profileImage;
    @Column(name = "role")
    private Role role;
//    @OneToMany//(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonIgnore
//    //@JsonBackReference
//    private List<Post> postList=new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "student")
    private Student student;
    public User() {
    }

    public User(String name, String email, String phone, String password, String profileImage, Role role, Student student) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.student = student;
    }

    public User(Long id, String name, String email, String phone, String password, String profileImage, Role role, Student student) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.student = student;
    }

    public User(Long id, String name, String email, String phone, String profileImage) {
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
    @Transient
    public String getProfileImagePath(){
       if (profileImage == null || id == null) return null;
       return "images/" + id + "/" + profileImage;
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
                ", student=" + student +
                '}';
    }
}
