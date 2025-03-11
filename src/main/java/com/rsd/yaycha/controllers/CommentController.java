package com.rsd.yaycha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsd.yaycha.dto.CommentDTO;
import com.rsd.yaycha.entities.Comment;
import com.rsd.yaycha.entities.CommentLike;
import com.rsd.yaycha.services.CommentService;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment createComment(@RequestBody CommentDTO commentDTO) {
        return commentService.createComment(commentDTO);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<CommentLike> likeComment(@PathVariable int id) {
        CommentLike likedComment = commentService.likeUnlikeComment(id);
        return ResponseEntity.ok(likedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable int id) {
        CommentDTO comment = commentService.deleteComment(id);
        return ResponseEntity.ok(comment);
    }

}
