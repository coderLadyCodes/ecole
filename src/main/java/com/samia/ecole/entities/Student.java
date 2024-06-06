package com.samia.ecole.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="profile_image")
    private String profileImage;
    @Column(name="birthday", nullable = false)
    private LocalDate birthday;
    @Column(name="classe", nullable = false)
    private String classe;
    @Column(name="presence", nullable = false)
    private Boolean presence;
    @Column(name="cantine", nullable = false)
    private Boolean cantine;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    public Student() {
    }

    public Student(String name, String profileImage, LocalDate birthday, String classe, Boolean presence, Boolean cantine, User user) {
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.classe = classe;
        this.presence = presence;
        this.cantine = cantine;
        this.user = user;
    }

    public Student(Long id, String name, String profileImage, LocalDate birthday, String classe, Boolean presence, Boolean cantine, User user) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.classe = classe;
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

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Boolean getPresence() {
        return presence;
    }

    public void setPresence(Boolean presence) {
        this.presence = presence;
    }

    public Boolean getCantine() {
        return cantine;
    }

    public void setCantine(Boolean cantine) {
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
                ", classe='" + classe + '\'' +
                ", presence=" + presence +
                ", cantine=" + cantine +
                ", user=" + user +
                '}';
    }
}
