package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberSerivce;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/* @SessionAttributes({"key", "key", ...})
 * 
 * - Model에 추가된 속성(Attribute) 중
 * 	 key 값이 일치하는 속성을 session scope로 변경
 */

// 아이디 저장(Cookie)
/* Cookie란?
 * - 클라이언트 측(브라우저)에서 관리하는 데이터(파일 형식)
 * 
 * - Cookie에는 만료기간, 데이터(key=value), 사용하는 사이트(주소)
 *  가 기록되어 있음
 *  
 * - 클라이언트가 쿠키에 기록된 사이트로 요청을 보낼 때
 *   요청에 쿠키가 담겨져서 서버로 넘어감
 *   
 * - Cookie의 생성, 수정, 삭제는 Server가 관리
 *   저장은 Client가 함
 *   
 * - Cookie는 HttpServletResponse를 이용해서 생성,
 *   클라이언트에게 전달(응답)할 수 있다.
 * 
 * /
 */
@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("member") 
@Slf4j
public class MemberController {

	@Autowired // 의존성 주입
	private MemberSerivce service;
	/* [로그인] 
	 *  - 특정 사이트에 아이디/비밀번호 등을 입력해서
	 *    해당 정보가 있으면 조회/서비스 이용
	 *    
	 *  - 로그인한 정보를 session에 기록하여
	 *    로그아웃 또는 브라우저 종료 시 까지
	 *    해당 정보를 계속 이용할 수 있게 함
	 * */
	
	
	// 커맨드 객체
	// - 요청 시 전달 받은 파라미터를
	// 같은 이름의 필드에 세팅한 객체
	/** 로그인
	 * @param inputMember : 커맨드 객체 (@ModelAttribute 생략)
	 * 									(memberEmail, memberPw 세팅된 상태)
	 * @param ra : 리다이렉트시 request scope로 데이터를 전달하는 객체
	 * @param model : 데이터 전달용 객체(request scope)
	 * @param saveId : 아이디 저장 체크 여부
	 * @param resp : 쿠키 생성, 추가를 위해
	 * @return "redirect:/";
	 */
	@PostMapping("login")
	public String login(@ModelAttribute Member inputMember,
			RedirectAttributes ra,
			Model model,
			@RequestParam(value = "saveId", required = false) String saveId,
			HttpServletResponse resp){
		log.debug(saveId);
		// 체크박스에 value가 없을 때
		// - 체크가 된 경우 : "on"(null 아님)
		// - 체크가 안 된 경우 : null
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember);
		
		// 로그인 실패시
		if(loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		// 로그인 성공시
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);
			
			// 1단계 : request scope에 세팅됨
			// 2단계 : 클래스 위에 @SessionAttributes() 어노테이션 떄문에
			//		   session scope로 이동됨
			
			/**************************************************************************************/
			// 아이디 저장(Cookie)
			
			// 쿠키 객체 생성(K:V)
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			
			// 클라이언트가 어떤 요청을 할 때 쿠키가 첨부될 지 지정
			
			// ex) "/" : IP 또는 도메인 또는 localhost 뒤에 "/" --> 메인페이지 + 그 하위 주소들
			cookie.setPath("/");
			
			// 만료 기간 지정
			if(saveId != null) { // 아이디 저장 체크 시
				cookie.setMaxAge(30 * 24 * 60 * 60); // 초 단위로 지정
			}
			else { // 미체크 시
				cookie.setMaxAge(0); // 0초 (클라이언트 쿠키 삭제)
			}
			
			// 응답 객체에 쿠키 추가
			resp.addCookie(cookie);
			/**************************************************************************************/

			
		}
		return "redirect:/";
	}
	
	/** 로그아웃 : Session 저장된 로그인된 회원 정보를 없앰(만료, 무효화, 완료)
	 * 
	 * @param SessionStatus : 세션을 완료(없앰)시키는 역할의 객체
	 * 		- @SessionAttributes 로 등록된 세션을 완료
	 * 		- 기존 세션이 사라짐과 동시에 새로운 세션 객체가 생성
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
	@GetMapping("login")
	public String loginPage() {
		return "member/login";
	}
	
	@GetMapping("signup")
	public String signupPage() {
		return "member/signup";
	}
	
	/**
	 * @param inputMember : 입력된 회원 정보
	 * 		  (memberEmail, memberPw, memberNickname, memberTel)
	 * @param memberAddress : 입력한 주소 input 3개의 값을 배열로 전달
	 * @param ra : 리다이렉트시 request scope로 잠깐 전달
	 * @return
	 */
	@PostMapping("signup")
	public String signup(
			Member inputMember,
			@RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra
			){
		
		// 회원 가입 서비스 호출
		int result = service.signup(inputMember, memberAddress);
		
		String path = null;
		String message = null;
		
		if(result > 0){
			message = inputMember.getMemberNickname() + "님의 가입을 환영합니다😀";
			
			path = "/";
		}
		else {
			message = "회원 가입 실패...";
			
			path = "signup";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return 중복시 1, 아니면 0
	 */
	@ResponseBody // 요청한 (fetch)로 값 돌려보냄
	@GetMapping("checkEmail")
	public int checkEmail(
			@RequestParam("memberEmail") String memberEmail) {
		return service.checkEmail(memberEmail);
	}
	
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(
			@RequestParam("memberNickname") String memberNickname) {
		return service.checkNickname(memberNickname);
	}
}





