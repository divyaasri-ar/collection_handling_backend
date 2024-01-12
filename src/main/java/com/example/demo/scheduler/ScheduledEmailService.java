package com.example.demo.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.Service.EmailService;
import com.example.demo.Service.PendingCustomerService;
import com.example.demo.model.PendingCustomer;

import jakarta.mail.MessagingException;

@Component
public class ScheduledEmailService {
	 private final PendingCustomerService customerService;
	    private final EmailService emailService;

	    @Autowired
	    public ScheduledEmailService(PendingCustomerService customerService, EmailService emailService) {
	        this.customerService = customerService;
	        this.emailService = emailService;
	    }

	    @Scheduled(cron = "0 37 14 * * ?") // This schedules the task to run every day at midnight
	    public void sendDueDateReminders() {
	    	System.out.println("Scheduled mail executed at 10:20 AM.");
	        LocalDate today = LocalDate.now();
	        LocalDate dueDateThresholdBefore = today.plusDays(2); // Two days before the due date
	        LocalDate dueDateThresholdAfter1 = today.plusDays(1); // One day after the due date
	        LocalDate dueDateThresholdAfter2 = today.plusDays(2); // Two days after the due date
	        LocalDate dueDateThresholdAfter3 = today.plusDays(3); // Three days after the due date

	        sendRemindersForDueDate(dueDateThresholdBefore, "Your due date is approaching.");

	        sendRemindersForDueDate(dueDateThresholdAfter1, "This is a reminder for your overdue payment (1 day after due date).");
	        sendRemindersForDueDate(dueDateThresholdAfter2, "This is a reminder for your overdue payment (2 days after due date).");
	        sendRemindersForDueDate(dueDateThresholdAfter3, "This is a reminder for your overdue payment (3 days after due date).");
	    }

	    private void sendRemindersForDueDate(LocalDate dueDateThreshold, String message) {
	        List<PendingCustomer> customersWithDueDate = customerService.getCustomersWithDueDate(dueDateThreshold);
	        for (PendingCustomer customer : customersWithDueDate) {
	            String emailContent = "<html>\r\n" +
	                    "    <head>\r\n" +
	                    "        <title>Reminder</title>\r\n" +
	                    "    </head>\r\n" +
	                    "    <body>\r\n" +
	                    "        <p>Dear " + customer.getName() + ",</p>\r\n" +
	                    "        <p>" + message + "</p>\r\n" +
	                    "        <p>Please make the payment as soon as possible. Thank you for your cooperation.</p>\r\n" +
	                    "        <br>\r\n" +
	                    "        <br>\r\n" +
	                    "        <table>\r\n" +
	                    "            <tr>\r\n" +
	                    "                <td>Id</td>\r\n" +
	                    "                <td>" + customer.getId() + "</td>\r\n" +
	                    "            </tr>\r\n" +
	                    "            <tr>\r\n" +
	                    "                <td>Name</td>\r\n" +
	                    "                <td>" + customer.getName() + "</td>\r\n" +
	                    "            </tr>\r\n" +
	                    "            <tr>\r\n" +
	                    "                <td>Email</td>\r\n" +
	                    "                <td>" + customer.getEmail() + "</td>\r\n" +
	                    "            </tr>\r\n" +
	                    "            <tr>\r\n" +
	                    "                <td>Mobile</td>\r\n" +
	                    "                <td>" + customer.getMobile() + "</td>\r\n" +
	                    "            </tr>\r\n" +
	                    "            <tr>\r\n" +
	                    "                <td>Outstanding Bill</td>\r\n" +
	                    "                <td>" + customer.getOutbill() + "</td>\r\n" +
	                    "            </tr>\r\n" +
	                    "            <tr>\r\n" +
	                    "                <td>Due Date</td>\r\n" +
	                    "                <td>" + customer.getDuedate() + "</td>\r\n" +
	                    "            </tr>\r\n" +
	                    "        </table>\r\n" +
	                    "         <p>To make the payment, please click the pay button:</p>\r\n"+
	                    "        <form action='http://localhost:4200/paymentportal'>\r\n" +
	                    "            <input type='submit' value='Pay Now'>\r\n" +
	                    "        </form>\r\n" +
	                    "    </body>\r\n" +
	                    "</html>";

	            try {
	                emailService.sendEmail(customer.getEmail(), "Payment Reminder", emailContent);
	            } catch (MessagingException e) {
	                // Handle email sending exception (log or rethrow if necessary)
	                e.printStackTrace();
	            }
	        }
	    }
	    
}

