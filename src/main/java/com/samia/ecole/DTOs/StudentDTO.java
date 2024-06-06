package com.samia.ecole.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
    private String classe;
    @JsonProperty("presence")
    @JsonSerialize
    @JsonDeserialize()
    private Boolean presence;
    @JsonProperty("cantine")
    @JsonSerialize
    @JsonDeserialize()
    private Boolean cantine;
    private Long userId;

    public StudentDTO() {
    }

    public StudentDTO(String name, String profileImage, LocalDate birthday, String classe, Boolean presence, Boolean cantine, Long userId) {
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.classe = classe;
        this.presence = presence;
        this.cantine = cantine;
        this.userId = userId;
    }

    public StudentDTO(Long id, String name, String profileImage, LocalDate birthday, String classe, Boolean presence, Boolean cantine, Long userId) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.classe = classe;
        this.presence = presence;
        this.cantine = cantine;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", birthday=" + birthday +
                ", classe='" + classe + '\'' +
                ", presence=" + presence +
                ", cantine=" + cantine +
                ", userId=" + userId +
                '}';
    }
}
