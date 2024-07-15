//package com.samia.ecole.controllers;
//import com.samia.ecole.DTOs.CommentDTO;
//import com.samia.ecole.services.CommentService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/comments")
//public class CommentController {
//    private final CommentService commentService;
//
//    public CommentController(CommentService commentService) {
//        this.commentService = commentService;
//    }
//    @GetMapping()
//    public List<CommentDTO> getAllComments(){
//       return commentService.getAllComments();
//    }
//    @PostMapping()
//    public CommentDTO createComment(@RequestBody CommentDTO commentDTO){
//        return commentService.createComment(commentDTO);
//
//    }
//    @PutMapping("{id}")
//    public CommentDTO updateComment(@PathVariable(value = "id") Long id, @RequestBody CommentDTO commentDetails){
//        return commentService.updateComment(id,commentDetails);
//    }
//    @DeleteMapping("{id}")
//    public void deleteComment(@PathVariable(value="id") Long id){
//        commentService.deleteComment(id);
//    }
//}
