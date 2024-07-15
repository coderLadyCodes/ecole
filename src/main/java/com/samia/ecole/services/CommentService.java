//package com.samia.ecole.services;
//import com.samia.ecole.DTOs.CommentDTO;
//import com.samia.ecole.entities.Comment;
//import com.samia.ecole.exceptions.CustomException;
//import com.samia.ecole.repositories.CommentRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CommentService {
//    private final CommentRepository commentRepository;
//
//    public CommentService(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }
//        public CommentDTO mapToCommentDto(Comment comment) {
//            CommentDTO commentDTO = new CommentDTO();
//            commentDTO.setId(comment.getId());
//            commentDTO.setCommentContent(comment.getCommentContent());
//            commentDTO.setLocalDateTime(comment.getLocalDateTime());
//            return commentDTO;
//        }
//
//        public Comment mapToComment(CommentDTO commentDTO) {
//            Comment comment = new Comment();
//            comment.setId(commentDTO.getId());
//            comment.setCommentContent(commentDTO.getCommentContent());
//            comment.setLocalDateTime(commentDTO.getLocalDateTime());
//            return comment;
//        }
//
//        public List<CommentDTO> getAllComments() {
//            List<Comment> comments = commentRepository.findAll();
//            return comments.stream().map(this::mapToCommentDto)
//                    .collect(Collectors.toList());
//        }
//
//        public CommentDTO createComment(CommentDTO commentDTO) {
//            Comment comment = mapToComment(commentDTO);
//            comment.setLocalDateTime(LocalDateTime.now());
//            Comment savedComment = commentRepository.save(comment);
//            return mapToCommentDto(savedComment);
//        }
//
//        public CommentDTO updateComment(Long id, CommentDTO commentDetails) {
//            Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException("Comment not found with id :" + id, HttpStatus.NOT_FOUND));
//            comment.setCommentContent(commentDetails.getCommentContent());
//            comment.setLocalDateTime(commentDetails.getLocalDateTime());
//            Comment updatedComment = commentRepository.save(comment);
//            return mapToCommentDto(updatedComment);
//        }
//
//        public void deleteComment(Long id) {
//            Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException("Comment not found with id :" + id, HttpStatus.NOT_FOUND));
//            commentRepository.deleteById(id);
//        }
//    }
