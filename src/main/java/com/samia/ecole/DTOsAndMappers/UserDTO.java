package com.samia.ecole.DTOsAndMappers;

public class UserDTO {
   private Long id;
    private String name;
    private String email;
    private String phone;
    private String profileImage;

    public UserDTO() {
    }

    public UserDTO(String name, String email, String phone, String profileImage) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profileImage = profileImage;
    }

    public UserDTO(Long id, String name, String email, String phone, String profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profileImage = profileImage;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
