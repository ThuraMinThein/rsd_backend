package com.rsd.yaycha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.Post;

@GraphQlRepository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
