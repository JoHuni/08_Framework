package com.kh.test.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerService service;
	
	@PostMapping("insertCustomer")
	public String insertCustomer(
			Customer customer,
			Model model) {
		int result = service.insertCustomer(customer);
		String customerName = customer.getCustomerName();
		if(result > 0) {
			model.addAttribute("customerName", customerName);
			return "result";
		}
		return "result";
	}
}
