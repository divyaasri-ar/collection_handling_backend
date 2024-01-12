package com.example.demo.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.model.Installment;
import com.example.demo.model.PendingCustomer;
import com.example.demo.model.Ticket;
import com.example.demo.repository.InstallmentRepository;
import com.example.demo.repository.PendingCustomerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InstallmentService {
    private final InstallmentRepository installmentRepository;
    private final PendingCustomerRepository customerRepository;
	@Autowired
    private JavaMailSender javaMailSender;
	@Autowired
	private  EmailService emailService;

    @Autowired
    public InstallmentService(InstallmentRepository installmentRepository, PendingCustomerRepository customerRepository) {
        this.installmentRepository = installmentRepository;
        this.customerRepository = customerRepository;
    }

    public Installment saveInstallment(Installment installment) {
        return installmentRepository.save(installment);
    }
    
    public List<BigDecimal> calculateInstallmentAmounts(int duration, BigDecimal outstandingBill, Long id) {
        PendingCustomer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));
        System.out.println("calculate installment amount");
        
        BigDecimal totalAmountValue = outstandingBill;
        BigDecimal installmentAmount = totalAmountValue.divide(BigDecimal.valueOf(duration), 2, RoundingMode.HALF_UP);
        
        List<BigDecimal> installmentAmounts = new ArrayList<>();
        for (int i = 0; i < duration; i++) {
            installmentAmounts.add(installmentAmount);
        }
        
        // Print the installment amount once
        System.out.println(installmentAmount);
        
        return installmentAmounts;
    }

    public Installment getCustomerById(Long customerId) {
        return installmentRepository.findById(customerId).orElse(null);
    }
    public Installment getCustomerByCId(Long customerId) {
        return installmentRepository.findByCustomerId(customerId);
    }
    

//    public List<BigDecimal> calculateInstallmentAmounts(int duration, BigDecimal outstandingBill, Long id) {
//        PendingCustomer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));
//        System.out.println("calculate installment amount");
//        BigDecimal totalAmountValue = outstandingBill; // Declare and initialize totalAmount variable
//
//        BigDecimal installmentAmount = totalAmountValue.divide(BigDecimal.valueOf(duration), 2, RoundingMode.HALF_UP);
//
//        List<BigDecimal> installmentAmounts = new ArrayList<>();
//        for (int i = 0; i < duration; i++)
//        {
//            installmentAmounts.add(installmentAmount);
//        }
//        
//        System.out.println(installmentAmount);
//        return installmentAmounts;
//    }
}

    
//    public List<Double> calculateInstallmentAmounts(int duration, double totalAmount) {
//        // Calculate installment amounts based on duration and totalAmount
//        // Implement your logic here to split the totalAmount into installments
//        // For example, if you want to split equally:
//        double installmentAmount = totalAmount / duration;
//        List<Double> installmentAmounts = new ArrayList<>();
//        for (int i = 0; i < duration; i++) {
//            installmentAmounts.add(installmentAmount);
//        }
//        return installmentAmounts;
  //  }

