package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 객체 자동 생성
@Controller // 요청/응답 제어 역할 명시 + Bean 등록
public class MainController {
	
	@Autowired // 등록된 Bean 중 같은 타입 or 상속 관계
	private TodoService service;
	
	@RequestMapping("/")
	public String mainPage(Model model) {
		log.debug("service : " + service);
		
		Map<String, Object> map = service.selectAll();
		
		// map에 담긴 내용 추출
		List<Todo> todoList = (List<Todo>) map.get("todoList");
		model.addAttribute("todoList", todoList);
		
		int completeCount = (int)map.get("completeCount");
		model.addAttribute("completeCount", completeCount);

		// classpath:/templates/common/main.html로 forward
		return "common/main";
	}
}
