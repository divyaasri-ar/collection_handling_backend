package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.AdminService;
import com.example.demo.model.Admin;
import com.example.demo.model.Demo;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

//	@Autowired
//	AdminService authService;
	public final AdminService adminService;
	
//	@Autowired
//	Admin admin;
	
	@Autowired
	public AdminController(AdminService adminService)
	{
		this.adminService=adminService;
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/login")
	public ResponseEntity<Demo> loginAdmin(@RequestBody Map<String, String> credentials) {
	    String email = credentials.get("email");
	    String password = credentials.get("password");
	    System.out.println(credentials);

	    if (adminService.authenticateAdmin(email, password)) {
	    	System.out.println("hii");
	        return new ResponseEntity<Demo>(new Demo("success"),HttpStatus.OK);
	    } else {
	        return new ResponseEntity<Demo>(new Demo("invalid"),HttpStatus.UNAUTHORIZED);
	    }
	}
//	@PostMapping("/login")
//	public String loginAdmin(@RequestParam String email,@RequestParam String password)
//	{
////		Admin admin = Admin.builder().build();
//		System.out.println("Received email"+email);
//		System.out.println("Received password"+password);
//		
//		if(adminService.authenticateAdmin(email, password))
//		{
//			System.out.println("Login success");
//			return "login Successful";
////			return ResponseEntity.ok("Login Successful");
//		}
//		else
//		{
//			System.out.println("Login Invalid");
//			return "invalid Credentials";
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
//		}
//	}

}
