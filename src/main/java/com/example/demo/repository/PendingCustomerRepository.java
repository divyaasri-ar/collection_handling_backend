package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PendingCustomer;

public interface PendingCustomerRepository extends JpaRepository<PendingCustomer, Long>{
	
	Optional<PendingCustomer> findById(Long id);
	//Optional<PendingCustomer> findById(BigDecimal totalAmount2);

	 //List<PendingCustomer> findByDueDateBefore(LocalDate currentDate);
	 List<PendingCustomer> findByDuedate(LocalDate dueDate);
	 List<PendingCustomer> findByDuedateBetween(LocalDate startDate, LocalDate endDate);
	// List<PendingCustomer> findByDueDateBetween(LocalDate dueDate,LocalDate dueDate2);
	 List<PendingCustomer> findByDuedateBefore(LocalDate dueDate);
	    List<PendingCustomer> findByDuedateAfter(LocalDate dueDate);
	    //List<Customer> findByDueDate(LocalDate dueDate);
	

}
