package com.jhh.book.Book.model.service;

import java.util.List;

import com.jhh.book.model.dto.Book;

public interface BookService {

	/** 전체 조회
	 * @return bookList
	 */
	List<Book> showBook();

	/** 책 등록
	 * @param book
	 * @return
	 */
	int insertBook(Book book);

	/** 책 목록 검색
	 * @param searchInput
	 * @return
	 */
	List<Book> searchBook(String searchInput);

	/** 책 삭제
	 * @return result
	 */
	int deleteBook(int bookNo);

	/** 가격 수정
	 * @param price
	 * @return result
	 */
	int updatePrice(Book book);





}
