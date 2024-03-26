package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

/* @ResponseBody
 * - 컨트롤러 메서드의 반환 값
 * 	 HTTP 응답 본문에 직접 바인딩하는 역할임을 명시
 * 
 * (쉬운 해석)
 * -> 컨트롤러 메서드의 반환 값
 * 요청했던 HTML/JS 파일 부분에 값을 돌려 보낼 것이다를 명시
 * 
 * - forward/redirect로 인식 하지 않음
 * */

/* @RequestBody
 * - 비동기 요청(ajax)시 전달 되는 데이터 중 
 * 	 body 부분에 포함된 요청 데이터를
 *   알맞은 Java 객체 타입으로 바인딩하는 어노테이션
 *   
 * (쉬운 설명)
 * - 비동기 요청 시 body에 담긴 값을
 *   알맞은 타입으로 변환해서 매개변수에 저장 
 * */




/* [HttpMessageConverter]
 *  Spring에서 비동기 통신 시
 * - 전달되는 데이터의 자료형
 * - 응답하는 데이터의 자료형
 * 위 두가지 알맞은 형태로 가공(변환)해주는 객체
 * 
 * - 문자열, 숫자 <-> TEXT
 * - Map <-> JSON
 * - DTO <-> JSON
 * 
 * (참고)
 * HttpMessageConverter가 동작하기 위해서는
 * Jackson-data-bind 라이브러리가 필요한데
 * Spring Boot 모듈에 내장되어 있음
 * (Jackson : 자바에서 JSON 다루는 방법 제공하는 라이브러리)
 */





@RequestMapping("ajax")
@Controller
@Slf4j
public class AjaxController {
	
	// @Autowired
	// - 등록된 Bean 중 같은 타입 또는 상속관계 Bean을
	// 해당 필드에 의존성 주입(DI)
	
	@Autowired
	private TodoService service;
	
	@GetMapping("main")
	public String ajaxMain() {
		return "ajax/main";
	}
	
	// 전체 Todo 개수 조회
	// -> forward / redirect를 원하는 것이 아님 !!!
	// -> "전체 Todo 개수"라는 데이터가 반환되는 것을 원함!!
	
	@ResponseBody // 값(totalCount) 그대로 돌려 보낼 거임
	@GetMapping("totalCount")
	public int getTotalCount() {
		log.info("getTotalCount() 메서드 호출됨!!!");
		
		int totalCount = service.getTotalCount();
		
		return totalCount;
	}
	
	// completeCount 값을 DB에서 조회해서 그대로 반환
	// -> 값(Data)만 응답하길 원함
	
	@ResponseBody
	@GetMapping("completeCount")
	public int getCompleteCount() {
		return service.getCompleteCount();
	}
	
	@ResponseBody
	@PostMapping("add")
	public int addTodo(
//			JSON이 파라미터로 전달된 경우 아래의 방법으론 얻어올 수 없음
//			@RequestParam("todoTitle") String todoTitle,
//			@RequestParam("todoContent") String todoContent
			
			@RequestBody Todo todo // 요청 body에 담긴 값을 Todo에 저장
			) {
			log.debug(todo.toString());
			
			int result = service.addTodo(todo.getTodoTitle(), todo.getTodoContent());
		
		return result;
	}
	
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
		List<Todo> todoList = service.selectList();
		
		// List 타입은 Java의 타입으로
		// JS에서 사용 할 수 없음
		// -> JSON 변환(GSON 라이브러리 이용)

		
		return todoList;
		
		// List(Java 전용 타입)를 반환
		// -> JS에서 인식할 수 없기 때문에
		//    HttpMessageConverter가 JSON [{}, {}, {}](JSON Array) 형태로 변환
	}
	
	@ResponseBody // 요청한 곳으로 데이터 돌려보냄
	@GetMapping("detail")
	public Todo selectTodo(@RequestParam("todoNo") int todoNo) {
		// return 자료형은 : Todo
		// -> HttpMessageConverter가 String(JSON)형태로 변환해서 반환
		return service.todoDetail(todoNo);
	}
	
	// Delete 방식 요청 처리(비동기 요청만 가능!!)
	@ResponseBody
	@DeleteMapping("delete")
	public int deleteTodo(@RequestBody int todoNo) {
		return service.todoDelete(todoNo);
	}
	
	@ResponseBody
	@PutMapping("changeComplete")
	public int changeComplete(@RequestBody Todo todo) {
		return service.updateComplete(todo);
	}
	
	@ResponseBody
	@PutMapping("update")
	public int todoUpdate(@RequestBody Todo todo) {
		return service.todoUpdate(todo);
	}
}
