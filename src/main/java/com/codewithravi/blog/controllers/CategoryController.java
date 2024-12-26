package com.codewithravi.blog.controllers;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.ApiResponse;
import com.codewithravi.blog.payloads.CategoryDto;
import com.codewithravi.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto savedCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(savedCategoryDto,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{categoryId}")
	ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer catId) throws ResourceNotFoundException{
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<>(updatedCategoryDto,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer catId) throws ResourceNotFoundException{
		CategoryDto updatedCategoryDto = this.categoryService.getCategory(catId);
		return new ResponseEntity<>(updatedCategoryDto,HttpStatus.OK);
	}
	
	@GetMapping("/")
	ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> categoriesDto = this.categoryService.getCategories();
		return new ResponseEntity<>(categoriesDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer catId) throws ResourceNotFoundException{
		this.categoryService.deleteCategory(catId);
		ApiResponse apiResponse = new ApiResponse("Category Deleted Successfully",true);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	
}
