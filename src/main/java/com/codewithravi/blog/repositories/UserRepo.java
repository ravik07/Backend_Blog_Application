package com.codewithravi.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithravi.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
 
}
