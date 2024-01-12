package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.AdminService;
import com.example.demo.Service.CustomerSupportService;
import com.example.demo.model.Demo;
import com.example.demo.model.Democus;

@RestController
@RequestMapping("/api/custsupport")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerSupportController {
	private final CustomerSupportService customerSupportService;
	  @Autowired
	    public CustomerSupportController(CustomerSupportService customerSupportService) {
	        this.customerSupportService = customerSupportService;
	    }

	    @PostMapping("/login")
	    public ResponseEntity<Democus> loginAdmin(@RequestBody Map<String, String> credentials) {
	        String email = credentials.get("email");
	        String password = credentials.get("password");

	        if (customerSupportService.authenticateCustomerSupport(email, password)) {
	            return new ResponseEntity<>(new Democus("success"), HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(new Democus("invalid"), HttpStatus.UNAUTHORIZED);
	        }
	    }
	}

