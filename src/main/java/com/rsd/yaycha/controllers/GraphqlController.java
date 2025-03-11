package com.rsd.yaycha.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.rsd.yaycha.entities.Comment;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.services.CommentService;
import com.rsd.yaycha.services.PostService;
import com.rsd.yaycha.services.UserService;

@Controller
@CrossOrigin(origins = "*")
public class GraphqlController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    //user
    @QueryMapping
    public List<User> users() {
        return userService.findAll();
    }

    @QueryMapping
    public User userWithId(@Argument int id) {
        return userService.findOneById(id);
    }

    @QueryMapping
    public User currentUser(){
        return userService.getCurrentUser();
    }

    @QueryMapping
    public List<User> follower(){
        return userService.getUserFollowers();
    }

    @QueryMapping
    public List<User> following(){
        return userService.getUserFollowings();
    }

    //post
    @QueryMapping
    public List<Post> posts() {
        return postService.getAllPosts();
    }

    @QueryMapping
    public Post postWithId(@Argument int id) {
        return postService.getPostById(id);
    }

    @QueryMapping
    public List<Post> postsWithUserId(@Argument int userId) {
        return postService.getPostByUserId(userId);
    }

    //comment

    @QueryMapping
    public List<Comment> comments() {
        return commentService.getAllComments();
    }

    @QueryMapping
    public Comment commentWithId(@Argument int id) {
        return commentService.getCommentById(id);
    }

    @QueryMapping
    public List<Comment> commentsWithPostId(@Argument int postId) {
        return commentService.getCommentsByPostId(postId);
    }

}
