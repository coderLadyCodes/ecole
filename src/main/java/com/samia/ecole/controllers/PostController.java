package com.samia.ecole.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samia.ecole.DTOs.PostDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.PostService;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
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

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping()
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDTO createPost(@RequestPart String postDTO,@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        PostDTO postdto = mapper.readValue(postDTO, PostDTO.class);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            int extensionIndex = fileName.lastIndexOf('.');
            String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

            String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

            String profileImageName = shortenedFileName + fileName.substring(extensionIndex);

            postdto.setImagePost(profileImageName);

            PostDTO savedPost = postService.createPost(postdto);

            String uploadDir = "images/" + savedPost.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return savedPost;
        } else {
            return postService.createPost(postdto);
        }
    }

        @GetMapping("{id}")
        public PostDTO getPostById(@PathVariable(value="id") Long id){
            return postService.getPostById(id);
        }
                                           // UPDATE POST
        @PutMapping("{id}")
        public PostDTO updatePost(@PathVariable(value = "id") Long id,
                                  @RequestPart String postDetails,
                                  @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PostDTO postdetail = mapper.readValue(postDetails, PostDTO.class);
            PostDTO originalPost = postService.getPostById(id);
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                int extensionIndex = fileName.lastIndexOf('.');
                String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

                String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

                String profileImageName = shortenedFileName + fileName.substring(extensionIndex);

                postdetail.setImagePost(profileImageName);

                PostDTO updatedPost = postService.updatePost(id, postdetail);

                String uploadDir = "images/" + updatedPost.getId();

                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
                if (originalPost.getImagePost() != null && !originalPost.getImagePost().isEmpty()) {
                    String previousPath = uploadDir + "/" + originalPost.getImagePost();
                    FileUploadUtil.deleteFile(previousPath);
                }
                return updatedPost;

            } else {
                return postService.updatePost(id,postdetail);
            }
        }
        @DeleteMapping("{id}")
         public void deleteUser(@PathVariable(value = "id") Long id){
             postService.deletePost(id);
        }
    }
