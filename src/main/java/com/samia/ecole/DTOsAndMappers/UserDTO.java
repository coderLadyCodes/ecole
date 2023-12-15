package com.samia.ecole.DTOsAndMappers;

public record UserDTO(
        Long id,
        String name,
        String email,
        String phone,
        String profileImage
) {
}
