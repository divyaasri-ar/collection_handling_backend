package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CustomerSupportTicket;

public interface CustomerSupportTicketRepository extends JpaRepository<CustomerSupportTicket, Long> {
	CustomerSupportTicket findByCustomerId(Long customerId);
	CustomerSupportTicket deleteByCustomerId(Long customerId);

}
