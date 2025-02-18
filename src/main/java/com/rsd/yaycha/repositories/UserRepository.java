package com.rsd.yaycha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import com.rsd.yaycha.entities.User;

@GraphQlRepository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String email);

}
