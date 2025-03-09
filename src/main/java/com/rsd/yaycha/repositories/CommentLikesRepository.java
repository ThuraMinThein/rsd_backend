package com.rsd.yaycha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.CommentLike;

@GraphQlRepository
public interface CommentLikesRepository extends JpaRepository<CommentLike, Integer>{

}
