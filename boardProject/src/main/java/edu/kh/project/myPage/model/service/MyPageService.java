package edu.kh.project.myPage.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;

public interface MyPageService {

	/** 회원 정보 수정
	 * @param inputMember
	 * @param memberAddress
	 * @return result
	 */
	int updateInfo(Member inputMember, String[] memberAddress);

	/** 현재 비밀번호 찾기
	 * @param memberNo
	 * @param inputMember
	 * @return memberNo(현재 세션에 올라가있는 놈), (
	 */
	int FindCurrentPw(int memberNo, String currentPw,String newPw, String newPwConfirm);

	/** 회원 탈퇴
	 * @param memberNo
	 * @param memberPw
	 * @return
	 */
	int userWithdrawal(int memberNo, String memberPw);

	String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException;
}
