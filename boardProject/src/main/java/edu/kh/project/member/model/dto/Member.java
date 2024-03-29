package edu.kh.project.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter	// Spring EL, MyBatis
@Setter // 커맨드 객체
@NoArgsConstructor
@ToString
public class Member {
	
   private int 		memberNo;
   private String 	memberEmail;
   private String 	memberPw;
   private String 	memberNickname;
   private String 	memberTel;
   private String 	memberAddress;
   private String 	profileImg;
   private String 	enrollDate;
   private String 	memberDelFl;
   private int 		authority; 
   
}
