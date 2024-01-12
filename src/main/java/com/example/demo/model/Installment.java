package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Installment {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 private Long customerId;
	 private String name;
	 private String mobile;
	    private String email;
	    private BigDecimal prevdue;
	    private BigDecimal installmentAmount;
	    //private BigDecimal paidAmount;
	    //private double amount;
	    private int duration;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public BigDecimal getInstallmentAmount() {
			return installmentAmount;
		}
		public void setInstallmentAmount(BigDecimal installmentAmount) {
			this.installmentAmount = installmentAmount;
		}
//		public BigDecimal getPaidAmount() {
//			return paidAmount;
//		}
//		public void setPaidAmount(BigDecimal paidAmount) {
//			this.paidAmount = paidAmount;
//		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		
		public BigDecimal getPrevdue() {
			return prevdue;
		}
		public void setPrevdue(BigDecimal prevdue) {
			this.prevdue = prevdue;
		}
		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
		public Long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}
		
		
		
	    
		

}
