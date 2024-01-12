package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Customer findByMobile(String mobile);
	boolean existsByMobile(String mobile);
	//Customer findByPhoneNumber(String phoneNumber);

}
