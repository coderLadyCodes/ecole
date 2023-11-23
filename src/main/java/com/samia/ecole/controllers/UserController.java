package com.samia.ecole.controllers;

import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.services.FileService;
import com.samia.ecole.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserController {
    @Value("${ecole.images.userprofiles}")
    String userprofileimagepath;

    private final UserService userService;
    private final FileService fileservice;

    public UserController(UserService userService, FileService fileservice) {
        this.userService = userService;
        this.fileservice = fileservice;
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/user")
    //@PostMapping(consumes = "application/json;charset=UTF-8", value="/user")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/id/image")
    public User uploadUserImage(@RequestParam("image")MultipartFile image, @PathVariable Long id){
        User user = userService.getUserById(id);
        String filename = null;
        try {
            filename = fileservice.uploadImage(userprofileimagepath, image);
        } catch (IOException e) {
            throw new CustomException("File Not Found with the name:", HttpStatus.BAD_REQUEST);
        }
         user.setProfileImage(filename);
        return userService.updateUser(id, user);
    }

    @GetMapping(value = "/user/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable("imagename)") String imagename, HttpServletResponse response){
        try {
            InputStream inputStream = fileservice.serveImage(userprofileimagepath, imagename);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(inputStream,response.getOutputStream());
        }catch (FileNotFoundException e){
            throw new CustomException("File Not Found with the name:" + imagename, HttpStatus.BAD_REQUEST);
        }catch ( Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value="id") Long id){
       return userService.getUserById(id);
    }
    @PutMapping("/user/update/{id}")
    public User updateUser(@Valid @PathVariable(value = "id") Long id, @RequestBody User userDetails){
        return userService.updateUser(id, userDetails);
    }
    @DeleteMapping("/user/delete/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
