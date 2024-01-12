package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.CustomerSupportTicketService;
import com.example.demo.Service.TicketService;
import com.example.demo.model.CustomerSupportTicket;
import com.example.demo.model.Ticket;
import com.example.demo.repository.CustomerSupportTicketRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerSupportTicketController {
	 private final CustomerSupportTicketRepository customerSupportTicketRepository;
	 @Autowired
	 private TicketService ticketService;
	 @Autowired
	 private CustomerSupportTicketService customerSupportTicketService;

	    public CustomerSupportTicketController(CustomerSupportTicketRepository customerSupportTicketRepository) {
	        this.customerSupportTicketRepository = customerSupportTicketRepository;
	    }


	    @PostMapping("/ticket")
	    public ResponseEntity<CustomerSupportTicket> createCustomerSupportTicket(@RequestBody CustomerSupportTicket ticket) {
	        CustomerSupportTicket savedTicket = customerSupportTicketRepository.save(ticket);
	        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
	    }
//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<Void> deleteCustomerSupportTicket(@PathVariable Long id) {
//	        customerSupportTicketService.deleteCustomerSupportTicket(id);
//	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	    }
	    
	    
	    @PostMapping("/csticketdelete/{customerId}")
	    @ResponseBody
	    public ResponseEntity<String> deleteCSTicket(@PathVariable Long customerId) {
	        // Assuming you have a PendingCustomer entity
	        CustomerSupportTicket ticket = customerSupportTicketRepository.findByCustomerId(customerId);
	        if (ticket != null) {
	        	customerSupportTicketRepository.delete(ticket); // Delete the pending customer
	        	System.out.println("Customer Details deleted successfully");
	            return ResponseEntity.ok("Pending customer deleted.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pending customer not found.");
	        }
	    }

}
