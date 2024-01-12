package com.example.demo.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.config.TwilioConfig;
import com.example.demo.dto.OtpRequest;
import com.example.demo.dto.OtpResponseDto;
import com.example.demo.dto.OtpStatus;
import com.example.demo.dto.OtpValidationRequest;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {
	
	private final TwilioConfig twilioConfig;
	
	private final CustomerRepository crepo;
    Map<String, String> otpMap = new HashMap<>();


	public OtpResponseDto sendSMS(OtpRequest otpRequest) {
		OtpResponseDto otpResponseDto = null;
		String otp = null;
		try {
			String pnumber= "+91"+otpRequest.getPhoneNumber();
			System.out.println(pnumber);
			PhoneNumber to = new PhoneNumber(pnumber);//to
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
			otp = generateOTP();
			String otpMessage = "Dear Customer , Your OTP is  " + otp + " for sending sms through Spring boot application. Thank You.";
			Message message = Message
			        .creator(to, from,
			                otpMessage)
			        .create();
			otpMap.put(otpRequest.getUsername(), otp);
			otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage,Long.parseLong(otp));
		} catch (Exception e) {
			e.printStackTrace();
			otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage(),Long.parseLong(otp));
		}
		return otpResponseDto;
	}
	
	public String validateOtp(OtpValidationRequest otpValidationRequest,Long opt) {
		System.out.println("User Name "+otpValidationRequest.getUsername());
		Customer c = crepo.findByMobile(otpValidationRequest.getUsername());
		System.out.println(c);
    	System.out.println(Long.parseLong(otpValidationRequest.getOtpNumber()));
        if (opt == Long.parseLong(otpValidationRequest.getOtpNumber())) {
        	System.out.println(opt);
        	System.out.println(Long.parseLong(otpValidationRequest.getOtpNumber()));
            return "success";
        } else {
            return "fail";
        }
	}
	
	private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
	


  public String otp(OtpValidationRequest otpValidationRequest) {
  	System.out.println("User Name "+otpValidationRequest.getUsername());
		Customer c = crepo.findByMobile(otpValidationRequest.getUsername());
		System.out.println(c);
		return "success";
  }
	

}
