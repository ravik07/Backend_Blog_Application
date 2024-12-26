package com.codewithravi.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithravi.blog.entities.Category;
import com.codewithravi.blog.payloads.CategoryDto;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
	
}
