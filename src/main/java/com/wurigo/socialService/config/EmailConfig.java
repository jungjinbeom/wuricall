package com.wurigo.socialService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;

public class EmailConfig {
	@Bean
	public SimpleMailMessage templateSimpleMessage() {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setText(
	      "This is the test email template for your email:\n%s\n");
	    return message;
	}
}
