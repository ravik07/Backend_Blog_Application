package com.codewithravi.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithravi.blog.exceptions.ResourceNotFoundException;
import com.codewithravi.blog.entities.User;
import com.codewithravi.blog.payloads.UserDto;
import com.codewithravi.blog.repositories.UserRepo;
import com.codewithravi.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) throws ResourceNotFoundException{
		// TODO Auto-generated method stub
//		User user = this.userRepo.findById(userId).orElseThrow();
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(user.getPassword());
		User updatedUser = this.userRepo.save(user);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
//		List<UserDto> userDtos = new ArrayList<>();
//		for(User user : users) {
//			userDtos.add(this.userToDto(user));
//		}
		
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		UserDto userDto = this.getUserById(userId);
		this.userRepo.delete(this.modelMapper.map(userDto, User.class));
	}
	
	public User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		return this.modelMapper.map(userDto, User.class);
		
	}
	
	public UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		return this.modelMapper.map(user, UserDto.class);
		
		
	}
}
