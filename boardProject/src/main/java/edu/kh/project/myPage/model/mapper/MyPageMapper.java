package edu.kh.project.myPage.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	/** 회원 정보 수정
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Member inputMember);

	/** 현재 비밀번호 가져오기(암호화 된)
	 * @param memberNo
	 * @return
	 */
	String FindCurrentPw(int memberNo);

	/** 비번 바꾸기
	 * @param memberNo
	 * @param newPw
	 * @return
	 */
	int changePassword(Member inputMember);

}
