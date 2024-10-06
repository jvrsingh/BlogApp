package com.blogapp.BlogApp1.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blogapp.BlogApp1.entity.Comment;
import com.blogapp.BlogApp1.entity.Post;
import com.blogapp.BlogApp1.exception.BlogAPIException;
import com.blogapp.BlogApp1.exception.ResourceNotFoundException;
import com.blogapp.BlogApp1.payload.CommentDto;
import com.blogapp.BlogApp1.repository.CommentRepository;
import com.blogapp.BlogApp1.repository.PostRepository;
import com.blogapp.BlogApp1.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl (CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        // set post to comment entity
        comment.setPost(post);
        // save comment entity to DB
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId){
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // TODO Auto-generated method stub
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment do not belongs to post");
        }

        return mapToDTO(comment);
    } 

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        // TODO Auto-generated method stub
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment do not belongs to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        
        Comment updateComment = commentRepository.save(comment);
        return mapToDTO(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment do not belongs to post");
        }

        commentRepository.delete(comment);       
    }





    private CommentDto mapToDTO(Comment comment){

        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        // CommentDto commentDto = new CommentDto();
        // commentDto.setId(comment.getId());
        // commentDto.setName(comment.getName());
        // commentDto.setEmail(comment.getEmail());
        // commentDto.setBody(comment.getBody());
        return commentDto;        
    }

    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = mapper.map(commentDto, Comment.class);
        // Comment comment = new Comment();
        // comment.setId(commentDto.getId());
        // comment.setName(commentDto.getName());
        // comment.setEmail(commentDto.getEmail());
        // comment.setBody(commentDto.getBody());
        return comment;
    }





}
