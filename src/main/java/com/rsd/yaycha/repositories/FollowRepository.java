package com.rsd.yaycha.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.Follow;
import com.rsd.yaycha.entities.User;

@GraphQlRepository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    @Query("SELECT f.follower FROM Follow f WHERE f.following.id = :id")
    List<User> findFollowerByFollowingId(@Param("id") int id);

    @Query("SELECT f.following FROM Follow f WHERE f.follower.id = :id")
    List<User> findFollowingByFollowerId(@Param("id") int id);

}
