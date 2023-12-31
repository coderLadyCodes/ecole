package com.samia.ecole.controllers;
import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*", methods= {RequestMethod.POST, RequestMethod.GET,RequestMethod.PUT})
@RestController
@RequestMapping("/users")
public class UserController {
//    @Value("${ecole.images.userprofiles}")
//    String userprofileimagepath;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }) //produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
    public UserDTO createUser(@RequestPart UserDTO userDTO, @RequestPart MultipartFile multipartFile) throws IOException {

        if(multipartFile == null){
            return userService.createUser(userDTO);
        }else {

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            userDTO.setProfileImage(fileName);
            UserDTO savedUser = userService.createUser(userDTO);
            String uploadDir = "images/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return savedUser;
        }
    }

//    @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) //consumes = "multipart/mixed"
//    public UserDTO createUser(@RequestBody UserDTO userDTO, @RequestPart(value="profileImage",required = false) MultipartFile multipartFile) throws IOException, IOException {
//        System.out.println("START");
//        if(multipartFile == null){
//                return userService.createUser(userDTO);
//            }else {
//            System.out.println("FILE EXISTS");
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            userDTO.setProfileImage(fileName);
//            UserDTO savedUser = userService.createUser(userDTO);
//            String uploadDir = "images/" + savedUser.getId();
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//            System.out.println("THE OUTPUT IS LIKELY TO BE : " + uploadDir + fileName + multipartFile);
//            return savedUser;
//    }}
//    @PostMapping()
//    public UserDTO createUser(@RequestBody UserDTO userDTO){
//        return userService.createUser(userDTO);
//    }
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
