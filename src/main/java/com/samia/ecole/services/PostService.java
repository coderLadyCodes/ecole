package com.samia.ecole.services;

import com.samia.ecole.DTOs.PostDTO;
import com.samia.ecole.entities.Classroom;
import com.samia.ecole.entities.Post;
import com.samia.ecole.entities.Role;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.ClassroomRepository;
import com.samia.ecole.repositories.PostRepository;
import com.samia.ecole.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final FileUploadUtil fileUploadUtil;

    public PostService(PostRepository postRepository, UserRepository userRepository, ClassroomRepository classroomRepository, FileUploadUtil fileUploadUtil) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.fileUploadUtil = fileUploadUtil;
    }
    public  PostDTO mapToPostDTO(Post post){
         PostDTO postDTO = new PostDTO();
         postDTO.setId(post.getId());
         postDTO.setTitle(post.getTitle());
         postDTO.setPostContent(post.getPostContent());
         postDTO.setImagePost(post.getImagePost());
         postDTO.setLocalDateTime(post.getLocalDateTime());
         postDTO.setUserId(post.getUser().getId());
         postDTO.setClassroomId(post.getClassroomId());
        return postDTO;
    }
    public Post mapToPost(PostDTO postDTO){
         Post post = new Post();
         post.setId(postDTO.getId());
         post.setTitle(postDTO.getTitle());
         post.setPostContent(postDTO.getPostContent());
         post.setImagePost(postDTO.getImagePost());
         post.setLocalDateTime(postDTO.getLocalDateTime());
         User user = new User();
         user.setId(postDTO.getUserId());
         post.setUser(user);
         post.setClassroomId(post.getClassroomId());
        return post;
    }
    // SUPER ADMIN CAN CHANGE THE DETAILS IN getAllPosts()
    public List<PostDTO> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToPostDTO)
                .collect(Collectors.toList());
    }
    // EACH ADMIN GETS THE LIST OF POSTS
    public List<PostDTO> getPostsByUserId(Long userId){
    List<Post> posts = postRepository.getPostsByUserId(userId);
    return posts.stream().map((this::mapToPostDTO))
            .collect(Collectors.toList());
    }
    public List<PostDTO> getPostByClassroomId(Long classroomId){
    List<Post> posts = postRepository.getPostByClassroomId(classroomId);
    return posts.stream().map((this::mapToPostDTO))
            .collect(Collectors.toList());
    }
    public PostDTO getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new CustomException("post not found with id :" + id, HttpStatus.NOT_FOUND));
        return mapToPostDTO(post);
    }
    public PostDTO createPost(PostDTO postDTO, MultipartFile multipartFile) throws IOException {
        if (postDTO == null) {
            throw new IllegalArgumentException("postDTO cannot be null");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role userRole = user.getRole();

//        Long classroomId = user.getClassroomId();
//        if (classroomId == null) {
//            throw new IllegalArgumentException("User is not associated with any classroom");
//        }
//        Optional<Classroom> optionalClassroom = classroomRepository.findById(classroomId);
//        if (optionalClassroom.isEmpty()) {
//            throw new IllegalArgumentException("Classroom not found for classroomId: " + classroomId);
//        }
        Long classroomId;
        if (Role.SUPER_ADMIN.equals(userRole)) {
            classroomId = postDTO.getClassroomId();
        } else {
            classroomId = user.getClassroomId();
            if (classroomId == null) {
                throw new IllegalArgumentException("User is not associated with any classroom");
            }
        }

        Optional<Classroom> optionalClassroom = classroomRepository.findById(classroomId);
        if (optionalClassroom.isEmpty()) {
            throw new IllegalArgumentException("Classroom not found for classroomId: " + classroomId);
        }

        Post post = mapToPost(postDTO);
        if (multipartFile != null && !multipartFile.isEmpty()){
            String imageUrl = fileUploadUtil.uploadFile(multipartFile);
            post.setImagePost(imageUrl);
        }
        post.setUser(user);
        post.setClassroomId(optionalClassroom.get().getId());
        Post savedPost = postRepository.save(post);
        return mapToPostDTO(savedPost);
    }
    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDetails, MultipartFile multipartFile) throws IOException {
        Post originalPost = postRepository.findById(id)
                .orElseThrow(() -> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
        String oldImageUrl = originalPost.getImagePost();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String newImageUrl = fileUploadUtil.uploadFile(multipartFile);
            postDetails.setImagePost(newImageUrl);

            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                String publicId = extractPublicIdFromUrl(oldImageUrl);
                fileUploadUtil.deleteFile(publicId);
            }
        } else {
            postDetails.setImagePost(oldImageUrl);
        }
        originalPost.setTitle(postDetails.getTitle() == null ? originalPost.getTitle() : postDetails.getTitle());
        originalPost.setPostContent(postDetails.getPostContent() == null ? originalPost.getPostContent() : postDetails.getPostContent());
        originalPost.setImagePost(postDetails.getImagePost());
        Post postUpdated = postRepository.save(originalPost);
        return mapToPostDTO(postUpdated);
    }
    private String extractPublicIdFromUrl(String imageUrl) {
        String[] urlParts = imageUrl.split("/");
        String fileName = urlParts[urlParts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
        if(post.getImagePost() != null){
            String publicId = extractPublicIdFromUrl(post.getImagePost());
            try {
                fileUploadUtil.deleteFile(publicId);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image from Cloudinary: " + e.getMessage());
            }
        }
        User user = post.getUser();
        if (user != null){
            user.getPostList().remove(post);
        }
        postRepository.deleteById(id);
    }

}
