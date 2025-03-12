package com.rsd.yaycha.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.Post;
import com.rsd.yaycha.entities.PostLike;
import com.rsd.yaycha.entities.User;

@GraphQlRepository
public interface PostLikesRepository extends JpaRepository<PostLike, Integer> {

    PostLike findByPostAndUser(Post post, User user);

    @Query(
        "SELECT pl FROM PostLike pl WHERE pl.post.id = :postId"
    )
    List<PostLike> findPostLikesByPostId(int postId);

}
