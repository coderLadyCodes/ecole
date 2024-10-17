package com.samia.ecole.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samia.ecole.DTOs.PostDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@MultipartConfig
@RequestMapping("/posts")
public class PostController{
    private final PostService postService;
    private final FileUploadUtil fileUploadUtil;

    public PostController(PostService postService, FileUploadUtil fileUploadUtil) {
        this.postService = postService;
        this.fileUploadUtil = fileUploadUtil;
    }
    @GetMapping()
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/user/{userId}")
    public List<PostDTO> getPostsByUserId(@PathVariable(value = "userId") Long userId){
        return postService.getPostsByUserId(userId);
    }

    @GetMapping("/classroom/{classroomId}")
    public List<PostDTO> getPostByClassroomId(@PathVariable(value = "classroomId") Long classroomId){
        return postService.getPostByClassroomId(classroomId);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDTO createPost(@RequestPart String postDTO,@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        PostDTO postdto = mapper.readValue(postDTO, PostDTO.class);
        //if (multipartFile != null && !multipartFile.isEmpty()) {
            //String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            //int extensionIndex = fileName.lastIndexOf('.');
            //String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

            //String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

            //String profileImageName = shortenedFileName + fileName.substring(extensionIndex);
            //String imageUrl = fileUploadUtil.uploadFile(multipartFile);
            //postdto.setImagePost(imageUrl);

            //PostDTO savedPost = postService.createPost(postdto);

            //String uploadDir = "images/" + savedPost.getId();

            //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            //return postService.createPost(postdto);
        //} else {
            return postService.createPost(postdto, multipartFile);
        //}
    }

        @GetMapping("/post/{id}")
        public PostDTO getPostById(@PathVariable(value="id") Long id){
            return postService.getPostById(id);
        }

        @PutMapping("{id}")
        public PostDTO updatePost(@PathVariable(value = "id") Long id,
                                  @RequestPart String postDetails,
                                  @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PostDTO postdetail = mapper.readValue(postDetails, PostDTO.class);
            //PostDTO originalPost = postService.getPostById(id);
            //String oldImageUrl = originalPost.getImagePost();
            //if (multipartFile != null && !multipartFile.isEmpty()) {
                //try {
                    //String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                    //int extensionIndex = fileName.lastIndexOf('.');
                    //String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

                    //String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

                    //String profileImageName = shortenedFileName + fileName.substring(extensionIndex);
                    //String newImageUrl = fileUploadUtil.uploadFile(multipartFile);
                    //postdetail.setImagePost(newImageUrl);

                    //PostDTO updatedPost = postService.updatePost(id, postdetail);

                    //if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                        //String publicId = extractPublicIdFromUrl(oldImageUrl);
                        //fileUploadUtil.deleteFile(publicId);
                    //}
                    //return updatedPost;

                    //String uploadDir = "images/" + updatedPost.getId();

                    //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
                    //if (originalPost.getImagePost() != null && !originalPost.getImagePost().isEmpty()) {
                        //String previousPath = uploadDir + "/" + originalPost.getImagePost();
                       // FileUploadUtil.deleteFile(previousPath);
                    //}
                    //return updatedPost;
                //} catch (Exception e) {
                    //throw new IOException("Error updating user and image: " + e.getMessage());
                //}

                //} else {
            //}
                return postService.updatePost(id,postdetail, multipartFile);
            }
//    private String extractPublicIdFromUrl(String imageUrl) {
//        String[] urlParts = imageUrl.split("/");
//        String fileName = urlParts[urlParts.length - 1];
//        return fileName.substring(0, fileName.lastIndexOf('.'));
//    }

        @DeleteMapping("{id}")
         public void deleteUser(@PathVariable(value = "id") Long id){
             postService.deletePost(id);
        }
    }
