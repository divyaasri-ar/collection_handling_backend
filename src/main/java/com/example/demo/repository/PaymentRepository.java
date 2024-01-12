package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface PaymentRepository extends JpaRepository<Customer, Long>{
	List<Customer> findByStatus(String status);

}
