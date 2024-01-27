package com.samia.ecole.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.UserService; 
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*", methods= {RequestMethod.POST, RequestMethod.GET,RequestMethod.PUT})
@RestController
@MultipartConfig
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) //headers="Content-Type=multipart/form-data"    consumes = "multipart/form-data", produces = "application/json"
    public UserDTO createUser(@RequestPart String userDTO, @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userdto = mapper.readValue(userDTO, UserDTO.class);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            userdto.setProfileImage(fileName);
            UserDTO savedUser = userService.createUser(userdto);
            String uploadDir = "images/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return savedUser;
        } else {
            return userService.createUser(userdto);
        }
    }
    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable(value="id") Long id){
           return  userService.getUserById(id);
    }
    @PutMapping("{id}")
    public UserDTO updateUser(@PathVariable(value = "id") Long id, @RequestBody UserDTO userDetails){
        return userService.updateUser(id, userDetails);
    }
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
