package edu.kh.project.main.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	/** 비밀번호 초기화
	 * @param map 
	 * @return result
	 */
	int resetPw(Map<String, Object> map);

	/** 탈퇴 취소
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
