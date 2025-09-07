package com.example.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Post;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto){
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto responseDto = mapToDto(savedPost);
        return responseDto;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create  pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream()
                                    .map(post->mapToDto(post))
                                    .collect(Collectors.toList());

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
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found with id: " + id));
        PostDto responseDto = mapToDto(post);
        return responseDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found with id: " + id));
        
        existingPost = Post.builder()
            .id(existingPost.getId())
            .title(postDto.getTitle())
            .description(postDto.getDescription())
            .content(postDto.getContent())
            .build();
        Post updatedPost = postRepository.save(existingPost);
        return mapToDto(updatedPost);
    }


    @Override
    public PostDto deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found with id: " + id));
        postRepository.delete(post);
        return mapToDto(post);
    }




    // Convert entity to Dto (assuming a constructor or builder exists)
    private PostDto mapToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }

    // Convert Dto to entity (assuming a constructor or builder exists)
    private Post mapToEntity(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build();
    }
    
}
