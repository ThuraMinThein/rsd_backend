package com.rsd.yaycha.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.Comment;

@GraphQlRepository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByPostId(int postId);

}
