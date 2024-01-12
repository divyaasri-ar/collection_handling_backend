package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerSupport;
import com.example.demo.repository.CustomerSupportRepository;

@Service
public class CustomerSupportService {
	 private final CustomerSupportRepository repository;

	    @Autowired
	    public CustomerSupportService(CustomerSupportRepository repository) {
	        this.repository = repository;
	    }

	    public boolean authenticateCustomerSupport(String email, String password) {
	        CustomerSupport customerSupport = repository.findByEmail(email);
	        return customerSupport != null && customerSupport.getPassword().equals(password);
	    }

}
