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

	@Override
	public void sendMessageFinished(String to, String subject, String sorteo, String... bbc) {
		String text = "El sorteo " + sorteo + " ha finalizado.\n Lamentamos informarle que no ha sigo ganador del mismo. Le invitamos a que siga participando";
		sendSimpleMessage(to, subject, text, bbc);
	}

	@Override
	public void sendMessageWinner(String to, String subject, String sorteo) {
		String text = "Felicidades!!!! Le informamos que ha sigo el ganador del sorteo " + sorteo + ". ";
		sendSimpleMessage(to, subject, text);	
	}
	
	public void sendSimpleMessage(String to, String subject, String text, String ...bbc) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setBcc(bbc);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
