package com.blogapp.BlogApp1.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
// import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blogapp.BlogApp1.entity.Post;
import com.blogapp.BlogApp1.exception.ResourceNotFoundException;
import com.blogapp.BlogApp1.payload.PostDto;
import com.blogapp.BlogApp1.payload.PostResponse;
import com.blogapp.BlogApp1.repository.PostRepository;
import com.blogapp.BlogApp1.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper){
        this.postRepository = postRepository;
        this.mapper = mapper;
    }


    @Override
    public PostDto createPost(PostDto postDto){

        Post post = mapToEntity((postDto));   
        Post newPost = postRepository.save(post);

        //convert entity to Dto
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
        
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content =  listOfPosts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        // Post updatePost = postRepository.save(mapToEntity(postDto));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    // Converted entity into Dto
    private PostDto mapToDto(Post post){

        PostDto postDto = mapper.map(post, PostDto.class);

        // PostDto postDto = new PostDto();
        // postDto.setId(post.getId());
        // postDto.setTitle(post.getTitle());
        // postDto.setDescription(post.getDescription());
        // postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto, Post.class);

        // Post post = new Post();
        // post.setId(postDto.getId());
        // post.setTitle(postDto.getTitle());
        // post.setDescription(postDto.getDescription());
        // post.setContent(postDto.getContent());
        return post;
    }

}
