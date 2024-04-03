package edu.kh.project.myPage.model.service;

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
}
