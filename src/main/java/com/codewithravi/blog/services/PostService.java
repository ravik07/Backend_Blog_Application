package com.codewithravi.blog.services;

import java.util.List;

import com.codewithravi.blog.exceptions.GeneralException;
import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.PostDto;
import com.codewithravi.blog.payloads.PostResponse;


public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) throws ResourceNotFoundException;
	PostDto updatePost(PostDto postDto,Integer postId) throws ResourceNotFoundException;
	void deletePost(Integer postId) throws ResourceNotFoundException;
	PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy,String sortDir);
	PostDto getPostById(Integer postId) throws ResourceNotFoundException;
	List<PostDto> getPostsByCategory(Integer categoryId) throws ResourceNotFoundException, GeneralException;
	List<PostDto> getPostsByUser(Integer UserId) throws ResourceNotFoundException, GeneralException;
	List<PostDto> searchPosts(String keyword) throws GeneralException;
}
