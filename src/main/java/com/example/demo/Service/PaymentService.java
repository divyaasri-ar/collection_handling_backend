package com.example.demo.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.model.Installment;
import com.example.demo.model.PendingCustomer;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.PendingCustomerRepository;

@Service
public class PaymentService {
	private final PaymentRepository paymentRepository;
	@Autowired
	private  PendingCustomerRepository repository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Customer> getPendingCustomers() {
        return paymentRepository.findByStatus("Unpaid");
    }

    public List<Customer> getAllCustomers() {
        return paymentRepository.findAll();
    }
    
    public BigDecimal getOutstandingBill(Long customerId) {
        Optional<Customer> customerOptional = paymentRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            System.out.println(customer);
            return customer.getOutbill();
        }
        return BigDecimal.ZERO; // Return zero if the customer is not found
    }
    public BigDecimal getPreviousDue(Long customerId) {
        Optional<Customer> customerOptional = paymentRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            System.out.println(customer);
            return customer.getPrevdue();
        }
        return BigDecimal.ZERO; // Return zero if the customer is not found
    }
    
    public Optional<Customer> getCustomerById(Long customerId) {
        return paymentRepository.findById(customerId);
    }

    public void processPayment(Long customerId, BigDecimal amount) {
        Optional<Customer> customerOptional = paymentRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            BigDecimal outstandingBill = customer.getOutbill();
            if (outstandingBill.compareTo(amount) >= 0) {
                // Process payment logic here, update outstanding bill and customer status
                customer.setOutbill(outstandingBill.subtract(amount));
                customer.setStatus("paid");
                paymentRepository.save(customer);
            } else {
                throw new RuntimeException("Payment amount exceeds outstanding bill.");
            }
        } else {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
    }
    
    public void optprocessPayment(Long customerId, BigDecimal amount) {
        Optional<Customer> customerOptional = paymentRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            BigDecimal previousDue = customer.getPrevdue();
            if (previousDue.compareTo(amount) >= 0) {
                // Process payment logic here, update outstanding bill and customer status
                customer.setPrevdue(previousDue.subtract(amount));
                customer.setStatus("pending");
                paymentRepository.save(customer);
            } else {
                throw new RuntimeException("Payment amount exceeds previous bill.");
            }
        } else {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
    }
//    public PendingCustomer getCustomerById(Long customerId) {
//        return repository.findById(customerId).orElse(null);
//    }

}
