package com.codewithravi.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithravi.blog.entities.Category;
import com.codewithravi.blog.entities.Post;
import com.codewithravi.blog.entities.User;
import com.codewithravi.blog.exceptions.GeneralException;
import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.PostDto;
import com.codewithravi.blog.payloads.PostResponse;
import com.codewithravi.blog.repositories.CategoryRepo;
import com.codewithravi.blog.repositories.PostRepo;
import com.codewithravi.blog.repositories.UserRepo;
import com.codewithravi.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) throws ResourceNotFoundException{
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow( () -> new ResourceNotFoundException("User","UserId",userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName("Default.png");
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost,PostDto.class);

	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","PostId",postId));
//		post.setImageName(postDto.getImageName());
		Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		post.setCategory(category);
		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
		
		
	}

	@Override
	public void deletePost(Integer postId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		
//		using if else loop
//		if(sortDir =="asc") {
//			Sort sort =Sort.by(sortBy).ascending();
//		}else {
//			Sort sort = Sort.by(sortBy).descending();
//		}
		
		//using ternary operator
		Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findAll(p);

        List<Post> allPosts = pagePost.getContent();
//		
        
		List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setTotalElements(pagePost.getNumberOfElements());
        postResponse.setLastPage(pagePost.isLast());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) throws ResourceNotFoundException, GeneralException{
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(category);
		if(!posts.isEmpty()) {
			return posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		}
		throw new GeneralException("For this category "+categoryId +" posts are not available");
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) throws ResourceNotFoundException, GeneralException{
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		if(!posts.isEmpty()) {
			return posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		}
		throw new GeneralException("For this user "+userId +" posts are not available");
		
	}

	@Override
	public List<PostDto> searchPosts(String keyword) throws GeneralException{
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.searchByTitle(keyword);
		if(!posts.isEmpty()) {
			return posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		}
		throw new GeneralException("Posts are not available where this keyword is used");
	}

}
