package com.rsd.yaycha.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsd.yaycha.dto.CommentDTO;
import com.rsd.yaycha.entities.Comment;
import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public Comment createComment(CommentDTO commentDTO) {
        User user = userService.findOneById(commentDTO.getUserId());
        Post post = postService.getPostById(commentDTO.getPostId());
        Comment comment = convertDtoToEntity(commentDTO, user, post);
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment getCommentById(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    
    public CommentDTO deleteComment(int id) {
        Comment comment = getCommentById(id);
        if(comment == null) {
            throw new RuntimeException("Comment not found");
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
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setPostId(comment.getPost().getId());
        return commentDTO;
    }

}
