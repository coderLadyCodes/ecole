package com.samia.ecole.controllers;

import com.samia.ecole.entities.Comment;
import com.samia.ecole.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/comments")
    public List<Comment> getAllComments(){
       return commentService.getAllComments();
    }
    @PostMapping("/user/post/{postid}/comment")
    public Comment createComment(@RequestBody Comment comment, @PathVariable(value = "postid") Long postid){
        Comment commentcreated = commentService.createComment(comment, postid);
        return commentcreated;
    }
    @DeleteMapping("/user/post/comment/{id}")
    public void deleteComment(@PathVariable(value="id") Long id){
        commentService.deleteComment(id);
    }
}
