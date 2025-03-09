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

import com.rsd.yaycha.dto.PostDTO;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.PostLike;
import com.rsd.yaycha.services.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDto) {
        Post newPost =  postService.createPost(postDto);
        return ResponseEntity.ok(newPost);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<PostLike> likePost(@PathVariable int id) {
        PostLike likedPost = postService.likePost(id);
        return ResponseEntity.ok(likedPost);
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<PostLike> unlikePost(@PathVariable int id) {
        PostLike unlikePost = postService.unlikePost(id);
        return ResponseEntity.ok(unlikePost);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable int id) {
        PostDTO deletedPost = postService.deletePost(id);
        return ResponseEntity.ok(deletedPost);
    }

}
