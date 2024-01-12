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
public class FinalRemainder {
	private final PendingCustomerService customerService;
    private final EmailService emailService;

    @Autowired
    public FinalRemainder(PendingCustomerService customerService, EmailService emailService) {
        this.customerService = customerService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 25 18 * * ?") // This schedules the task to run every day at midnight
    public void sendDueDateReminders() {
    	System.out.println("Scheduled mail for final ramainder");
    	LocalDate today = LocalDate.now();
    	LocalDate dueDateThresholdAfter5 = today.plusDays(5); // Five days after the due date

        // Reminders after 5 days past the due date
        sendRemindersForDueDate(dueDateThresholdAfter5, "This is your final reminder. Your account will be terminated in 2 days.");
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
                    "        <p>This is your final reminder regarding the outstanding payment on your bill payment. We have not yet received the payment that was due by" +customer.getDuedate()+".</p>\r\n"+
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
                    "         <p>We kindly urge you to settle the outstanding amount of"+customer.getOutbill()+ "at your earliest convenience to avoid any further actions. To make the payment, please click the pay button:</p>\r\n"+
                    "        <form action='http://localhost:4200/paymentportal'>\r\n" +
                    "            <input type='submit' value='Pay Now'>\r\n" +
                    "        </form>\r\n" +
                    "    </body>\r\n" +
                    "</html>";

            try {
                emailService.sendEmail(customer.getEmail(), "Final Reminder", emailContent);
            } catch (MessagingException e) {
                // Handle email sending exception (log or rethrow if necessary)
                e.printStackTrace();
            }
        }
    }
    

}
