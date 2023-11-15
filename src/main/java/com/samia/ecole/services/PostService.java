package com.samia.ecole.services;

import com.samia.ecole.entities.Post;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UserNotFoundException;
import com.samia.ecole.repositories.PostRepository;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    public PostService(PostRepository postRepository, UserRepository userRepository, FileService fileService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }
    public Post createPost(Post post, MultipartFile file, String folderpath){
       // User foundUser = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        post.setLocalDateTime(LocalDateTime.now());
       // post.setUser(foundUser);
        String filenamewithtimestamp="";
        if(file != null){
            try{
                filenamewithtimestamp = fileService.uploadImage(folderpath, file);
                post.setImagePost(filenamewithtimestamp);
            }catch (IOException e) {
                throw new CustomException("Something went wrong when uploading image",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return postRepository.save(post);
    }
    public Post getPostById(Long id){
        return postRepository.findById(id).orElseThrow(()-> new CustomException("post not found with id :" + id, HttpStatus.NOT_FOUND));
    }
    public Post updatePost(Post postDetails, Long id){
        User founduser = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        Post foundPost = postRepository.findById(id)
                .orElseThrow(() -> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
        foundPost.setTitle(postDetails.getTitle() == null ? foundPost.getTitle() : postDetails.getTitle());
        foundPost.setPostContent(postDetails.getPostContent() == null ? foundPost.getPostContent() : postDetails.getPostContent());
        foundPost.setImagePost(foundPost.getPostContent());
        return postRepository.save(foundPost);
    }
    public void deletePost(Long id){
        postRepository.findById(id).orElseThrow(()-> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
        postRepository.deleteById(id);
    }
    public List<Post> getAllPosts(){
       return postRepository.findAll();
    }
}
