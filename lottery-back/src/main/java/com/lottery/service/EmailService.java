package com.lottery.service;

public interface EmailService {

	void sendMessage(String to,
            String subject,
            String ...templateModel);
}
