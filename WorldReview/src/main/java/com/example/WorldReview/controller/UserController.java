package com.example.WorldReview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.WorldReview.dto.UserDTO;
import com.example.WorldReview.service.UserService;


public class UserController {
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String save(UserDTO userDTO) {
		UserService.signup(userDTO);
		return "redirect:/list";
		
		
	}
}
