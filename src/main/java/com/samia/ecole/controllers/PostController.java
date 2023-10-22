package com.samia.ecole.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samia.ecole.entities.Post;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.services.FileService;
import com.samia.ecole.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PostController {
    @Value("${ecole.images.posts}")
    String postimagepath;
    private final PostService postService;
    private final FileService fileService;
    private final ObjectMapper objectMapper;

    public PostController(PostService postService, FileService fileService, ObjectMapper objectMapper) {
        this.postService = postService;
        this.fileService = fileService;
        this.objectMapper = objectMapper;
    }
                                           // GET ALL POSTS
    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }
                                          //CREATE POST
    @PostMapping("/user/{id}/post")
    public Post createPost(@RequestParam("post") String post, @RequestParam(name= "image", required = false)MultipartFile file, @PathVariable(value ="id") Long id) {
        Post createdpost = null;
        try{
            Post postdata = objectMapper.readValue(post, Post.class);
            createdpost = postService.createPost(postdata,id,file,postimagepath);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return createdpost;
    }
//    @PostMapping("/user/{id}/post/{id}/image")
//    public ResponseEntity<ApiResponse> addImageToPost(@RequestParam("image") MultipartFile image, @PathVariable(value ="id") Long id){
//        String uploadedImageFilename = "";
//        try {
//            uploadedImageFilename = fileService.uploadImage(postimagepath, image);
//        } catch (IOException e) {
//            throw new CustomException("Error Occurred in Uploading Image", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        ApiResponse apiResponse = new ApiResponse("Image Successfully Uploaded with filename :" + uploadedImageFilename,
//                LocalDateTime.now(), HttpStatus.OK, HttpStatus.OK.value());
//        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//    }
                                              // SERVE IMAGE
       @PostMapping("/user/post/{id}/image")
        public void addImageToPost(@RequestParam("image") MultipartFile image, @PathVariable(value ="id") Long id){
            String uploadedImageFilename = "";
             try {
                   uploadedImageFilename = fileService.uploadImage(postimagepath, image);
             } catch (IOException e) {
                   throw new CustomException("Error Occurred in Uploading Image", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        }
                                            // SERVE IMAGE
    @GetMapping(value = "/images/postimage/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable("imagename") String imagename, HttpServletResponse response) {

        try {
            InputStream is = fileService.serveImage(postimagepath, imagename);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(is, response.getOutputStream());

        } catch (FileNotFoundException e) {
            throw new CustomException("File Not Found with the name:" + imagename, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
                                            // GET POST BY POST ID
        @GetMapping("user/post/{id}")
        public Post getPostById(@PathVariable(value="id") Long id){
            return postService.getPostById(id);
        }
                                           // UPDATE POST
        @PutMapping("/user/post/{id}")
        public Post updatePost(@RequestBody Post post, @PathVariable(value = "id") Long id){
        return postService.updatePost(post,id);
        }
                                          // DELETE POST
        @DeleteMapping("user/post/{id}")
         public void deleteUser(@PathVariable(value = "id") Long id){
             postService.deletePost(id);
        }
    }