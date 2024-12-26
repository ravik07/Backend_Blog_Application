package com.codewithravi.blog.services;

import java.util.List;

import com.codewithravi.blog.entities.User;
import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Integer userId) throws ResourceNotFoundException;
	UserDto getUserById(Integer userId) throws ResourceNotFoundException;
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId) throws ResourceNotFoundException;
}
