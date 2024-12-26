package com.codewithravi.blog.services;

import java.util.List;

import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) throws ResourceNotFoundException;
	public void deleteCategory(Integer categoryId) throws ResourceNotFoundException;
	public CategoryDto getCategory(Integer categoryId) throws ResourceNotFoundException;
	List<CategoryDto> getCategories();

}
