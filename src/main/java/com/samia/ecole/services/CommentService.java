package com.samia.ecole.services;

import com.samia.ecole.entities.Comment;
import com.samia.ecole.entities.Post;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.CommentRepository;
import com.samia.ecole.repositories.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment){
//        Post postcomment = postRepository.findById(postid).orElseThrow(() -> new CustomException("Post not found with id : " + postid, HttpStatus.NOT_FOUND));
//        comment.setPost(postcomment);        //, Long postid
        comment.setLocalDateTime(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment commentDetails,Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CustomException("Comment not found with id :" + id, HttpStatus.NOT_FOUND));
        comment.setCommentContent(commentDetails.getCommentContent());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CustomException("Comment not found with id :" + id, HttpStatus.NOT_FOUND));
        commentRepository.deleteById(id);
    }
}
