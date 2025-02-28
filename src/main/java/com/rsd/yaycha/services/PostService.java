package com.rsd.yaycha.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsd.yaycha.dto.PostDTO;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.PostRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public Post createPost(PostDTO postDto) {
        User user = userService.getCurrentUser();
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Post post = convertDtoToEntity(postDto, user);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    
    public List<Post> getPostByUserId(int userId) {
        return postRepository.findAllByUserId(userId);
    }


    public Post getPostById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    
    public PostDTO deletePost(int id) {
        Post post = getPostById(id);
        if(post == null){
            throw new EntityNotFoundException("Post not found");
        }
        postRepository.delete(post);
        return convertEntityToDto(post);
    }


    //utils
    public Post convertDtoToEntity(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setCreatedAt(postDTO.getCreatedAt());
        post.setUser(user);
        return post;
    }

    public PostDTO convertEntityToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        return postDTO;
    }

}
