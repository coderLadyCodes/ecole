package com.samia.ecole.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.samia.ecole.entities.Grade;

import java.time.LocalDate;

public class StudentDTO {
    private Long id;
    private String name;
    private String profileImage;
    @JsonProperty("birthday")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;
//    private String classe;
//    @JsonProperty("absence")
//    @JsonSerialize
//    @JsonDeserialize()
//    private Boolean absence;
//    @JsonProperty("cantine")
//    @JsonSerialize
//    @JsonDeserialize()
//    private Boolean cantine;
//    private Garderie garderie;
    private Grade grade;
    private Long userId;
    private Long classroomId;
    public StudentDTO() {
    }

    public StudentDTO(String name, String profileImage, LocalDate birthday, Grade grade, Long userId, Long classroomId) {
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.grade = grade;
        this.userId = userId;
        this.classroomId = classroomId;
    }

    public StudentDTO(Long id, String name, String profileImage, LocalDate birthday, Grade grade, Long userId, Long classroomId) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.grade = grade;
        this.userId = userId;
        this.classroomId = classroomId;
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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", birthday=" + birthday +
                ", grade=" + grade +
                ", userId=" + userId +
                ", classroomId=" + classroomId +
                '}';
    }
}
