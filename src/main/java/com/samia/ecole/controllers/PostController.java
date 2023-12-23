//package com.samia.ecole.controllers;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.samia.ecole.DTOsAndMappers.PostDTO;
//import com.samia.ecole.DTOsAndMappers.UserDTO;
//import com.samia.ecole.entities.Post;
//import com.samia.ecole.services.FileUploadUtil;
//import com.samia.ecole.services.PostService;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/posts")
//public class PostController{
//    //@Value("${ecole.images.posts}")
//    //String postimagepath;
//    private final PostService postService;
//
//    public PostController(PostService postService) {
//        this.postService = postService;
//
//
//    }
//    @GetMapping()
//    public List<PostDTO> getAllPosts(){
//        return postService.getAllPosts();
//    }
//    @PostMapping()
//    public PostDTO createPost(@RequestBody PostDTO postDTO, @RequestParam(name= "image", required = false) MultipartFile multipartFile ) throws IOException {
//        if(multipartFile == null){
//            return postService.createPost(postDTO);
//        }else {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            postDTO.setImagePost(fileName);
//            PostDTO savedPost = postService.createPost(postDTO);
//            String uploadDir = "images/" + savedPost.getId();
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//            return savedPost;
//        }
//    }
//                                               //ADD IMAGE TO POST
////       @PostMapping("post/{id}/image")
////       @ResponseBody
////        public void addImageToPost(@PathVariable(value ="id") Long id, @RequestParam("image") MultipartFile image){
////            String uploadedImageFilename = "";
////             try {
////                   uploadedImageFilename = fileService.uploadImage(postimagepath, image);
////             } catch (IOException e) {
////                   throw new CustomException("Error Occurred in Uploading Image", HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////
////        }
//        @GetMapping("{id}")
//        public PostDTO getPostById(@PathVariable(value="id") Long id){
//            return postService.getPostById(id);
//        }
//                                           // UPDATE POST
//        @PutMapping("{id}")
//        public PostDTO updatePost(@PathVariable(value = "id") Long id, @RequestBody PostDTO postDetails){
//        return postService.updatePost(id, postDetails);
//        }
//                                          // DELETE POST
//        @DeleteMapping("{id}")
//         public void deleteUser(@PathVariable(value = "id") Long id){
//             postService.deletePost(id);
//        }
//    }
