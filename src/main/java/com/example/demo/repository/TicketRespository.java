package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Ticket;
import java.util.List;


public interface TicketRespository extends JpaRepository<Ticket, Long> {
	
	Ticket findByCustomerId(Long customerId);

	Ticket deleteByCustomerId(Long customerId);
}
