package com.samia.ecole.controllers;
import com.samia.ecole.DTOsAndMappers.UserDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*", methods= {RequestMethod.POST, RequestMethod.GET,RequestMethod.PUT})
@RestController
@RequestMapping("/users")
public class UserController {
//    @Value("${ecole.images.userprofiles}")
//    String userprofileimagepath;

    private final UserService userService;
   // private final FileService fileservice;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping //consumes = "multipart/mixed"   consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    public UserDTO createUser(@RequestBody UserDTO userDTO, @RequestParam(value="profileImage",required = false) MultipartFile multipartFile) throws IOException {
        if(multipartFile == null){
                return userService.createUser(userDTO);
            }else {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            userDTO.setProfileImage(fileName);
            UserDTO savedUser = userService.createUser(userDTO);
            String uploadDir = "images/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return savedUser;
    }}


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

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable(value="id") Long id){
        UserDTO userDTO = userService.getUserById(id);
        return userDTO;
    }
    @PutMapping("{id}")
    public UserDTO updateUser(@Valid @PathVariable(value = "id") Long id, @RequestBody UserDTO userDetails){
        return userService.updateUser(id, userDetails);
    }
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
