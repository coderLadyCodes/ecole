package com.samia.ecole.services;

import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.UserAlreadyExistsException;
import com.samia.ecole.exceptions.UserNotFoundException;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found"));
    }
    public User createUser(User user){
        if(userAlreadyExists(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " this user Exists already !");
        }
        return userRepository.save(user);
    }

    private boolean userAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User updateUser(Long id, User userDetails){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setProfileImage(userDetails.getProfileImage());
        user.setRole(userDetails.getRole());
        user.setPostList(userDetails.getPostList());
        user.setStudent(userDetails.getStudent());
        //user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
