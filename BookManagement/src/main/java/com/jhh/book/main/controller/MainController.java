package com.jhh.book.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhh.book.main.model.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final MainService service;
	
	@RequestMapping("/")
	public String mainPage() {
		return "main";
	}
	
	@GetMapping("insertBook")
	public String insertPage() {
		return "insertBook";
	}
	
	@GetMapping("bookRUD")
	public String bookRUDPage() {
		return "bookRUD";
	}
}
