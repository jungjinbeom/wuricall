package com.wurigo.socialService.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	  private JavaMailSender sender;
	  
	  public void sendEmail(String toAddress, String subject, String body) {
	    
	    MimeMessage message = sender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    try {
	      helper.setTo(toAddress);
	      helper.setSubject(subject);
	      helper.setText(body);
	    } catch (MessagingException e) {
	      e.printStackTrace();
	    }
	   
	    sender.send(message);
	    
	  }
}
