package com.samia.ecole.controllers;

import com.samia.ecole.entities.User;
import com.samia.ecole.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UserController {
    public static String uploadDirectory = System.getProperty("user.dir") + "src/resources/static/images";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(path = "/hello")
    public String hello(){
        return "hello école";
    }
    @PostMapping("/saveUser")
    public User createUser(@ModelAttribute User user, @RequestParam("image")MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFileName);
        Files.write(fileNameAndPath, file.getBytes());
        user.setMedia(originalFileName);
        return userService.createUser(user);
    }
}
