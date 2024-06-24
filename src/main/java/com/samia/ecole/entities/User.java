package com.samia.ecole.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {
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
    @Column(name="password", nullable = false)
    private String password;

    @Column(name="profile_image")
    private String profileImage;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Post> postList=new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Student> studentList = new ArrayList<>();
    @Column(name = "active_account")
    private boolean actif = false;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role.toString()));
        return this.role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }
    public User() {
    }

    public User(String name, String email, String phone, String password, String profileImage, Role role, List<Post> postList, List<Student> studentList, boolean actif) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.postList = postList;
        this.studentList = studentList;
        this.actif = actif;
    }

    public User(Long id, String name, String email, String phone, String password, String profileImage, Role role, List<Post> postList, List<Student> studentList, boolean actif) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
        this.postList = postList;
        this.studentList = studentList;
        this.actif = actif;
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

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
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
                ", actif=" + actif +
                '}';
    }

    private User(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
        this.profileImage = builder.profileImage;
        this.role = builder.role;
        this.postList = builder.postList;
        this.studentList = builder.studentList;
        this.actif = builder.actif;
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String password;
        private String profileImage;
        private Role role;
        private List<Post> postList=new ArrayList<>();
        private List<Student> studentList = new ArrayList<>();
        private boolean actif = false;

        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }
        public Builder profileImage(String profileImage){
            this.profileImage = profileImage;
            return this;
        }
        public Builder role(Role role){
            this.role = role;
            return this;
        }
        public Builder postList(List<Post> postList){
            this.postList = postList;
            return this;
        }
        public Builder studentList(List<Student> studentList){
            this.studentList = studentList;
            return this;
        }
        public Builder actif(boolean actif){
            this.actif = actif;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }
}
