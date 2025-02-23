package com.rsd.yaycha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsd.yaycha.dto.CommentDTO;
import com.rsd.yaycha.entities.Comment;
import com.rsd.yaycha.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment createComment(@RequestBody CommentDTO commentDTO) {
        return commentService.createComment(commentDTO);
    }

}
