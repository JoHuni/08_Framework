package edu.kh.project.myPage.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;

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

	/** 회원 탈퇴
	 * @param memberNo
	 * @return
	 */
	int userWithdrawal(int memberNo);

	/** 파일 정보를 DB에 삽입
	 * @param uf
	 * @return
	 */
	int insertUploadFile(UploadFile uf);

	List<UploadFile> fileList();

	/** 프사 변경
	 * @param mem
	 * @return result
	 */
	int profile(Member mem);

}
