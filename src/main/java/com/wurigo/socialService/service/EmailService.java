package com.wurigo.socialService.service;

public interface EmailService {
	public void sendEmail(String toAddress, String subject, String body);
}
