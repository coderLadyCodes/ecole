package com.samia.ecole.controllers;

import com.samia.ecole.entities.User;
import com.samia.ecole.services.UserService;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class UserController {
    public static String uploadDirectory = System.getProperty("user.dir") + "src/resources/static/images";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/allusers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }
//    @PostMapping("/user")
//    public User createUser(@ModelAttribute User user, @RequestParam("image")MultipartFile file) throws IOException {
//        String originalFileName = file.getOriginalFilename();
//        Path fileNameAndPath = Paths.get(uploadDirectory, originalFileName);
//        Files.write(fileNameAndPath, file.getBytes());
//        user.setMedia(originalFileName);
//        return userService.createUser(user);
//    }

//    @GetMapping("/user/media/{id}")
//    public Resource getMedia(@PathVariable Long id) throws IOException {
//        User user = userService.getUserById(id);
//        Path mediaPath = Paths.get(uploadDirectory, user.getMedia());
//        Resource resource = new FileSystemResource(mediaPath.toFile());
//        String contentType = Files.probeContentType(mediaPath);
//        //return contentType(MediaType.parseMediaType(contentType)).body(resource);
//        return resource;
//    }
@GetMapping("/user/{id}")
public User getUserById(@PathVariable(value="id") Long id){
    return userService.getUserById(id);
}
    @PutMapping("/update/{id}")
    public User updateUser(@Valid @PathVariable(value = "id") Long id, @RequestBody User userDetails){
        return userService.updateUser(id, userDetails);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
