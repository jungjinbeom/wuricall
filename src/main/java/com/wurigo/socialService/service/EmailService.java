package com.wurigo.socialService.service;

public interface EmailService {
	public boolean sendMail(String to , String subject, String connect) throws Exception;
}
