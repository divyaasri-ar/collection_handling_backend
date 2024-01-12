package com.example.demo;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.demo.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class SpringBillingApplication {
	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void setup() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBillingApplication.class, args);
		System.out.println("Started!");
	}
	
//	 @Bean
//	    public JavaMailSender javaMailSender() {
//	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//	        mailSender.setHost("smtp.gmail.com");
//	        mailSender.setPort(587);
//	        mailSender.setUsername("sec19cs121@sairamtap.edu.in"); // Replace with your email address
//	        mailSender.setPassword("oove qpdn dotm fzuw"); // Replace with your email password
//
//	        return mailSender;
//	    }

}
