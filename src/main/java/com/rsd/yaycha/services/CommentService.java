package com.rsd.yaycha.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsd.yaycha.dto.CommentDTO;
import com.rsd.yaycha.entities.Comment;
import com.rsd.yaycha.entities.CommentLike;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.CommentLikesRepository;
import com.rsd.yaycha.repositories.CommentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikesRepository commentLikesRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public Comment createComment(CommentDTO commentDTO) {
        User user = userService.getCurrentUser();
        Post post = postService.getPostById(commentDTO.getPostId());
        Comment comment = convertDtoToEntity(commentDTO, user, post);
        return commentRepository.save(comment);
    }

    public CommentLike likeUnlikeComment(int id) {
        User user = userService.getCurrentUser();
        if(user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Comment comment = getCommentById(id);
        if(comment == null) {
            throw new EntityNotFoundException("Comment not found");
        }
        CommentLike commentLike = commentLikesRepository.findByCommentAndUser(comment, user);
        if(commentLike == null) {
            
        CommentLike newCommentLike = new CommentLike();
        newCommentLike.setComment(comment);
        newCommentLike.setUser(user);
        CommentLike result = commentLikesRepository.save(newCommentLike);
        increaseCommentLike(id);
        return result;
        }
        commentLikesRepository.delete(commentLike);
        decreaseCommentLike(id);
        return commentLike;
    }

    
    private void increaseCommentLike(int id) {
        Comment comment = getCommentById(id);
        int totalLikes = comment.getTotalLikes() > 0 ? comment.getTotalLikes() + 1 : 1;
        comment.setTotalLikes(totalLikes);
        commentRepository.save(comment);
    }

    private void decreaseCommentLike(int id) {
        Comment comment = getCommentById(id);
        int totalLikes = comment.getTotalLikes() > 0 ? comment.getTotalLikes() - 1 : 0;
        comment.setTotalLikes(totalLikes);
        commentRepository.save(comment);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    
    public List<Comment> getCommentsByPostId(int postId) {
        return commentRepository.findAllByPostId(postId);
    }


    public Comment getCommentById(int id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found with this id"));
    }

    
    public CommentDTO deleteComment(int id) {
        Comment comment = getCommentById(id);
        if(comment == null) {
            throw new EntityNotFoundException("Comment not found");
        }
        commentRepository.delete(comment);
        return convertEntityToDto(comment);
    }

    //utils
    public Comment convertDtoToEntity(CommentDTO commentDTO, User user, Post post) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        comment.setUser(user);
        comment.setPost(post);
        return comment;
    }

    public CommentDTO convertEntityToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setPostId(comment.getPost().getId());
        return commentDTO;
    }

}
