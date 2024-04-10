package com.example.buildfolio.service;

import com.example.buildfolio.model.UserContact;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vk783838@gmail.com");
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        try{
            mailSender.send(message);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean sendMessage(UserContact userContact, String receiverEmail){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String htmlContent = getHtmlContent(userContact);
            helper.setTo(receiverEmail);
            helper.setSubject("Email From BuildFolio");
            helper.setText(htmlContent, true);
            mailSender.send(message);
            return true;
        }
        catch (Exception e ) {
            return  false;
        }
    }

    public String getHtmlContent(UserContact userContact)  {
        String htmlContent = "<html><body>" +
                "<h2>New Contact Form Submission</h2>" +
                "<p><strong>Name:</strong> " + userContact.getName() + "</p>" +
                "<p><strong>Email:</strong> " + userContact.getEmail() + "</p>" +
                "<p><strong>Message:</strong> <br/><br/>" + userContact.getMessage() + "</p>" +
                "</body></html>";
        return htmlContent;
    }
}