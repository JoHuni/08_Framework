package com.jhh.book.Book.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jhh.book.Book.model.mapper.BookMapper;
import com.jhh.book.model.dto.Book;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	private final BookMapper mapper;
	
	@Override
	public List<Book> showBook() {
		return mapper.showBook();
	}
	
	@Override
	public int insertBook(Book book) {
		return mapper.insertBook(book);
	}
	
	@Override
	public List<Book> searchBook(String searchInput) {
		
		return mapper.searchBook(searchInput);
	}
	
	@Override
	public int deleteBook(int bookNo) {
		return mapper.deleteBook(bookNo);
	}
	
	@Override
	public int updatePrice(Book book) {
		int result =  mapper.updatePrice(book);
		return result;
	}
}
