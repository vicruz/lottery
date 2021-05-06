package com.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lottery.service.EmailService;

@Service
public class EmailSenderImpl implements EmailService {
	
	private static final String NOREPLY_ADDRESS = "noreply@lottery-test.com";
	
	@Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SimpleMailMessage template;

	@Override
	public void sendMessage(String to, String subject, String... templateModel) {
		String text = String.format(template.getText(), templateModel);  
        sendSimpleMessage(to, subject, text);

	}

	public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
