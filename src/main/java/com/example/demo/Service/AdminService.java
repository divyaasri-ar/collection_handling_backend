package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService {
private final AdminRepository repository;
	
	@Autowired
	public AdminService(AdminRepository repository)
	{
		this.repository=repository;
	}
	
	public boolean authenticateAdmin(String email, String password)
	{
		Admin admin=repository.findByEmail(email);
		System.out.println(admin.getPassword().equals(password));
		return admin.getPassword().equals(password);
	}

}
