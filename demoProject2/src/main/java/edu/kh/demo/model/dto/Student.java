package edu.kh.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Spring EL 같은 경우 GETTER가 꼭 필수로 작성 되어있어야 함
// -> ${Student.getName()} == ${Student.name}
// getter 대신 필드명 호출하는 형식으로 작성하는데

// 자동으로 getter 호출
@Getter // 컴파일시 getter 자동생성
@Setter // 컴파일시 setter 자동생성
@ToString // 컴파일시 toString 자동생성

@NoArgsConstructor // 매개변수 없는 생성자(== 기본 생성자)
@AllArgsConstructor // 모든 필드를 초기화하는 용도의 매개변수
public class Student {
	private String sutdentNo; // 학번
	private String name;
	private int age;
}
