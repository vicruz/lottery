package com.lottery.service;

public interface EmailService {

	void sendMessage(String to,
            String subject,
            String ...templateModel);
	
	void sendMessageFinished(String to, String subject, String sorteo, String ...bbc);
	
	void sendMessageWinner(String to, String subject, String sorteo);
}
