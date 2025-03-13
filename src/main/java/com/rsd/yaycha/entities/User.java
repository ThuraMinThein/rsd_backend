package com.rsd.yaycha.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "user_name", unique = true)
    private String userName;

    private String password;

    private String bio;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Post> posts;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<CommentLike> commentLikes;

    @OneToMany(mappedBy = "follower",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Follow> follower;
    
    @OneToMany(mappedBy = "following", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Follow> following;

}
