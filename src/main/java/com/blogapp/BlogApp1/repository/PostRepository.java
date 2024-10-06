package com.blogapp.BlogApp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

// import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.BlogApp1.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
