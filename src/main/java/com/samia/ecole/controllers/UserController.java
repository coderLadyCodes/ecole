package com.samia.ecole.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samia.ecole.DTOs.AuthentificationDTO;
import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.security.JwtService;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@MultipartConfig
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }
    @GetMapping("/dashboard")
    public String dashboar(){
        return "dashboard";
    }
    @PostMapping(value ="/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping("/activation")
    public void activation(@RequestBody Map<String, String> activation){
        userService.activation(activation);
    }
    @PostMapping("/connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );
        if(authenticate.isAuthenticated()){
            return this.jwtService.generate(authentificationDTO.username());
        }
        return  null;
    }
    @PostMapping("/refresh-token")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest){
        return this.jwtService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/change-password")
    public void  passwordChange(@RequestBody Map<String, String> activation){
        this.userService.changePassword(activation);
    }
    @PostMapping("/new-password")
    public void  newPassword(@RequestBody Map<String, String> activation){
        this.userService.newPassword(activation);
    }
    @PostMapping("/logout")
    public void logout(){
        this.jwtService.deconnexion();
    }
    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable(value="id") Long id){
           return  userService.getUserById(id);
    }
    @PutMapping(value = "/users/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
