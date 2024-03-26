package edu.kh.todo.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.todo.model.dto.Todo;

public interface TodoService {

	
	/** 할 일 목록 + 완료된 할 일 개수 조회
	 * @return
	 */
	Map<String, Object> selectAll();

	/** 할 일 추가
	 * @param todoTitle
	 * @param todoContent
	 * @return result
	 */
	int addTodo(String todoTitle, String todoContent);

	/** 할 일 상세 조회
	 * @param todoNo
	 * @return todo
	 */
	Todo todoDetail(int todoNo);

	/** 할 일 삭제
	 * @param todoNo
	 * @return result
	 */
	int todoDelete(int todoNo);

	/** 할 일 수정
	 * @param todo
	 * @return todo
	 */
	int todoUpdate(Todo todo);

	
	/** 할 일 완료 여부 수정
	 * @param todo
	 * @return todo
	 */
	int todoCompleteUpdate(Todo todo);

	/** 할 일 개수 조회
	 * @return totalCount
	 */
	int getTotalCount();

	/** 완료된 할 일 조회
	 * @return completeCount
	 */
	int getCompleteCount();

	/** 할 일 목록 조회
	 * @return
	 */
	List<Todo> selectList();

	int updateComplete(Todo todo);
	
}
