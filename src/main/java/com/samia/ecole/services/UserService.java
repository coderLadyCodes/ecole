package com.samia.ecole.services;

import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.UserAlreadyExistsException;
import com.samia.ecole.exceptions.UserNotFoundException;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO mapToUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setProfileImage(user.getProfileImage());
        return userDTO;
    }
    public User mapToUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setProfileImage(userDTO.getProfileImage());
        return user;
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

    public UserDTO createUser(UserDTO userDTO){
        User user = mapToUser(userDTO);
        if(userAlreadyExists(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " this user Exists already !");
        } else if (user.getEmail() == null){
            throw new UserNotFoundException("user can not be null");
        }
        User savedUser = userRepository.save(user);
        return mapToUserDto(savedUser);
    }
    private boolean userAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserDTO updateUser(Long id, UserDTO userDetails){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setProfileImage(userDetails.getProfileImage());
        User userUpdated = userRepository.save(user);
        return mapToUserDto(userUpdated);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
