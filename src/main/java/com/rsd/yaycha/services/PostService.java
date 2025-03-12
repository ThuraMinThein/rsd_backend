package com.rsd.yaycha.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsd.yaycha.dto.PostDTO;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.PostLike;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.PostLikesRepository;
import com.rsd.yaycha.repositories.PostRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;

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

    public PostLike likeUnlikePost(int id) {
        User user = userService.getCurrentUser();
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Post post = getPostById(id);
        if(post == null){
            throw new EntityNotFoundException("Post not found");
        }
        PostLike postLike = postLikesRepository.findByPostAndUser(post, user);
        if(postLike == null){
            PostLike newPostLike = new PostLike();
            newPostLike.setPost(post);
            newPostLike.setUser(user);
            newPostLike.setCreatedAt(new Date());
            PostLike result = postLikesRepository.save(newPostLike);
            increasePostLike(id);
            return result;
        }else {
            postLikesRepository.delete(postLike);
            decreasePostLike(id);
            return postLike;
        }
    }

    private void increasePostLike(int id) {
        Post post = getPostById(id);
        int totalLikes = post.getTotalLikes() > 0 ? post.getTotalLikes() + 1 : 1;
        post.setTotalLikes(totalLikes);
        postRepository.save(post);
    }

    private void decreasePostLike(int id) {
        Post post = getPostById(id);
        int totalLikes = post.getTotalLikes() > 0 ? post.getTotalLikes() - 1 : 0;
        post.setTotalLikes(totalLikes);
        postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    
    public List<Post> getPostByUserId(int userId) {
        return postRepository.findAllByUserId(userId);
    }

    
    public List<PostLike> getAllPostLikes(int postId) {
        return postLikesRepository.findPostLikesByPostId(postId);
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
