package com.samia.ecole.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createUser(@RequestPart String userDTO, @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userdto = mapper.readValue(userDTO, UserDTO.class);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            int extensionIndex = fileName.lastIndexOf('.');
            String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

            String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

            String profileImageName = shortenedFileName + fileName.substring(extensionIndex);

            userdto.setProfileImage(profileImageName);

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
@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public UserDTO updateUser(@PathVariable(value = "id") Long id,
                          @RequestPart String userDetails,
                          @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDetail = mapper.readValue(userDetails, UserDTO.class);
        UserDTO originalUser =  userService.getUserById(id);
    if (multipartFile != null && !multipartFile.isEmpty()) {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        int extensionIndex = fileName.lastIndexOf('.');
        String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

        String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

        String profileImageName = shortenedFileName + fileName.substring(extensionIndex);

        userDetail.setProfileImage(profileImageName);

        UserDTO updatedUser = userService.updateUser(id, userDetail);

        String uploadDir = "images/" + updatedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        if (originalUser.getProfileImage() != null && !originalUser.getProfileImage().isEmpty()) {
            String previousPath = uploadDir + "/" + originalUser.getProfileImage();
            FileUploadUtil.deleteFile(previousPath);
        }
        return updatedUser;

    } else {
        return userService.updateUser(id,userDetail);
    }
}
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
