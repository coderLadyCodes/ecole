package com.samia.ecole.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="profile_image", nullable = false)
    private String profileImage;
    @Column(name="birthday", nullable = false)
    private LocalDate birthday;
    @Column(name="presence", nullable = false)
    private boolean presence;
    @Column(name="cantine", nullable = false)
    private boolean cantine;
    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    public Student() {
    }

    public Student(String name, String profileImage, LocalDate birthday, boolean presence, boolean cantine, User user) {
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.presence = presence;
        this.cantine = cantine;
        this.user = user;
    }

    public Student(Long id, String name, String profileImage, LocalDate birthday, boolean presence, boolean cantine, User user) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.presence = presence;
        this.cantine = cantine;
        this.user = user;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public boolean isCantine() {
        return cantine;
    }

    public void setCantine(boolean cantine) {
        this.cantine = cantine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", birthday=" + birthday +
                ", presence=" + presence +
                ", cantine=" + cantine +
                ", user=" + user +
                '}';
    }
}
