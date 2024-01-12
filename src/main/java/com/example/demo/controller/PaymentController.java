package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.EmailService;
import com.example.demo.Service.PaymentService;
import com.example.demo.model.Customer;
import com.example.demo.model.DemoMail;
import com.example.demo.model.Installment;
import com.example.demo.model.PendingCustomer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PendingCustomerRepository;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/customers")
public class PaymentController {
	private final PaymentService paymentService;
	
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PendingCustomerRepository customerRepository;
	

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/pending")
    public List<Customer> getPendingCustomers() {
        return paymentService.getPendingCustomers();
    }
    
//    @GetMapping
//    public ResponseEntity<List<Customer>> getAllCustomers() {
//        List<Customer> customers = customerService.getAllCustomers();
//        return new ResponseEntity<>(customers, HttpStatus.OK);
//    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<Customer> getAllCustomers() {
        return paymentService.getAllCustomers();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{customerId}/outstanding-bill")
    public BigDecimal getOutstandingBill(@PathVariable Long customerId) {
    	System.out.println(customerId);
    	System.out.println(paymentService.getOutstandingBill(customerId));
        return paymentService.getOutstandingBill(customerId);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{customerId}/previous-due")
    public BigDecimal getPreviousDue(@PathVariable Long customerId) {
    	System.out.println(customerId);
    	System.out.println(paymentService.getPreviousDue(customerId));
        return paymentService.getPreviousDue(customerId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = paymentService.getCustomerById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{customerId}/process-payment")
    public void processPayment(@PathVariable Long customerId, @RequestBody BigDecimal amount) {
    	paymentService.processPayment(customerId, amount);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{customerId}/optprocess-payment")
    public void optprocessPayment(@PathVariable Long customerId, @RequestBody BigDecimal amount) {
    	paymentService.optprocessPayment(customerId, amount);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{customerId}/process")
    public ResponseEntity<Customer> processPayment(@PathVariable Long customerId) {
        Customer customer = repository.findById(customerId).orElse(null);
        if (customer != null) {
        	customer.setStatus("paid"); // Setting the 'paid' flag to true
            double outbillValue = 0.00;
            BigDecimal decimalOutbillValue = new BigDecimal(outbillValue);
            customer.setOutbill(decimalOutbillValue);
            System.out.println(decimalOutbillValue);
            repository.save(customer); // Save the updated customer entity
            System.out.println(customer);
//            customer.setCurrdue(new BigDecimal("0.00"));
//            BigDecimal currentDue = customer.getCurrdue();
//            BigDecimal previousDue = customer.getPrevdue();
//            BigDecimal outstandingBill = currentDue.add(previousDue);
//            customer.setOutbill(outstandingBill);
//
//            repository.save(customer); 
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{customerId}/optupdateprocess")
    public ResponseEntity<PendingCustomer> optupdateprocessPayment(@PathVariable Long customerId) {
        PendingCustomer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
        	
        	BigDecimal previousDue = customer.getPrevdue();
        	BigDecimal outstandingBill = customer.getOutbill().subtract(previousDue);
        	
        	double prevdueValue = 0.00;
            BigDecimal decimalOutbillValue = new BigDecimal(prevdueValue);
            customer.setPrevdue(decimalOutbillValue);
            System.out.println(decimalOutbillValue);

            customerRepository.save(customer);
            
            System.out.println(customer);

            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{customerId}/optprocess")
    public ResponseEntity<Customer> optprocessPayment(@PathVariable Long customerId) {
        Customer customer = repository.findById(customerId).orElse(null);
        if (customer != null) {
        	
        	BigDecimal previousDue = customer.getPrevdue();
        	BigDecimal outstandingBill = customer.getOutbill().subtract(previousDue);
        	
        	double prevdueValue = 0.00;
            BigDecimal decimalOutbillValue = new BigDecimal(prevdueValue);
            customer.setPrevdue(decimalOutbillValue);
            System.out.println(decimalOutbillValue);
            
            customer.setStatus("pending");

            repository.save(customer);
            
            System.out.println(customer);

            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/paymentemail/{customerId}")
    public ResponseEntity<DemoMail> sendPaymentEmail(@PathVariable Long customerId) throws MessagingException {
        System.out.println("Sending payment confirmation email");
        Customer customer = repository.findById(customerId).orElse(null);
        System.out.println(customerId);
        System.out.println("Payment Confirmation Email sent successfully");

        if (customer == null) {
            return new ResponseEntity<DemoMail>(new DemoMail("Customer not found with ID: " + customerId), HttpStatus.OK);
        }

        String emailContent = "Dear " + customer.getName() + ",\n\n" +
                "Thank you for your payment! We have received your payment of $" + customer.getOutbill() + ".\n" +
                "If you have any questions about this payment or any other inquiries, please feel free to contact our customer support.\n\n" +
                "Thank you for choosing our service!\n\n";
             

        // Assuming customer.getEmail() gets the customer's email address
        emailService.sendEmail(customer.getEmail(), "Subject: Installment Payment Confirmation", emailContent);

        return new ResponseEntity<DemoMail>(new DemoMail("Payment confirmation email sent successfully to customer with ID: " + customerId), HttpStatus.OK);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/optpaymentemail/{customerId}")
    public ResponseEntity<DemoMail> optsendPaymentEmail(@PathVariable Long customerId) throws MessagingException {
        System.out.println("Sending Late payment confirmation email");
        Customer customer = repository.findById(customerId).orElse(null);
        System.out.println(customerId);
        System.out.println("Installment Payment Confirmation Email sent successfully");

        if (customer == null) {
            return new ResponseEntity<DemoMail>(new DemoMail("Customer not found with ID: " + customerId), HttpStatus.OK);
        }

        String emailContent = "Dear " + customer.getName() + ",\n\n" +
                "Thank you for your payment! We have received your payment of $" + customer.getPrevdue() + ".\n" +
                "Your connection will be rectified soon. If you have any questions about this payment or any other inquiries, please feel free to contact our customer support.\n\n" +
                "Thank you for choosing our service!\n\n";
             

        // Assuming customer.getEmail() gets the customer's email address
        emailService.sendEmail(customer.getEmail(), "Subject: Payment Confirmation", emailContent);

        return new ResponseEntity<DemoMail>(new DemoMail("Payment confirmation email sent successfully to customer with ID: " + customerId), HttpStatus.OK);
    }

//        
//        @PostMapping("/paymentemail/{customerId}")
//        public ResponseEntity<DemoMail> sendEmail(@PathVariable Long customerId) throws MessagingException {
//            Optional<Customer> customer = paymentService.getCustomerById(customerId);
//            if (customer == null) {
//                //return ResponseEntity.badRequest().body("Customer not found with ID: " + customerId);
//                return new ResponseEntity<DemoMail>(new DemoMail("Customer not found with ID: " + customerId),HttpStatus.OK);
//            }
//
//            String emailContent = "Thank you for your payment!\n\n" +
//                    "Installment Amount: Rs." + customer.get + "\n" +
//                    "Installment Duration: " + customer.getDuration() + " split\n" +
//                    "Paid Amount: $" + customer. + "\n" +
//                    "Outstanding Balance: $" + customer.getOutbill() + "\n\n" +
//                    "If you have any questions, please contact our support team.";
//
//
//            emailService.sendEmail(customer.getEmail(), "Subject: Installment Payment Confirmation", emailContent);
//       
//            return new ResponseEntity<DemoMail>(new DemoMail("Email sent successfully to customer with ID:"+ customerId),HttpStatus.OK);
//        }
        
    

    
//    @CrossOrigin(origins = "http://localhost:4200")
//    @PostMapping("/{customerId}/process")
//    public ResponseEntity<Customer> processPayment(@PathVariable Long customerId) {
//        // Perform payment processing logic
//        
//        // Assuming payment is successful, update the customer's status
//        Customer customer = repository.findById(customerId).orElse(null);
//        if (customer != null) {
//            customer.setStatus("paid"); // Setting the 'paid' flag to true
//            double outbillValue = 0.00;
//            BigDecimal decimalOutbillValue = new BigDecimal(outbillValue);
//            customer.setOutbill(decimalOutbillValue);
//            System.out.println(decimalOutbillValue);
//            repository.save(customer); // Save the updated customer entity
//            System.out.println(customer);
//                return ResponseEntity.ok(customer);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//    }

}
