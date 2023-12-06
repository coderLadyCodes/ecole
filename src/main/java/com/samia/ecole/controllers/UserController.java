package com.samia.ecole.controllers;
import com.samia.ecole.entities.User;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@CrossOrigin("http://localhost:3000")
//@CrossOrigin(origins = "*", methods= {RequestMethod.POST, RequestMethod.GET,RequestMethod.PUT})
@RestController
public class UserController {
//    @Value("${ecole.images.userprofiles}")
//    String userprofileimagepath;

    private final UserService userService;
   // private final FileService fileservice;

    public UserController(UserService userService) {
        this.userService = userService;

    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value="/user", consumes={MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public User createUser(@RequestBody User user, @RequestParam(value="image",required = false) MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        user.setProfileImage(fileName);
        User savedUser = userService.createUser(user);
        String uploadDir = "images/" + savedUser.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return savedUser;
    }
//    @PostMapping("/user")
//    //@PostMapping(consumes = "application/json;charset=UTF-8", value="/user")
//    public User createUser(@Valid @RequestBody User user) {
//        return userService.createUser(user);
//    }
//
//    @PostMapping("/id/image")
//    public User uploadUserImage(@RequestParam("image")MultipartFile image, @PathVariable Long id){
//        User user = userService.getUserById(id);
//        String filename = null;
//        try {
//            filename = fileservice.uploadImage(userprofileimagepath, image);
//        } catch (IOException e) {
//            throw new CustomException("File Not Found with the name:", HttpStatus.BAD_REQUEST);
//        }
//         user.setProfileImage(filename);
//        return userService.updateUser(id, user);
//    }
//
//    @GetMapping(value = "/user/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void serveImage(@PathVariable("imagename)") String imagename, HttpServletResponse response){
//        try {
//            InputStream inputStream = fileservice.serveImage(userprofileimagepath, imagename);
//            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//            StreamUtils.copy(inputStream,response.getOutputStream());
//        }catch (FileNotFoundException e){
//            throw new CustomException("File Not Found with the name:" + imagename, HttpStatus.BAD_REQUEST);
//        }catch ( Exception e){
//            e.printStackTrace();
//        }
//    }

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
