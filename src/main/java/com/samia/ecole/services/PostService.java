package com.samia.ecole.services;

import com.samia.ecole.DTOs.PostDTO;
import com.samia.ecole.entities.Post;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UserNotFoundException;
import com.samia.ecole.repositories.PostRepository;
import com.samia.ecole.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    public  PostDTO mapToPostDTO(Post post){
         PostDTO postDTO = new PostDTO();
         postDTO.setId(post.getId());
         postDTO.setTitle(post.getTitle());
         postDTO.setPostContent(post.getPostContent());
         postDTO.setImagePost(post.getImagePost());
         postDTO.setLocalDateTime(post.getLocalDateTime());
         postDTO.setUserId(post.getUser().getId());
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
    public PostDTO getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new CustomException("post not found with id :" + id, HttpStatus.NOT_FOUND));
        return mapToPostDTO(post);
    }
    public PostDTO createPost(PostDTO postDTO){
        if (postDTO == null) {
            throw new IllegalArgumentException("postDTO cannot be null");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //userId = user.getId();
        //Long userId = postDTO.getUserId();
//        if (userId == null) {
//            throw new IllegalArgumentException("userId cannot be null for creating a Post");
//        }
        //(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found for userId: + userId" );
        }
        //User user =optionalUser.get();
        //user.setRole(Role.ADMIN);
        //user.setRole(Role.SUPER_ADMIN);
        Post post = mapToPost(postDTO);
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return mapToPostDTO(savedPost);
    }
    public PostDTO updatePost(Long id, PostDTO postDetails){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
        post.setTitle(postDetails.getTitle() == null ? post.getTitle() : postDetails.getTitle());
        post.setPostContent(postDetails.getPostContent() == null ? post.getPostContent() : postDetails.getPostContent());
        post.setImagePost(postDetails.getImagePost());
        Post postUpdated = postRepository.save(post);
        return mapToPostDTO(postUpdated);
    }
    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
        User user = post.getUser();
        if (user != null){
            user.getPostList().remove(post);
        }
        postRepository.deleteById(id);
    }

}
