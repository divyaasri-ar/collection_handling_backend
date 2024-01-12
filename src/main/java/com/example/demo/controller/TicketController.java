package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.EmailService;
import com.example.demo.Service.TicketService;
import com.example.demo.model.Customer;
import com.example.demo.model.DemoMail;
import com.example.demo.model.PendingCustomer;
import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRespository;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {
	
	@Autowired
    private TicketService ticketService;
	@Autowired
    private EmailService emailService;
	@Autowired
	private TicketRespository ticketRespository;
	
	@PostMapping("/api/ticket")
    public Ticket raiseTicket(@RequestBody Ticket ticket) {
		System.out.println("Ticket Saved successfully");
        return ticketService.saveTicket(ticket);
    }
	
    @ResponseBody
    
    @GetMapping("/all/ticket")
    public List<Ticket> getAllCustomers() {
        return ticketRespository.findAll();
    }
	
	@PostMapping("/ticketemail/{customerId}")
    public ResponseEntity<DemoMail> sendEmail(@PathVariable Long customerId) throws MessagingException {
		System.out.println("Raising ticket");
        Ticket customer = ticketService.getCustomerById(customerId);
        System.out.println(customerId);
        System.out.println("Email sent succeesfully");
        if (customer == null) {
            return new ResponseEntity<DemoMail>(new DemoMail("Customer not found with ID: " + customerId),HttpStatus.OK);
        }

        String emailContent = "Thank you for reporting the issue!\n\n" +
                "Issue Type: " + customer.getSelectedIssue() + "\n" +
                "Other Issue: " + customer.getOtherIssue() + "\n" +
                "We have received your ticket and our support team will assist you shortly.\n\n" +
              "If you have any additional information, please feel free to reply to this email.";


        // Assuming customerTicket.getEmail() gets the customer's email address
        emailService.sendEmail(customer.getEmail(), "Subject: Issue Reported Confirmation", emailContent);

        return new ResponseEntity<DemoMail>(new DemoMail("Email sent successfully to customer with ID:"+ customerId),HttpStatus.OK);
    }
	
//	@PostMapping("/ticketdelete/{customerId}")
//    @ResponseBody
//    public ResponseEntity<String> deletePendingCustomer(@PathVariable Long customerId) {
//        // Assuming you have a PendingCustomer entity
//        Ticket ticket = ticketService.getCustomerById(customerId);
//        if (ticket != null) {
//        	ticketRespository.delete(ticket); // Delete the pending customer
//        	System.out.println("Customer Details deleted successfully in ticket");
//            return ResponseEntity.ok("raised ticket customer deleted.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Raised ticket customer not found.");
//        }
//    }
//	
//	@GetMapping("/checkCustomer/{customerId}")
//    public ResponseEntity<String> checkCustomerExistence(@PathVariable Long customerId) {
//        boolean customerExists = ticketService.doesCustomerExist(customerId);
//
//        if (customerExists) {
//            return ResponseEntity.ok("Customer exists in the ticket database.");
//        } else {
//            return ResponseEntity.status(404).body("Customer not found in the ticket database.");
//        }
//    }
	
	 @CrossOrigin(origins = "http://localhost:4200")
	    @PostMapping("/ticketdelete/{customerId}")
	    @ResponseBody
	    public ResponseEntity<String> deleteTicket(@PathVariable Long customerId) {
	        // Assuming you have a PendingCustomer entity
	        Ticket ticket = ticketRespository.findByCustomerId(customerId);
	        if (ticket != null) {
	        	ticketRespository.delete(ticket); // Delete the pending customer
	        	System.out.println("Customer Details deleted successfully");
	            return ResponseEntity.ok("Pending customer deleted.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pending customer not found.");
	        }
	    }
    
}

