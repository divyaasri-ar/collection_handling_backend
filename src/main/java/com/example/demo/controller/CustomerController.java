package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	  
	@Autowired
	CustomerRepository customerRepository;
	
	    @CrossOrigin(origins = "http://localhost:4200")
	    @ResponseBody
	    
	    @GetMapping("/all")
	    public List<Customer> getAllCustomers() {
	        return customerRepository.findAll();
	    }

}
