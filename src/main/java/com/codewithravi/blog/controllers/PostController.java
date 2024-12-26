package com.codewithravi.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithravi.blog.config.AppConstants;
import com.codewithravi.blog.exceptions.GeneralException;
import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.ApiResponse;
import com.codewithravi.blog.payloads.PostDto;
import com.codewithravi.blog.payloads.PostResponse;
import com.codewithravi.blog.services.FileService;
import com.codewithravi.blog.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/category/{categoryId}/user/{userId}")
	ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId ) throws ResourceNotFoundException{
		PostDto savedPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(savedPostDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{postId}")
	ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId) throws ResourceNotFoundException{
		PostDto updatePostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePostDto,HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}")
	ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) throws ResourceNotFoundException{
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}")
	ResponseEntity<?> deletePost(@PathVariable Integer postId) throws ResourceNotFoundException{
		this.postService.deletePost(postId);
		ApiResponse apiResponse = new ApiResponse("Post Deleted Successfully",true);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	ResponseEntity<PostResponse> getAllPost(

			@RequestParam(value ="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			){
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) throws ResourceNotFoundException, GeneralException{
		List<PostDto> postDto = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) throws ResourceNotFoundException, GeneralException{
		List<PostDto> postDto = this.postService.getPostsByUser(userId);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	ResponseEntity<List<PostDto>> searchPostByKeyword(@PathVariable("keywords") String key) throws GeneralException{
		List<PostDto> postDto = this.postService.searchPosts(key);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException, ResourceNotFoundException{
		
		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value="/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		InputStream resource = this.fileService.getResource(path,imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
