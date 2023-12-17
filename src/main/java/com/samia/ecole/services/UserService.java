package com.samia.ecole.services;

import com.samia.ecole.DTOsAndMappers.UserDTO;
import com.samia.ecole.DTOsAndMappers.UserDTOMapper;
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
    private final UserDTOMapper userDTOMapper;

    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }
    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map((user) ->UserDTOMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }
    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        return UserDTOMapper.mapToUserDto(user);
    }
    public UserDTO createUser(UserDTO userDTO){
        User user = UserDTOMapper.mapToUser(userDTO);
        if(userAlreadyExists(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " this user Exists already !");
        }
        User savedUser = userRepository.save(user);
        return UserDTOMapper.mapToUserDto(savedUser);
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
       // user.setRole(userDetails.getRole());
       // user.setPostList(userDetails.getPostList());
        //user.setStudent(userDetails.getStudent());
        //user.setPassword(userDetails.getPassword());
        User userUpdated = userRepository.save(user);
        return UserDTOMapper.mapToUserDto(userUpdated);
    }
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
