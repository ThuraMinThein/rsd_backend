package com.rsd.yaycha.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.Comment;
import com.rsd.yaycha.entities.CommentLike;
import com.rsd.yaycha.entities.User;

@GraphQlRepository
public interface CommentLikesRepository extends JpaRepository<CommentLike, Integer>{

    CommentLike findByCommentAndUser(Comment comment, User user);

    @Query(
        "SELECT cl FROM CommentLike cl WHERE cl.comment.id = :commentId"
    )
    List<CommentLike> findCommentLikesByCommentId(int commentId);

}
