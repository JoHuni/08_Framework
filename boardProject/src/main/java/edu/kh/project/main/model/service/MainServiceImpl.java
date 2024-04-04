package edu.kh.project.main.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import edu.kh.project.main.model.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{
	private final MainMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	@Override
	public int resetPw(int inputNo) {
		String pw = "123";

		String encPw = bcrypt.encode(pw);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("inputNo", inputNo);
		map.put("encPw", encPw);
		
		return mapper.resetPw(map);
	}
	
	@Override
	public int restoreMember(String memNo) {
		return mapper.restoreMember(memNo);
	}
	
	@Override
	public int deleteMember(String input) {
		return mapper.deleteMember(input);
	}
}
