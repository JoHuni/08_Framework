package edu.kh.project.main.model.service;

public interface MainService {

	/** 비밀번호 초기화
	 * @param inputNo
	 * @return result
	 */
	int resetPw(int inputNo);

	/** 탈퇴 처리 취소
	 * @param memNo
	 * @return result
	 */
	int restoreMember(String memNo);

	/** 회원 삭제
	 * @param input
	 * @return result
	 */
	int deleteMember(String input);

}
