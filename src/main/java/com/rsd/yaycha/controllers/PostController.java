package com.rsd.yaycha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsd.yaycha.dto.PostDTO;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody PostDTO postDto) {
        return postService.createPost(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable int id) {
        PostDTO deletedPost = postService.deletePost(id);
        return ResponseEntity.ok(deletedPost);
    }

}
