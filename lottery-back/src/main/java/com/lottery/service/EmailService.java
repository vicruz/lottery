package com.lottery.service;

public interface EmailService {

	void sendMessage(String to,
            String subject,
            String ...templateModel);
	
	void sendMessageFinished(String to, String subject, String sorteo, String ...bbc);
	
	void sendMessageWinner(String to, String subject, String sorteo);
	
	void sendMimeMessageJoinRaffle(String to, String subject, String nombre, 
			String sorteo, int numero, String monto, String... bbc);
	
	void sendMimeMessageFinished(String to, String subject, String sorteo, String ...bbc);
	
	void sendMimeMessageWinner(String to, String subject, String sorteo);
}
