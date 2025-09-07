package com.example.blog.service;

import java.util.List;

import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPost( int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);

    PostDto deletePostById(Long id);
    
}
