package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PendingCustomerDto {

    private Long id;
    private String name;
    private String mobile;
    private String email;
    private BigDecimal prevdue;
    private BigDecimal currdue;
    private BigDecimal outbill;
    private LocalDate duedate;
    private long daysUnpaid;
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
	public long getDaysUnpaid() {
		return daysUnpaid;
	}
	public void setDaysUnpaid(long daysUnpaid) {
		this.daysUnpaid = daysUnpaid;
	}
	
	public void calculateDaysUnpaid() {

        LocalDate currentDate = LocalDate.now();

        daysUnpaid = Math.abs(ChronoUnit.DAYS.between(duedate, currentDate));

    }
	
	@Override
	public String toString() {
		return "PendingCustomerDto [id=" + id + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", prevdue=" + prevdue + ", currdue=" + currdue + ", outbill=" + outbill + ", duedate=" + duedate
				+ ", daysUnpaid=" + daysUnpaid + "]";
	}

    
}
