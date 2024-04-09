package com.kh.test.customer.model.service;

import org.springframework.stereotype.Service;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.mapper.CustomerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomeServiceImpl implements CustomerService{
	private final CustomerMapper mapper;
	@Override
	public int insertCustomer(Customer customer) {
		return mapper.insertCustomer(customer);
	}
}
