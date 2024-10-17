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
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@MultipartConfig
public class UserController {
    private final FileUploadUtil fileUploadUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(FileUploadUtil fileUploadUtil, AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.fileUploadUtil = fileUploadUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/identification")
    public String identification() {
        return "identifier";
    }

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createUser(@RequestPart String userDTO, @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userdto = mapper.readValue(userDTO, UserDTO.class);
        //if (multipartFile != null && !multipartFile.isEmpty()) {
            //String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            //int extensionIndex = fileName.lastIndexOf('.');
            //String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

            //String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

            //String profileImageName = shortenedFileName + fileName.substring(extensionIndex);

            //userdto.setProfileImage(profileImageName);

            //String imageUrl = fileUploadUtil.uploadFile(multipartFile);

            //userdto.setProfileImage(imageUrl);

            //UserDTO savedUser = userService.createUser(userdto);

            //String uploadDir = "images/" + savedUser.getId();

            //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            //return userService.createUser(userdto);
        //} else {
            return userService.createUser(userdto, multipartFile);
        //}
    }

    @PostMapping("/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        userService.activation(activation);
    }

    @PostMapping("/connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO, HttpServletResponse httpServletResponse) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            Map<String, String> tokens = this.jwtService.generate(authentificationDTO.username());
            User user = (User) authentication.getPrincipal();
            tokens.put("role", user.getRole().name());
            tokens.put("id", String.valueOf(user.getId()));
            tokens.put("userName", user.getName());
            tokens.put("classroomId", String.valueOf(user.getClassroomId())); // I ADDED THIS SO THAT THE WEBSOCKET WORKS

            String jwtToken = tokens.get("bearer");
            Cookie cookie = new Cookie("token", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(10 * 60);
            httpServletResponse.addCookie(cookie);
            //tokens.remove("bearer");
            //Set refresh token as HttpOnly cookie
            String refreshToken = tokens.get("refresh");
            Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(30 * 60);
            httpServletResponse.addCookie(refreshTokenCookie);

            return tokens;
        }
        return null;
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
                String newAccessToken = tokens.get("bearer");

                // Set new access token as HttpOnly cookie
                Cookie newAccessTokenCookie = new Cookie("token", newAccessToken);
                newAccessTokenCookie.setHttpOnly(true);
                newAccessTokenCookie.setSecure(true);
                newAccessTokenCookie.setPath("/");
                newAccessTokenCookie.setMaxAge(7 * 24 * 60 * 60 * 1000);
                response.addCookie(newAccessTokenCookie);

                return tokens;
            } catch (RuntimeException e) {
                throw new RuntimeException("User Not authorized !! ");
            }
        }
        throw new RuntimeException("Refresh token not found");
    }


    @GetMapping("/api/get-token") // THIS IS FOR WEBSOCKET NOT SURE IF IT WORTH IT
    public Map<String, String> getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            System.out.println("No cookies received.");
            throw new RuntimeException("No cookies found in the request.");
        }

        for (Cookie cookie : cookies) {
            //System.out.println("Received Cookie: " + cookie.getName() + " = " + cookie.getValue());
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }

        if (token != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            //System.out.println("Token found: " + token);
            return response;
        } else {
            System.out.println("Token not found in cookies.");
            throw new RuntimeException("Token cookie not found.");
        }
    }

    @PostMapping("/change-password")
    public void passwordChange(@RequestBody Map<String, String> activation) {
        this.userService.changePassword(activation);
    }

    @PostMapping("/new-password")
    public void newPassword(@RequestBody Map<String, String> activation) {
        this.userService.newPassword(activation);
    }

    @PostMapping("/deconnexion")
    public void logout(HttpServletResponse response) {
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
    public UserDTO getUserById(@PathVariable(value = "id") Long id) {
        return userService.getUserById(id);
    }
    @Transactional
    @PutMapping(value = "/users/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO updateUser(@PathVariable(value = "id") Long id,
                              @RequestPart String userDetails,
                              @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDetail = mapper.readValue(userDetails, UserDTO.class);
        //UserDTO originalUser = userService.getUserById(id);
        //String oldImageUrl = originalUser.getProfileImage();
        //if (multipartFile != null && !multipartFile.isEmpty()) {
            //try {
                //String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                //int extensionIndex = fileName.lastIndexOf('.');
                //String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

                //String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

                //String profileImageName = shortenedFileName + fileName.substring(extensionIndex);
                //String newImageUrl = fileUploadUtil.uploadFile(multipartFile);

                //userDetail.setProfileImage(newImageUrl);

                //UserDTO updatedUser = userService.updateUser(id, userDetail);

                //if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    //String publicId = extractPublicIdFromUrl(oldImageUrl);
                    //fileUploadUtil.deleteFile(publicId);
                //}

                //return updatedUser;

            //} catch (Exception e) {
                //throw new IOException("Error updating user and image: " + e.getMessage());
            //}

            //String uploadDir = "images/" + updatedUser.getId();

            //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            //if (originalUser.getProfileImage() != null && !originalUser.getProfileImage().isEmpty()) {
            //String previousPath = uploadDir + "/" + originalUser.getProfileImage();
            //String publicId = extractPublicIdFromUrl(originalUser.getProfileImage());
            //fileUploadUtil.deleteFile(publicId);
            //FileUploadUtil.deleteFile(publicId);
        //}
        return userService.updateUser(id, userDetail, multipartFile);

    }
//    private String extractPublicIdFromUrl(String imageUrl) {
//        String[] urlParts = imageUrl.split("/");
//        String fileName = urlParts[urlParts.length - 1];
//        return fileName.substring(0, fileName.lastIndexOf('.'));
//    }


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
