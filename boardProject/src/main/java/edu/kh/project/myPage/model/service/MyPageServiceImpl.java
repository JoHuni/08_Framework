package edu.kh.project.myPage.model.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;

	
	// @RequiredArgsConstructor 를 이용했을 때 자동 완성 되는 구문
//	@Autowired
//	public MyPageServiceImpl(MyPageMapper mapper) {
//		this.mapper = mapper;
//	}
	
	// 회원 정보 수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		// 입력된 주소가 있을 경우 
		// memberAddress를 A^^^B^^^C 형태로 가공
		
		// 주소 입력 X -> inputMember.getMemberAddress()  -> ",,"
		if( inputMember.getMemberAddress().equals(",,") ) {
			
			// 주소에 null 대입
			inputMember.setMemberAddress(null);
		
		} else { 
			//  memberAddress를 A^^^B^^^C 형태로 가공
			String address = String.join("^^^", memberAddress);
			
			// 주소에 가공된 데이터 대입
			inputMember.setMemberAddress(address);
		}
		
		// SQL 수행 후 결과 반환
		return mapper.updateInfo(inputMember);
	}
	
	@Override
	public int FindCurrentPw(int memberNo, String currentPw, String newPw,
			String newPwConfirm) {
		
		String result1 = mapper.FindCurrentPw(memberNo);
		
		
		if(bcrypt.matches(currentPw, result1) && newPw.equals(newPwConfirm)) {
		
			String newPw2 = bcrypt.encode(newPw);
			
			Member member = new Member();
			member.setMemberNo(memberNo);
			member.setMemberPw(newPw2);
			
			return mapper.changePassword(member);	
		}
		return 0;
	}
	
	@Override
	public int userWithdrawal(int memberNo, String memberPw) {
		String result = mapper.FindCurrentPw(memberNo);
		
		if(bcrypt.matches(memberPw, result)) {
			return mapper.userWithdrawal(memberNo);
		}
		return 0;
	}
	
	@Override
	public String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException {
		// MultipartFile이 제공하는 메서드
		// - getSize() : 파일크기
		// - isEmpty() : 업로드한 파일이 없을 경우 true
		// - getOriginalFileName() : 원본 파일명
		// - transferTo(경로) :
		//	 메모리 또는 임시 저장 경로에 업로드된 파일을
		//	 원하는 경로에 전송*서버 어떤 폴더에 저장할 지 지정)
		
		if(uploadFile.isEmpty()) {
			 return null;
		}
		
		// 업로드한 파일이 있을 경우
		uploadFile.transferTo(new File("C:\\uploadFiles\\test\\" + uploadFile.getOriginalFilename()));
		
		// 웹에서 해당 파일에 접근할 수 있는 경로를 반환
		// 웹 접근 주소 : /myPage/file/a.jpg
		return "/myPage/file/" + uploadFile.getOriginalFilename();
	}
}





