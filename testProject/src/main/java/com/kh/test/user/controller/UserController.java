package com.kh.test.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService service;
	@GetMapping("userSearch")
	public String userSearch(
			@RequestParam("inputId") String inputId,
			Model model) {
		List<User> userList = service.userSearch(inputId);
		
		if(userList.isEmpty()) {
			return "searchFail";
		}
		else {
			model.addAttribute("user", userList);
			return "searchSuccess";
		}
	}
}
