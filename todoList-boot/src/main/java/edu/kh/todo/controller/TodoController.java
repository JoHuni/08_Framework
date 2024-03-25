package edu.kh.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;

@Controller // Bean 등록
@RequestMapping("todo") // "/todo"로 시작하는 요청 매핑
public class TodoController {
	
	@Autowired // 같은 타입 Bean 의존성 주입 (DI)
	private TodoService service;
	
	@PostMapping("add")	// "/todo/add" POST 방식 요청 매핑
	public String addTodo(
			@RequestParam("todoTitle") String todoTitle,
			@RequestParam("todoContent") String todoContent,
			RedirectAttributes ra
			) {
		
		// RedirectAttributes : 리다이렉트 시 값을 1회성으로 전달하는 객체
		
		// RedirectAttributes.addFlashAttribute("key", value) 형식으로 잠깐 세션에 속성 추가
		
		// [원리]
		// 응답 전 : request scope
		// redirect 중 : session scope로 이동
		// 응답 후 : reqeust scope로 복귀
		
		
		// 서비스 메서드 호출 후 결과 반환 받기
		int result = service.addTodo(todoTitle, todoContent);
		
		String message = null;
		if(result > 0) {
			message = "할 일 추가 성공!!!";
		}
		else {
			message = "할 일 추가 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:/"; //메인 페이지 재요청
	}
	
	@GetMapping("detail")
	public String todoDetail(
			@RequestParam("todoNo") int todoNo,
			Model model,
			RedirectAttributes ra) {
		Todo todo = service.todoDetail(todoNo);
		
		String path = null;
		
		if(todo != null) {
			// tempaltes/todo/detail.html
			path = "todo/detail"; // forward 경로
			
			model.addAttribute("todo", todo);
		}
		else{
			path = "redirct:/"; // 메인 페이지로 리다이렉트
			// RedirectAttributes : 리다이렉트시 데이터를 request scope로 전달할 수 있는 객체
			
			ra.addFlashAttribute("message", "해당 할 일이 존재하지 않습니다.");
		}
		
		return path;
	}	
	
	@GetMapping("delete")
	public String todoDelete(
			@RequestParam("todoNo") int todoNo,
			RedirectAttributes ra) {
		int result = service.todoDelete(todoNo);
		
		String message = null;
		
		String path = null;
		if(result > 0) {	
			message = "삭제 성공!";
		    path = "redirect:/";
		}
		else {
			message = "삭제 실패!";
			path = "redirect:/todo/detail";
		}
		ra.addFlashAttribute("message", message);
		return path;

	}
	
	@GetMapping("update")
	public String todoUpdate(
		@RequestParam("todoNo") int todoNo,
		Model model
		) {
		
		// 상세조회 서비스 호출 -> 수정 화면에 출력할 이전 내용 
		Todo todo = service.todoDetail(todoNo);
		
		model.addAttribute("todo", todo);
		
		return "todo/update";
	}
	
	
	
	/** 할 일 수정
	 * @param todoNo
	 * @param todoTitle
	 * @param todoContent
	 * @param updateTodo : 커맨드 객체(전달 받은 파라미터가 자동으로 필드에 세팅된 객체)
	 * @return
	 */
	@PostMapping("update")
	public String todoUpdate(
			Todo todo,
			RedirectAttributes ra) {
		
		int result = service.todoUpdate(todo);
		
		String message = null;
		
		String path = "redirect:";
		
		if(result > 0) {
			path += "/todo/detail?todoNo=" + todo.getTodoNo();
			message = "수정 성공!!!";
			ra.addFlashAttribute("message", message);
		}
		else {
			path += "/todo/update?todoNo=" + todo.getTodoNo();
			message = "수정 실패...";
			ra.addFlashAttribute("message", message);
		}
		return path;
	}
	
	/** 완료 여부 변경
	 * @param todo : 커맨드 객체 : @ModelAttribute 생략
	 * @param ra
	 * @return "detail?todoNo=" + 할 일 번호
	 */
	@GetMapping("changeComplete")
	public String changeComplete(
			Todo todo,
			RedirectAttributes ra) {
		int result = service.todoCompleteUpdate(todo);
		
		String message = null;
				
		if(result > 0) {
			message = "수정 성공!!!";
			ra.addFlashAttribute("message", message);
		}
		else {
			message = "수정 실패...";
			ra.addFlashAttribute("message", message);
		}
		
		// 현재 요청 주소 /todo/changeComplete
		// 응답 주소 : /todo/detail
		return "redirect:detail?todoNo=" + todo.getTodoNo();
	}
	
	
}
