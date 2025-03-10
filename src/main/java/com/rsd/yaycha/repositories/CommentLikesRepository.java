package com.rsd.yaycha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.Comment;
import com.rsd.yaycha.entities.CommentLike;
import com.rsd.yaycha.entities.User;

@GraphQlRepository
public interface CommentLikesRepository extends JpaRepository<CommentLike, Integer>{

    CommentLike findByCommentAndUser(Comment comment, User user);

}
