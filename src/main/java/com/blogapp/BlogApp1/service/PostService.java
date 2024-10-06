package com.blogapp.BlogApp1.service;

import java.util.List;

import com.blogapp.BlogApp1.payload.PostDto;
import com.blogapp.BlogApp1.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);
    // List<PostDto> getAllPosts(int pageNo, int pageSize);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);



}

