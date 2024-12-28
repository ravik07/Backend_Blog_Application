package com.codewithravi.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithravi.blog.entities.Comment;
import com.codewithravi.blog.entities.Post;
import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.CommentDto;
import com.codewithravi.blog.payloads.PostDto;
import com.codewithravi.blog.repositories.CommentRepo;
import com.codewithravi.blog.repositories.PostRepo;
import com.codewithravi.blog.services.CommentService;

@Service
public class CustomerServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub

		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		return this.modelMapper.map(this.commentRepo.save(comment),CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Comment comment =this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","CommentId",commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Comment comment  = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","CommentId",commentId));
		comment.setContent(commentDto.getContent());
		
		
		CommentDto commentDtoUpdated = this.modelMapper.map(this.commentRepo.save(comment), CommentDto.class);
		return commentDtoUpdated;
	}

	@Override
	public CommentDto findById(Integer commentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Comment comment  = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","CommentId",commentId));
		
		CommentDto commentDto = this.modelMapper.map(comment, CommentDto.class);
		return commentDto;
	}

	@Override
	public List<CommentDto> getAll() {
		// TODO Auto-generated method stub
		return this.commentRepo.findAll().stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public List<CommentDto> findByPost(Integer postId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
		List<CommentDto> commentDtos = this.commentRepo.findByPostId(post.getPostId()).stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return commentDtos;
	}

}
