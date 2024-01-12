package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.EmailService;
import com.example.demo.Service.InstallmentService;
import com.example.demo.model.DemoMail;
import com.example.demo.model.Installment;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InstallmentController {

    private final InstallmentService installmentService;
    @Autowired
    private EmailService emailService;

    @Autowired
    public InstallmentController(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @PostMapping("/api/installments")
    public Installment createInstallment(@RequestBody Installment installment) {
        return installmentService.saveInstallment(installment);
    }

    @PostMapping("/calculate-installment")
    public ResponseEntity<List<BigDecimal>> calculateInstallmentAmounts(@RequestBody Installment request) {
    	System.out.println("111111111111");
    	System.out.println(request);
        int duration = request.getDuration();
        BigDecimal totalAmount = request.getPrevdue();
        Long id = request.getId();
        System.out.println(duration);
        System.out.println(totalAmount);
        if (totalAmount != null) {
            List<BigDecimal> installmentAmounts = installmentService.calculateInstallmentAmounts(duration, totalAmount, id);
            System.out.println("controller" + installmentAmounts);
            return ResponseEntity.ok(installmentAmounts);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/pendingemail/{customerId}")
    public ResponseEntity<DemoMail> sendEmail(@PathVariable Long customerId) throws MessagingException {
        Installment customer = installmentService.getCustomerByCId(customerId);
        if (customer == null) {
            //return ResponseEntity.badRequest().body("Customer not found with ID: " + customerId);
            return new ResponseEntity<DemoMail>(new DemoMail("Customer not found with ID: " + customerId),HttpStatus.OK);
        }

        String emailContent = "Thank you for your payment!\n\n" +
                "Installment Amount: Rs." + customer.getInstallmentAmount() + "\n" +
                "Installment Duration: " + customer.getDuration() + " split\n" +
                "Paid Amount: $" + customer.getInstallmentAmount() + "\n" +
                "Outstanding Balance: $" + customer.getPrevdue() + "\n\n" +
                "If you have any questions, please contact our support team.";

        emailService.sendEmail(customer.getEmail(), "Subject: Installment Payment Confirmation", emailContent);
       
        return new ResponseEntity<DemoMail>(new DemoMail("Email sent successfully to customer with ID:"+ customerId),HttpStatus.OK);
    }
    
    
    
//    @PostMapping("/calculate-installment")
//    public ResponseEntity<List<BigDecimal>> calculateInstallmentAmounts(@RequestBody Map<String, Object> request) {
//        int duration = (int) request.get("duration");
//        BigDecimal totalAmount = (BigDecimal) request.get("totalAmount");
//        Long id = Long.parseLong(request.get("id").toString());
//
//        if (totalAmount != null) {
//            List<BigDecimal> installmentAmounts = installmentService.calculateInstallmentAmounts(duration, totalAmount, id);
//            System.out.println("controller"+installmentAmounts);
//            return ResponseEntity.ok(installmentAmounts);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    public ResponseEntity<List<BigDecimal>> calculateInstallmentAmounts(@RequestBody Map<String, Object> request) {
//        int duration = (int) request.get("duration");
//        //BigDecimal totalAmount = new BigDecimal(request.get("totalAmount").toString());
//        Object totalAmountObj = request.get("totalAmount");
//        if (totalAmountObj != null) {
//            BigDecimal totalAmount = new BigDecimal(totalAmountObj.toString());
//        } else {
//            System.out.println("Nul value");
//        }
//        Long id = Long.parseLong(request.get("id").toString());
//        List<BigDecimal> installmentAmounts = installmentService.calculateInstallmentAmounts(duration, totalAmount, id);
//        return ResponseEntity.ok(installmentAmounts);
//    }
}
