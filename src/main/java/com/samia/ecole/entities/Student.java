package com.samia.ecole.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
    @Column(name="profile_image", nullable = false)
    private String profileImage;
    //@JsonFormat(pattern="dd-MM-yyyy")
    @JsonProperty("birthday")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name="birthday", nullable = false)
    private LocalDate birthday;
    @JsonProperty("presence")
    @JsonSerialize( using = BooleanSerializer.class)
    @JsonDeserialize(using = NumberDeserializers.BooleanDeserializer.class)
    @Column(name="presence", nullable = false)
    private Boolean presence;
    @JsonProperty("cantine")
    @JsonSerialize( using = BooleanSerializer.class)
    @JsonDeserialize(using = NumberDeserializers.BooleanDeserializer.class)
    @Column(name="cantine", nullable = false)
    private Boolean cantine;

    public Student() {
    }

    public Student(String name, String profileImage, LocalDate birthday, Boolean presence, Boolean cantine) {
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.presence = presence;
        this.cantine = cantine;
    }

    public Student(Long id, String name, String profileImage, LocalDate birthday, Boolean presence, Boolean cantine) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.presence = presence;
        this.cantine = cantine;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", birthday=" + birthday +
                ", presence=" + presence +
                ", cantine=" + cantine +
                '}';
    }
}
