package com.kh.test.user.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserMapper mapper;
	@Override
	public List<User> userSearch(String inputId) {
		return mapper.userSearch(inputId);
	}
}
