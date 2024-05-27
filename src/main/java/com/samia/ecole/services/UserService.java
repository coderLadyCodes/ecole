package com.samia.ecole.services;

import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.entities.Role;
import com.samia.ecole.entities.User;
import com.samia.ecole.entities.Validation;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UserAlreadyExistsException;
import com.samia.ecole.exceptions.UserNotFoundException;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
     private final UserRepository userRepository;
     private final BCryptPasswordEncoder passwordEncoder;
     private final ValidationService validationService;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ValidationService validationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
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
        return user;
    }
    public UserDTO createUser(UserDTO userDTO){
        User user = mapToUser(userDTO);
        if (user.getEmail() == null || !user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }
        final Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException(user.getEmail() + " this user Exists already !");
        }
        if (user.getEmail() == null){
            throw new UserNotFoundException("user can not be null");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
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
//        Role role = user.getRole();
//        if (role == null){
//            role  = Role.PARENT;
//        }else if (role != Role.PARENT && role != Role.ADMIN && role != Role.SUPER_ADMIN) {
//            throw new IllegalArgumentException("roles doit etre spécifié");
//        }
//        user.setRole(role);
//        user.setActif(true);
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
        userRepository.save(userActivated);
    }
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new UserNotFoundException("Aucun utilisateur trouvé dans la bd"));
    }
    public UserDTO updateUser(Long id, UserDTO userDetails){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("user not found ! "));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setProfileImage(userDetails.getProfileImage());
        user.setRole(userDetails.getRole());
        User userUpdated = userRepository.save(user);
        return mapToUserDto(userUpdated);
    }
    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            //System.out.println("User Id: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail()+ ", Photo : " + user.getProfileImage());
        });
        return users.stream().map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        return mapToUserDto(user);
    }
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }

    public void changePassword(Map<String, String> parametres) {
        User user = this.loadUserByUsername(parametres.get("email"));
        this.validationService.enregistrer(user);
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
