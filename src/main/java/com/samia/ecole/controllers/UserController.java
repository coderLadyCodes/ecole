package com.samia.ecole.controllers;
import com.samia.ecole.DTOsAndMappers.UserDTO;
import com.samia.ecole.services.UserService;
import org.springframework.web.bind.annotation.*;
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

//    @PostMapping() //consumes = "multipart/mixed"   consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
//    public UserDTO createUser(@RequestBody UserDTO userDTO, @RequestParam(value="profileImage",required = false) MultipartFile multipartFile) throws IOException, IOException {
//        if(multipartFile == null){
//                return userService.createUser(userDTO);
//            }else {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            userDTO.setProfileImage(fileName);
//            UserDTO savedUser = userService.createUser(userDTO);
//            String uploadDir = "images/" + savedUser.getId();
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//            return savedUser;
//    }}
    @PostMapping()
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
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
