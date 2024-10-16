package com.samia.ecole.services;

import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.entities.Role;
import com.samia.ecole.entities.User;
import com.samia.ecole.entities.Validation;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UserAlreadyExistsException;
import com.samia.ecole.exceptions.UserNotFoundException;
import com.samia.ecole.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
//@MultipartConfig
@Service
public class UserService implements UserDetailsService {
     private final UserRepository userRepository;
     private final BCryptPasswordEncoder passwordEncoder;
     private final ValidationService validationService;
     private final FileUploadUtil fileUploadUtil;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ValidationService validationService, FileUploadUtil fileUploadUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.fileUploadUtil = fileUploadUtil;
    }

    public UserDTO mapToUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhone(user.getPhone());
        userDTO.setProfileImage(user.getProfileImage());
        userDTO.setRole(user.getRole());
        userDTO.setClassroomId(user.getClassroomId());
        return userDTO;
    }
    public User mapToUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setProfileImage(userDTO.getProfileImage());
        user.setRole(userDTO.getRole());
        user.setClassroomId(userDTO.getClassroomId());
        return user;
    }
    public UserDTO createUser(UserDTO userDTO, MultipartFile multipartFile) throws IOException {
        User user = mapToUser(userDTO);
        if (user.getEmail() == null || !user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }
        final Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException(user.getEmail() + " existe déja !");
        }
        if (user.getEmail() == null){
            throw new UserNotFoundException("l'utilisateur ne peut pas etre NULL");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("le mot de passe ne peut pas etre null ou vide");
        }

        if (multipartFile != null && !multipartFile.isEmpty()){
            String imageUrl = fileUploadUtil.uploadFile(multipartFile);
            user.setProfileImage(imageUrl);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.PARENT);
        if(user.getRole() != null && user.getRole().equals(Role.SUPER_ADMIN)){
            user.setRole(Role.SUPER_ADMIN);
            user.setActif(true);
        }
        if(user.getRole() != null && user.getRole().equals(Role.ADMIN)){
          user.setRole(Role.ADMIN);
            user.setActif(true);
        }

        if(user.getRole().equals(Role.PARENT)) {
            user.setActif(true);
        }

        user = userRepository.save(user);
        this.validationService.enregistrer(user);
        return mapToUserDto(user);
    }
    public void activation(Map<String, String> activation){
        Validation validation = validationService.codeValidation(activation.get("code"));
        if(Instant.now().isAfter(validation.getExpiration())) {
            throw new CustomException("ce code a expiré", HttpStatus.CONFLICT);
        }
        User userActivated = userRepository.findById(validation.getUser().getId()).orElseThrow(()-> new UserNotFoundException("Utilisateur inconnu"));
        userActivated.setActif(true);
        validation.setActivation(Instant.now());
        userRepository.save(userActivated);
    }
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new UserNotFoundException("Aucun utilisateur trouvé dans la bd"));
    }
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDetails, MultipartFile multipartFile) throws IOException {
//        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("user not found ! "));
//        user.setName(userDetails.getName());
//        user.setEmail(userDetails.getEmail());
//        user.setPhone(userDetails.getPhone());
//        user.setProfileImage(userDetails.getProfileImage());
//        /*
//       // user.setRole(userDetails.getRole());
//        //user.getClassroom().setId(userDetails.getClassroomId());  NOT SURE IF IT WORKS
//        */
//
//        user.setClassroomId(user.getClassroomId());
//        User userUpdated = userRepository.save(user);
//        return mapToUserDto(userUpdated);
        User originalUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String oldImageUrl = originalUser.getProfileImage();

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String newImageUrl = fileUploadUtil.uploadFile(multipartFile);
            userDetails.setProfileImage(newImageUrl);

            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                String publicId = extractPublicIdFromUrl(oldImageUrl);
                fileUploadUtil.deleteFile(publicId);
            }
        } else {
            userDetails.setProfileImage(oldImageUrl);
        }

           originalUser.setName(userDetails.getName());
           originalUser.setEmail(userDetails.getEmail());
           originalUser.setPhone(userDetails.getPhone());
           originalUser.setProfileImage(userDetails.getProfileImage());
           originalUser.setClassroomId(originalUser.getClassroomId());
           User userUpdated = userRepository.save(originalUser);
        //User updatedUser = updateUserDetails(originalUser, userDetails);
        return mapToUserDto(userUpdated);
    }
    private String extractPublicIdFromUrl(String imageUrl) {
        String[] urlParts = imageUrl.split("/");
        String fileName = urlParts[urlParts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        return mapToUserDto(user);
    }
    public List<UserDTO> getUsersByClassroomId(Long classroomId){
        List<User> users = userRepository.findByClassroomId(classroomId);
        return users.stream().map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        // Delete image from Cloudinary
        if (user.getProfileImage() != null) {
            String publicId = extractPublicIdFromUrl(user.getProfileImage());
            try {
                fileUploadUtil.deleteFile(publicId);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image from Cloudinary: " + e.getMessage());
            }
        }
        userRepository.deleteById(id);
    }

    public void changePassword(Map<String, String> parametres) {
        User user = this.loadUserByUsername(parametres.get("email"));
        if (user != null) {
            this.validationService.enregistrer(user);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + parametres.get("email"));
        }
    }

    public void newPassword(Map<String, String> parametres) {
        User user = this.loadUserByUsername(parametres.get("email"));
        final Validation validation = validationService.codeValidation(parametres.get("code"));
        if(validation.getUser().getEmail().equals(user.getEmail())){
            String pwdEncoded = this.passwordEncoder.encode(parametres.get("password"));
            user.setPassword(pwdEncoded);
            this.userRepository.save(user);
        }
    }
}
