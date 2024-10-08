package com.blogapp.BlogApp1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.BlogApp1.entity.Comment;


public interface CommentRepository  extends JpaRepository<Comment, Long>{

    List<Comment> findByPostId(long postId);

    

}
