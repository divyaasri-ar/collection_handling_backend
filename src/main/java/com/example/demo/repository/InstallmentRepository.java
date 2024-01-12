package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Installment;
import com.example.demo.model.Ticket;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
	
	Installment findByCustomerId(Long customerId);

}
