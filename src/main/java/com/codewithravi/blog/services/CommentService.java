package com.codewithravi.blog.services;

import java.util.List;

import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer postId) throws ResourceNotFoundException;
	void deleteComment(Integer commentId) throws ResourceNotFoundException;
	CommentDto updateComment(CommentDto commentDto,Integer commentId) throws ResourceNotFoundException;
	CommentDto findById(Integer commentId) throws ResourceNotFoundException;
	List<CommentDto> getAll();
	List<CommentDto> findByPost(Integer postId) throws ResourceNotFoundException;
	
}
