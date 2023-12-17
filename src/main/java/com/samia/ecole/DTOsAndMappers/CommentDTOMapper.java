package com.samia.ecole.DTOsAndMappers;

import com.samia.ecole.entities.Comment;
import com.samia.ecole.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class CommentDTOMapper {
    public static CommentDTO mapToCommentDto(Comment comment){
        return new CommentDTO(
                comment.getId(),
                comment.getCommentContent(),
                comment.getLocalDateTime()
        );
    }
    public static Comment mapToComment(CommentDTO commentDTO){
        return new Comment(
                commentDTO.getId(),
                commentDTO.getCommentContent(),
                commentDTO.getLocalDateTime()
        );
    }
}
