package com.samia.ecole.DTOsAndMappers;
import com.samia.ecole.entities.Post;

public class PostDTOMapper {
    public static PostDTO mapToPostDTO(Post post){
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getPostContent(),
                post.getImagePost(),
                post.getLocalDateTime()
        );
    }
    public static Post mapToPost(PostDTO postDTO){
        return new Post(
                postDTO.getId(),
                postDTO.getTitle(),
                postDTO.getPostContent(),
                postDTO.getImagePost(),
                postDTO.getLocalDateTime()
        );
    }
}
