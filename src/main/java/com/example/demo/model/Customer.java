package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Component
public class Customer {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String mobile;
    private String email;
    private Date duedate;
    private BigDecimal prevdue;
    private BigDecimal currdue;
    private BigDecimal outbill;
    private String status;
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
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public BigDecimal getOutbill() {
		return outbill;
	}
	public void setOutbill(BigDecimal outbill) {
		this.outbill = outbill;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", mobile=" + mobile + ", email=" + email + ", duedate="
				+ duedate + ", prevdue=" + prevdue + ", currdue=" + currdue + ", outbill=" + outbill + ", status="
				+ status + "]";
	}  

}
