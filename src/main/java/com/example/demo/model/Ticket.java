package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Ticket {	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private Long customerId;
	    private String selectedIssue;
	    private String otherIssue;
//	    private String selectedPriority;
	    private String email;
	    
	  

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getSelectedIssue() {
	        return selectedIssue;
	    }

	    public void setSelectedIssue(String selectedIssue) {
	        this.selectedIssue = selectedIssue;
	    }

	    public String getOtherIssue() {
	        return otherIssue;
	    }

	    public void setOtherIssue(String otherIssue) {
	        this.otherIssue = otherIssue;
	    }

//	    public String getSelectedPriority() {
//	        return selectedPriority;
//	    }
//
//	    public void setSelectedPriority(String selectedPriority) {
//	        this.selectedPriority = selectedPriority;
//	    }

		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}

		

		
	}

