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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

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
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }
    @GetMapping(value = "/images/user/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
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
    @PutMapping("user/update/{id}")
    public User updateUser(@Valid @PathVariable(value = "id") Long id, @RequestBody User userDetails){
        return userService.updateUser(id, userDetails);
    }
    @DeleteMapping("user/delete/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
