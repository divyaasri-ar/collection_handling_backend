package com.example.demo.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PendingCustomerDto;
import com.example.demo.model.PendingCustomer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PendingCustomerRepository;

import jakarta.mail.MessagingException;

@Service
public class PendingCustomerService {
	@Autowired
    private PendingCustomerRepository repository;
	@Autowired
    private JavaMailSender javaMailSender;
	@Autowired
	private  EmailService emailService;
	public List<PendingCustomerDto> getAllCustomersWithDaysUnpaid() {
        List<PendingCustomer> customers = repository.findAll();
        List<PendingCustomerDto> customerDTOs = new ArrayList<>();
       

        

        for (PendingCustomer customer : customers) {
        	PendingCustomerDto customerDTO = new PendingCustomerDto();
        	customerDTO.setId(customer.getId());
        	customerDTO.setName(customer.getName());
        	customerDTO.setMobile(customer.getMobile());
        	customerDTO.setEmail(customer.getEmail());
        	customerDTO.setPrevdue(customer.getPrevdue());
        	customerDTO.setCurrdue(customer.getCurrdue());
        	customerDTO.setOutbill(customer.getOutbill());
        	customerDTO.setDuedate(customer.getDuedate());
        	customerDTO.calculateDaysUnpaid(); 
        	customerDTOs.add(customerDTO);
        }

        return customerDTOs;
	}

    public List<PendingCustomer> getAllCustomers() {
        return repository.findAll();
    }

    public List<PendingCustomer> getCustomersWithOverdueBills() {
        LocalDate currentDate = LocalDate.now();
        return repository.findByDuedateBefore(currentDate);
    }

    public void sendEmailToCustomersWithDueDate() {
        LocalDate today = LocalDate.now();
        List<PendingCustomer> customers = repository.findByDuedate(today);

        for (PendingCustomer customer : customers) {
            String subject = "Payment Reminder";
            String message = "Dear " + customer.getName() + ",\n\n"
                             + "This is a reminder that your outstanding bill of $"
                             + customer.getOutbill() + " is due today. "
                             + "Please make the payment as soon as possible. "
                             + "Thank you for your cooperation.";

            sendEmail(customer.getEmail(), subject, message);
        }
    }
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
    public PendingCustomer getCustomerById(Long customerId) {
        return repository.findById(customerId).orElse(null);
    }
    
    public List<PendingCustomer> getCustomersWithDueDateToday() {
        LocalDate today = LocalDate.now();
        return repository.findByDuedate(today);
    }

    public List<PendingCustomer> getCustomersWithDueDate(LocalDate dueDate) {
        return repository.findByDuedate(dueDate);
    }

//    public List<PendingCustomer> getCustomersWithDueDateBefore(LocalDate dueDate) {
//        return repository.findByDueDateBefore(dueDate);
//    }
//
//    public List<PendingCustomer> getCustomersWithDueDateAfter(LocalDate dueDate) {
//        return repository.findByDueDateAfter(dueDate);
//    }
    public void sendReminderEmails(List<PendingCustomer> customers, String message) {
        for (PendingCustomer customer : customers) {
            try {
                emailService.sendEmail(customer.getEmail(), "Payment Reminder", message);
            } catch (MessagingException e) {
                // Handle email sending exception (log or rethrow if necessary)
                e.printStackTrace();
            }
        }
    }
    public void sendReminderEmail(PendingCustomer customer, String message) {
     
    }

    public void sendSecondReminder(PendingCustomer customer, String message) {
       
    }

    public void terminateConnection(PendingCustomer customer) {
        
    }

    public void moveCustomerToTerminatedDatabase(PendingCustomer customer) {
        
    }

//    public PendingCustomer getCustomerById(Long customerId) {
//        return repository.findById(customerId)
//                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
//    }

    public void updateCustomer(PendingCustomer customer) {
        
    }

    public void deleteCustomerById(Long customerId) {
    	repository.deleteById(customerId);
    }
}

