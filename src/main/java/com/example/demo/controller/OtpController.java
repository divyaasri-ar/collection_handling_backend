package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.SmsService;
import com.example.demo.dto.OtpRequest;
import com.example.demo.dto.OtpResponseDto;
import com.example.demo.dto.OtpValidationRequest;
import com.example.demo.dto.ValidateOpt;
import com.example.demo.model.Customer;
import com.example.demo.model.Demo;
import com.example.demo.model.demoOtp;
import com.example.demo.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/otp")
@Slf4j
@RequiredArgsConstructor
public class OtpController {
	
	private final SmsService smsService;
	private Long systemOtp;
	
	private final CustomerRepository crepo;
	
	@GetMapping("/process")
	public String processSMS() {
		return "SMS sent";
	}

//	@PostMapping("/send-otp")
//	public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
//		log.info("inside sendOtp :: "+otpRequest.getUsername());
//		return smsService.sendSMS(otpRequest);
//	}
//	@PostMapping("/validate-otp")
//    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
//		log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
//		return smsService.validateOtp(otpValidationRequest);
//    }
//	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/send")
    public ResponseEntity<demoOtp> sendOtp(@RequestBody OtpRequest otpRequest) {
        // Logic to send OTP
        OtpResponseDto response = smsService.sendSMS(otpRequest);
        systemOtp=response.getOtp();
       // return ResponseEntity.ok(response);
        return new ResponseEntity<demoOtp>(new demoOtp("success"),HttpStatus.OK);
    }
	
//	@CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/validate")
    public ResponseEntity<ValidateOpt> validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        // Logic to validate OTP
    	System.out.println(otpValidationRequest);
        String validationResponse = smsService.validateOtp(otpValidationRequest,systemOtp);
        //return ResponseEntity.ok(validationResponse);
        System.out.println();
        return new ResponseEntity<ValidateOpt>(new ValidateOpt(validationResponse,crepo.findByMobile(otpValidationRequest.getUsername())),HttpStatus.OK);
    }
    
//    @GetMapping("/checkPhoneNumber/{phoneNumber}")
//    public ResponseEntity<Map<String, Boolean>> checkPhoneNumberExists(@PathVariable String phoneNumber) {
//        boolean exists = crepo.existsByMobile(phoneNumber);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }
//    
    @PostMapping("/checkPhoneNumber")
    @ResponseBody
    public ResponseEntity<demoOtp> loginAdmin(@RequestBody String mobile) {
	    //String mob = credentials.get("mobile");
	    //String password = credentials.get("password");
	    //System.out.println(credentials);
    	System.out.println(mobile);
	    Customer cust=crepo.findByMobile(mobile);
	    System.out.println(cust);
	    if(cust==null || !mobile.equals(cust.getMobile()))
	    {
	    	return new ResponseEntity<demoOtp>(new demoOtp("invalid"),HttpStatus.UNAUTHORIZED);
	    }
	    return new ResponseEntity<demoOtp>(new demoOtp("success"),HttpStatus.OK);
	}
    
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    
    @PostMapping("/username")
    public ResponseEntity<demoOtp> getCustomer(@RequestBody OtpValidationRequest otpValidationRequest) {
    	System.out.println(otpValidationRequest);
        String validationResponse = smsService.otp(otpValidationRequest);
        //return ResponseEntity.ok(validationResponse);
        return new ResponseEntity<demoOtp>(new demoOtp(validationResponse),HttpStatus.OK);
    }
    
//    @CrossOrigin(origins = "http://localhost:4200")
//    @ResponseBody
//    
//    @PostMapping("/username")
//    public Customer getCustomer(OtpValidationRequest otpValidationRequest) {
//    	System.out.println("User Name "+otpValidationRequest.getUsername());
//		Customer c = crepo.findByMobile(otpValidationRequest.getUsername());
//		System.out.println(c);
//		return c;
//    }

//    @CrossOrigin(origins="http://localhost:4200")
//	@PostMapping("/username")
//	public ResponseEntity<Demo> loginAdmin(@RequestBody Map<String, String> credentials) {
//	    String username = credentials.get("username");
//	    String phoneNumber = credentials.get("phoneNumber");
//	    System.out.println(credentials);
//	    Customer c = crepo.findByMobile(phoneNumber);
//
//	    if (c==null && !phoneNumber.equals(c.getMobile())) {
//	    	System.out.println("hii");
//	        return new ResponseEntity<Demo>(new Demo("success"),HttpStatus.OK);
//	    } else {
//	        return new ResponseEntity<Demo>(new Demo("invalid"),HttpStatus.UNAUTHORIZED);
//	    }
//	}
}
