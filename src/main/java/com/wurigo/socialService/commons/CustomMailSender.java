package com.wurigo.socialService.commons;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class CustomMailSender {

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
