package com.samia.ecole.DTOsAndMappers;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class StudentDTO {
    private Long id;
    private String name;
    private String profileImage;
    private LocalDate birthday;
    private boolean presence;
    private boolean cantine;

    public StudentDTO() {
    }

    public StudentDTO(String name, String profileImage, LocalDate birthday, boolean presence, boolean cantine) {
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.presence = presence;
        this.cantine = cantine;
    }

    public StudentDTO(Long id, String name, String profileImage, LocalDate birthday, boolean presence, boolean cantine) {
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

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", birthday=" + birthday +
                ", presence=" + presence +
                ", cantine=" + cantine +
                '}';
    }
}
