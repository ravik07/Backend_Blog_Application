package com.codewithravi.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithravi.blog.entities.Comment;
import com.codewithravi.blog.entities.Post;
import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.ApiResponse;
import com.codewithravi.blog.payloads.CommentDto;
import com.codewithravi.blog.repositories.CommentRepo;
import com.codewithravi.blog.repositories.PostRepo;
import com.codewithravi.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
//	@Autowired
//	private ModelMapper modelMapper;
	
//	@Autowired
//	private PostRepo postService;
	
	@PostMapping("/create/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		CommentDto savedCommentDto = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<>(savedCommentDto,HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		this.commentService.deleteComment(commentId);
		ApiResponse apiResponse = new ApiResponse("Comment Deleted Successfully",true);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	
	}

	@PutMapping("/update/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,@PathVariable Integer commentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		CommentDto updatedCommentDto = this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<>(updatedCommentDto,HttpStatus.OK);
	}

	@GetMapping("/get/{commentId}")
	public ResponseEntity<CommentDto> findById(@PathVariable Integer commentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		CommentDto commentDto = this.commentService.findById(commentId);
		return new ResponseEntity<>(commentDto,HttpStatus.OK);
		
	}

	@GetMapping("/all")
	public ResponseEntity<List<CommentDto>> getAll() {
		// TODO Auto-generated method stub
		List<CommentDto> commentDtos = this.commentService.getAll();
		
		return new ResponseEntity<>(commentDtos,HttpStatus.OK);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<List<CommentDto>> findByPost(@PathVariable Integer postId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		List<CommentDto> commentDtos = this.commentService.findByPost(postId);
		
		return new ResponseEntity<>(commentDtos,HttpStatus.OK);
	}

}
