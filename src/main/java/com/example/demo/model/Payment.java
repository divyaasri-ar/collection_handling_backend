package com.example.demo.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Component
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	    private Long customerId; 
	    private String paymentMethod; 
	    private BigDecimal amount;
	    private String paynumber;
	    private String email;
	    
		public Long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}
		public String getPaymentMethod() {
			return paymentMethod;
		}
		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public String getPaynumber() {
			return paynumber;
		}
		public void setPaynumber(String paynumber) {
			this.paynumber = paynumber;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		
	}

