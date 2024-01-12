package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PendingCustomer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobile;
    private String email;
    private BigDecimal prevdue;
    private BigDecimal currdue;
    private BigDecimal outbill;
    private LocalDate duedate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public BigDecimal getCurrdue() {
		return currdue;
	}
	public void setCurrdue(BigDecimal currdue) {
		this.currdue = currdue;
	}
	public BigDecimal getOutbill() {
		return outbill;
	}
	public void setOutbill(BigDecimal outbill) {
		this.outbill = outbill;
	}
	public LocalDate getDuedate() {
		return duedate;
	}
	public void setDuedate(LocalDate duedate) {
		this.duedate = duedate;
	}
	@Override
	public String toString() {
		return "PendingCustomer [id=" + id + ", name=" + name + ", mobile=" + mobile + ", email=" + email + ", prevdue="
				+ prevdue + ", currdue=" + currdue + ", outbill=" + outbill + ", duedate=" + duedate + "]";
	}
	
    
    

}
