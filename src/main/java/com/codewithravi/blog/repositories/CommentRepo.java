package com.codewithravi.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codewithravi.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{
	@Query("SELECT c FROM Comment c JOIN c.post p WHERE p.id = :postId")
	List<Comment> findByPostId(@Param("postId") int postId);
	
}
