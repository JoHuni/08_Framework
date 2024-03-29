package edu.kh.project.member.model.service;

import edu.kh.project.member.model.dto.Member;

public interface MemberSerivce {

	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 회원 가입 서비스
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int signup(Member inputMember, String[] memberAddress);

	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return 중복시 1, 아닐시 0
	 */
	int checkEmail(String memberEmail);

	/** 닉네임 중복 검사
	 * @param memberNickname
	 * @return count
	 */
	int checkNickname(String memberNickname);

}