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
public class Termination {
	private final PendingCustomerService customerService;
    private final EmailService emailService;

    @Autowired
    public Termination(PendingCustomerService customerService, EmailService emailService) {
        this.customerService = customerService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 34 15 * * ?") // This schedules the task to run every day at midnight
    public void sendDueDateReminders() {
    	System.out.println("Scheduled mail for final ramainder");
    	LocalDate today = LocalDate.now();
    	LocalDate dueDateThresholdForTermination = today.plusDays(7); // 7 days after the due date
        sendTerminationEmails(dueDateThresholdForTermination, "Your account has been terminated due to non-payment. For any queries, please contact our support team.");
    }
    private void sendTerminationEmails(LocalDate dueDateThreshold, String terminationMessage) {
        List<PendingCustomer> customersWithDueDate = customerService.getCustomersWithDueDate(dueDateThreshold);
        for (PendingCustomer customer : customersWithDueDate) {
            String terminationEmailContent = "<html>\r\n" +
                    "    <head>\r\n" +
                    "        <title>Reminder</title>\r\n" +
                    "    </head>\r\n" +
                    "    <body>\r\n" +
                    "        <p>Dear " + customer.getName() + ",</p>\r\n" +
                    "        <p>" + terminationMessage + "</p>\r\n" +
                    "        <p>We regret to inform you that your telecom connection with us  has been terminated due to non-payment of outstanding dues.</p>\r\n"+
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
                    "         <p>+ Your outstanding balance of"+customer.getOutbill()+ "was due by "+customer.getDuedate()+". Despite multiple reminders, the payment has not been received.</p>\r\n"+
                    "         <p>As a result, your account has been terminated, and you will no longer be able to access our services. If you believe this is an error, or if you have any questions regarding your account or if you want to pay and continue our service, please click the pay button:lease click the pay button: please click the contiue button:. </p>\r\n"+
                    "        <form action='http://localhost:4200/customeroptionslogin'>\r\n" +
                    "            <input type='submit' value='Continue'>\r\n" +
                    "        </form>\r\n" +
                    "    </body>\r\n" +
                    "</html>";

            try {
                emailService.sendEmail(customer.getEmail(), "Termination Notice", terminationEmailContent);
            } catch (MessagingException e) {
                // Handle email sending exception (log or rethrow if necessary)
                e.printStackTrace();
            }
        }
    }
    

}

