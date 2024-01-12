package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.EmailService;
import com.example.demo.Service.PendingCustomerService;
import com.example.demo.dto.PendingCustomerDto;
import com.example.demo.model.Customer;
import com.example.demo.model.Demo;
import com.example.demo.model.DemoMail;
import com.example.demo.model.PendingCustomer;
import com.example.demo.model.demoOtp;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PendingCustomerRepository;

import jakarta.mail.MessagingException;

@RestController
public class PendingCustomerController {
    @Autowired
    private PendingCustomerService customerService;
    @Autowired
	PendingCustomerRepository customerRepository;
    @Autowired
    private EmailService emailService;
    
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody   
    @GetMapping("/all")
    public List<PendingCustomer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/customers")
    public  List<PendingCustomerDto>  showCustomerList(Model model) {
    	return customerService.getAllCustomersWithDaysUnpaid();
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/send-emails")
    public ResponseEntity<DemoMail> sendEmailsToCustomersWithDueDate() {
        customerService.sendEmailToCustomersWithDueDate();
        //return ResponseEntity.ok("Emails sent successfully to customers with due dates today.");
        return new ResponseEntity<DemoMail>(new DemoMail("Emails sent successfully to customers with due dates today."),HttpStatus.OK);
        
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/email/{customerId}")
    public ResponseEntity<DemoMail> sendEmail(@PathVariable Long customerId) throws MessagingException {
        PendingCustomer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            //return ResponseEntity.badRequest().body("Customer not found with ID: " + customerId);
            return new ResponseEntity<DemoMail>(new DemoMail("Customer not found with ID: " + customerId),HttpStatus.OK);
        }

        // Send email using emailService
        String emailContent = "Dear " + customer.getName() + ",\n\n"
                + "This is a reminder that your outstanding bill of $"
                + customer.getOutbill() + " is due today. "
                + "Please make the payment as soon as possible. "
                + "Thank you for your cooperation.";
        emailService.sendEmail(customer.getEmail(), "Subject: Your Invoice", emailContent);

        //return ResponseEntity.ok("Email sent successfully to customer with ID: " + customerId);
        return new ResponseEntity<DemoMail>(new DemoMail("Email sent successfully to customer with ID:"+ customerId),HttpStatus.OK);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/delete/{customerId}")
    @ResponseBody
    public ResponseEntity<String> deletePendingCustomer(@PathVariable Long customerId) {
        // Assuming you have a PendingCustomer entity
        PendingCustomer pendingCustomer = customerRepository.findById(customerId).orElse(null);
        if (pendingCustomer != null) {
        	customerRepository.delete(pendingCustomer); // Delete the pending customer
        	System.out.println("Customer Details deleted successfully");
            return ResponseEntity.ok("Pending customer deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pending customer not found.");
        }
    }
    
    
//    public String showCustomerList(Model model) {
//        List<PendingCustomerDto> customers = customerService.getAllCustomersWithDaysUnpaid();
//        model.addAttribute("customers", customers);
//        System.out.println(customers);
//        return "customer-list";
//    }


//    @CrossOrigin(origins = "http://localhost:4200")
//    @GetMapping("/send-notifications")
//    public String sendNotifications(Model model) {
//        List<PendingCustomer> customers = customerService.getCustomersWithOverdueBills();
//        for (PendingCustomer customer : customers) {
//            // Send notifications to customers
//        	customerService.sendReminderEmail(customer, "Your payment is overdue.");
//        }
//        return "redirect:/customers/list";
//    }
//	
    
    

}
