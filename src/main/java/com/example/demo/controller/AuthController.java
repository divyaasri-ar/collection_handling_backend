package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/api/logout")
	  public void logout(HttpServletRequest request, HttpServletRequest response) {
	    // Invalidating the session
		System.out.println("logout");
	    request.getSession().invalidate();
	  }
	}


