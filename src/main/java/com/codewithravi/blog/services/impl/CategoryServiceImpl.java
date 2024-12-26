package com.codewithravi.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithravi.blog.entities.Category;
import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.CategoryDto;
import com.codewithravi.blog.repositories.CategoryRepo;
import com.codewithravi.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return this.modelMapper.map(this.categoryRepo.save(category),CategoryDto.class);
		
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) throws ResourceNotFoundException{
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId", categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());;
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		return this.modelMapper.map(this.categoryRepo.save(category),CategoryDto.class);
		
	}

	@Override
	public void deleteCategory(Integer categoryId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		CategoryDto categoryDto = this.getCategory(categoryId);
		this.categoryRepo.delete(this.modelMapper.map(categoryDto, Category.class));
		
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = this.categoryRepo.findAll();
		return categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

		
	}

}
