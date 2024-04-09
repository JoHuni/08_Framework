package com.jhh.book.Book.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jhh.book.model.dto.Book;

@Mapper
public interface BookMapper {

	/** 책 전체 조회
	 * @return bookList
	 */
	List<Book> showBook();

	/** 책 삽입
	 * @param book
	 * @return result
	 */
	int insertBook(Book book);

	/** 책 목록 검색
	 * @param searchInput
	 * @return
	 */
	List<Book> searchBook(String searchInput);

	/** 책 삭제
	 * @return
	 */
	int deleteBook(int bookNo);

	/** 가격 수정
	 * @param price
	 * @return result
	 */
	int updatePrice(Book book);
}
