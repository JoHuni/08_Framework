package com.jhh.book.Book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jhh.book.Book.model.service.BookService;
import com.jhh.book.model.dto.Book;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {
	private final BookService service;
	
	@ResponseBody
	@GetMapping("showBook")
	public List<Book> showBook() {
		List<Book> bookList = service.showBook();
		
		return bookList;
	}
	
	@PostMapping("insertBook")
	public String insertBook(
			Book book,
			RedirectAttributes ra) {
		
//		String bookTitle = book.getBookTitle();
//		String bookWriter = book.getBookWriter();
//		int bookPrice = book.getBookPrice();
		
		int result = service.insertBook(book);
		
		String message = null;
		
		if(result > 0) {
			message = "등록 성공!";
			ra.addFlashAttribute("message", message);
			return "redirect:/";
		}
		else {
			message = "등록 실패...";
			ra.addFlashAttribute("message", message);
			return "redirect:/insertBook";
		}
	}
	
	@ResponseBody
	@GetMapping("searchBook")
	public List<Book> searchBook(@RequestParam("searchInput") String searchInput) {
		List<Book> bookList = service.searchBook(searchInput);
		
		return bookList;
	}
	
	@ResponseBody
	@PostMapping("deleteBook")
	public int deleteBook(@RequestBody int bookNo) {
		int result = service.deleteBook(bookNo);
		
		return result;
	}
	
	@ResponseBody
	@PutMapping("updatePrice")
	public int updatePrice(@RequestBody Book book) {
		int result = service.updatePrice(book);
		
		return result;
	}
}
