package com.samia.ecole.services;

import com.samia.ecole.DTOsAndMappers.CommentDTO;
import com.samia.ecole.DTOsAndMappers.CommentDTOMapper;
import com.samia.ecole.entities.Comment;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.CommentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final  CommentDTOMapper commentDTOMapper;

    public CommentService(CommentRepository commentRepository, CommentDTOMapper commentDTOMapper) {
        this.commentRepository = commentRepository;
        this.commentDTOMapper = commentDTOMapper;
    }

    public List<CommentDTO> getAllComments(){
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map((comment) ->CommentDTOMapper.mapToCommentDto(comment))
                .collect(Collectors.toList());
    }

    public CommentDTO createComment(CommentDTO commentDTO){
        Comment comment = CommentDTOMapper.mapToComment(commentDTO);
        comment.setLocalDateTime(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return CommentDTOMapper.mapToCommentDto(savedComment);
    }

    public CommentDTO updateComment(Long id,CommentDTO commentDetails){
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CustomException("Comment not found with id :" + id, HttpStatus.NOT_FOUND));
        comment.setCommentContent(commentDetails.getCommentContent());
        comment.setLocalDateTime(commentDetails.getLocalDateTime());
        Comment updatedComment = commentRepository.save(comment);
        return CommentDTOMapper.mapToCommentDto(updatedComment);
    }

    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CustomException("Comment not found with id :" + id, HttpStatus.NOT_FOUND));
        commentRepository.deleteById(id);
    }
}
