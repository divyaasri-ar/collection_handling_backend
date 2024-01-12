package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerSupportTicket;
import com.example.demo.repository.CustomerSupportTicketRepository;

import jakarta.persistence.EntityNotFoundException;

@Service

public class CustomerSupportTicketService {
	
	 private final CustomerSupportTicketRepository customerSupportTicketRepository;
	    private final TicketService ticketService;
	    @Autowired
	    public CustomerSupportTicketService(CustomerSupportTicketRepository customerSupportTicketRepository, TicketService ticketService) {
	        this.customerSupportTicketRepository = customerSupportTicketRepository;
	        this.ticketService = ticketService;
	    }

//	    public void deleteCustomerSupportTicket(Long ticketId) {
//	        customerSupportTicketRepository.deleteByCustomerId(ticketId);
//	    }
	 
	 public void deleteCustomerSupportTicket(Long ticketId) {
		    Optional<CustomerSupportTicket> optionalTicket = Optional.ofNullable(customerSupportTicketRepository.findByCustomerId(ticketId));
		    System.out.println(optionalTicket);
		    if (optionalTicket.isPresent()) {
		        CustomerSupportTicket ticket = optionalTicket.get();
		        Long customerId = ticket.getCustomerId();
		        customerSupportTicketRepository.deleteById(ticketId);
		        ticketService.deleteTicketByCustomerId(customerId);
		    } else {
		        throw new EntityNotFoundException("Ticket not found");
		    }
		}

	    

}
