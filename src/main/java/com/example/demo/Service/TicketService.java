package com.example.demo.Service;


import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Installment;
import com.example.demo.model.PendingCustomer;
import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRespository;

import io.jsonwebtoken.io.IOException;

@Service
public class TicketService {
	 @Autowired
	    private TicketRespository ticketRepository;
	 @Autowired
	    private JavaMailSender javaMailSender;
		@Autowired
		private  EmailService emailService;
		
		
		
	public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
	public Ticket getCustomerById(Long customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }
	
	public boolean doesCustomerExist(Long customerId) {
	    Ticket ticket = ticketRepository.findByCustomerId(customerId);
	    return ticket != null;
	}	
	
	public void deleteTicketByCustomerId(Long customerId) {
        ticketRepository.deleteByCustomerId(customerId);
    }


}
