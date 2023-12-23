//package com.samia.ecole.services;
//import com.samia.ecole.DTOsAndMappers.PostDTO;
//import com.samia.ecole.DTOsAndMappers.PostDTOMapper;
//import com.samia.ecole.entities.Post;
//import com.samia.ecole.exceptions.CustomException;
//import com.samia.ecole.repositories.PostRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class PostService {
//    private final PostRepository postRepository;
//
//    public PostService(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//    public List<PostDTO> getAllPosts(){
//        List<Post> posts = postRepository.findAll();
//        return posts.stream().map((post) -> PostDTOMapper.mapToPostDTO(post))
//                .collect(Collectors.toList());
//    }
//    public PostDTO getPostById(Long id){
//        Post post = postRepository.findById(id).orElseThrow(()-> new CustomException("post not found with id :" + id, HttpStatus.NOT_FOUND));
//        return PostDTOMapper.mapToPostDTO(post);
//    }
//    public PostDTO createPost(PostDTO postDTO){
//        Post post = PostDTOMapper.mapToPost(postDTO);
//        Post savedPost = postRepository.save(post);
//        return PostDTOMapper.mapToPostDTO(savedPost);
//    }
//    public PostDTO updatePost(Long id, PostDTO postDetails){
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
//        post.setTitle(postDetails.getTitle() == null ? post.getTitle() : postDetails.getTitle());
//        post.setPostContent(postDetails.getPostContent() == null ? post.getPostContent() : postDetails.getPostContent());
//        post.setImagePost(postDetails.getImagePost());
//        Post postUpdated = postRepository.save(post);
//        return PostDTOMapper.mapToPostDTO(postUpdated);
//    }
//    public void deletePost(Long id){
//        postRepository.findById(id).orElseThrow(()-> new CustomException("Post not found with id :" + id, HttpStatus.NOT_FOUND));
//        postRepository.deleteById(id);
//    }
//
//}
