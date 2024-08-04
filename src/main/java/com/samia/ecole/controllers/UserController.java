package com.samia.ecole.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samia.ecole.DTOs.AuthentificationDTO;
import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.entities.User;
import com.samia.ecole.security.JwtService;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping("/identification")
    public String identification(){
        return "identifier";
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
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO, HttpServletResponse httpServletResponse){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(authentication.isAuthenticated()) {
            Map<String, String> tokens = this.jwtService.generate(authentificationDTO.username());
            User user = (User) authentication.getPrincipal();
            tokens.put("role", user.getRole().name());
            tokens.put("id", String.valueOf(user.getId()));
            tokens.put("userName", user.getName());

            String jwtToken = tokens.get("bearer");
            Cookie cookie = new Cookie("token", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge( 30 * 60 * 1000);
            httpServletResponse.addCookie(cookie);
            //tokens.remove("bearer");
             //Set refresh token as HttpOnly cookie
            String refreshToken = tokens.get("refresh");
            Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge( 30 * 60 * 1000);
            httpServletResponse.addCookie(refreshTokenCookie);

            return tokens;
        }
        return  null;
    }

    @PostMapping("/refresh-token")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest, HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    refresh = cookie.getValue();
                    break;
                }
            }
        }

        if (refresh != null) {
            try {
                refreshTokenRequest.put("refresh", refresh);
                Map<String, String> tokens = jwtService.refreshToken(refreshTokenRequest);
                System.out.println("refresh token request is : " + refreshTokenRequest);
                String newAccessToken = tokens.get("bearer");

                // Set new access token as HttpOnly cookie
                Cookie newAccessTokenCookie = new Cookie("token", newAccessToken);
                newAccessTokenCookie.setHttpOnly(true);
                newAccessTokenCookie.setSecure(false);
                newAccessTokenCookie.setPath("/");
                newAccessTokenCookie.setMaxAge(30 * 60 * 1000);

                response.addCookie(newAccessTokenCookie);
                System.out.println("access token cookie : " + newAccessTokenCookie);

                return tokens;
            } catch (RuntimeException e) {
                throw new RuntimeException("Not authorized");
            }
        }
        throw new RuntimeException("Refresh token not found");
    }
//    @PostMapping("/refresh-token")
//    public Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest){
//        return this.jwtService.refreshToken(refreshTokenRequest);
//    }

    @PostMapping("/change-password")
    public void  passwordChange(@RequestBody Map<String, String> activation){
        this.userService.changePassword(activation);
    }
    @PostMapping("/new-password")
    public void  newPassword(@RequestBody Map<String, String> activation){
        this.userService.newPassword(activation);
    }
//    @PostMapping("/deconnexion")
//    public void logout(){
//        this.jwtService.deconnexion();
//        SecurityContextHolder.clearContext();
//    }
    @PostMapping("/deconnexion")
    public void logout(HttpServletResponse response){
        Cookie tokenCookie = new Cookie("token", null);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);

        Cookie refreshTokenCookie = new Cookie("refresh", null);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
        this.jwtService.deconnexion();
        SecurityContextHolder.clearContext();
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
    //@PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/classroom/{classroomId}/users")
    public List<UserDTO> getUsersByClassroomId(@PathVariable(value = "classroomId") Long classroomId){
        return userService.getUsersByClassroomId(classroomId);
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
