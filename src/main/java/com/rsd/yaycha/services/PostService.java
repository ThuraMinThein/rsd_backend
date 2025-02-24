package com.rsd.yaycha.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsd.yaycha.dto.PostDTO;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public Post createPost(PostDTO postDto) {
        User user = userService.findOneById(postDto.getUserId());
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Post post = convertDtoToEntity(postDto, user);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post convertDtoToEntity(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setCreatedAt(postDTO.getCreatedAt());
        post.setUser(user);
        return post;
    }

}
