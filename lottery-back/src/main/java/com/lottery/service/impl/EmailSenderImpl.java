package com.lottery.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

	@Override
	public void sendMimeMessageJoinRaffle(String to, String subject, String nombre, 
			String sorteo, int numero, String monto, String... bbc) {
		String text = "<html>\r\n"
				+ "	<div style=\"width:800px; margin:0 auto;\">\r\n"
				+ "		<p>Hola</p>\r\n"
				+ "		<p>Gracias "+nombre+" por tu participación en el sorteo de</p>\r\n"
				+ "		<h2>"+sorteo+"</h2>\r\n"
				+ "		<p>El numero que elegiste para participar es el: <b>"+numero+"</b></p>\r\n"
				+ "		<p>Con un costo de <b>$"+monto+"</b> el cual recuerda debes de pagar en un máximo de 72 hrs para que no sea liberado y pueda ser elegido por otro participante.Recuerda que puedes comprar tantos boletos como desees  para un mismo sorteo.</p>\r\n"
				+ "		<p>Una vez hecho tu pago…recuerda enviar tu comprobante de pago y recibirás la confirmación de recibido.</p>\r\n"
				+ "		<p>Recomiendanos y corre la voz</p>\r\n"
				+ "		<p>Mucha suerte!!!</p>\r\n"
				+ "	</div>\r\n"
				+ "</html>";
		sendMimeMessage(to, subject, text, bbc);
	}

	@Override
	public void sendMimeMessageFinished(String to, String subject, String sorteo, String... bbc) {
		String text = "<html>\r\n"
				+ "	<div style=\"width:800px; margin:0 auto;\">\r\n"
				+ "		<p>El sorteo:</p>\r\n"
				+ "		<h2>"+sorteo+"</h2>\r\n"
				+ "		<p>ha finalizado.</p>\r\n"
				+ "		<p>Lamentamos informarle que <b>NO</b> ha sigo ganador del mismo. Le invitamos a que siga participando</p>\r\n"
				+ "	</div>\r\n"
				+ "</html>";
		sendMimeMessage(to, subject, text, bbc);
	}

	@Override
	public void sendMimeMessageWinner(String to, String subject, String sorteo) {
		String text = "<html>\r\n"
				+ "	<div style=\"width:800px; margin:0 auto;\">\r\n"
				+ "		<h2>Felicidades!!</h2>\r\n"
				+ "		<p>Le informamos que ha sido ganador del sorteo:</p>\r\n"
				+ "		<h2>"+sorteo+"</h2>\r\n"
				+ "		<p>En breve nos pondremos en contacto con usted para hacerle entrega del mismo</p>\r\n"
				+ "		<p>Gracias por su participación</p>\r\n"
				+ "	</div>\r\n"
				+ "</html>";
		sendMimeMessage(to, subject, text);
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
	
	public void sendMimeMessage(String to, String subject, String text, String ...bbc) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setBcc(bbc);
            helper.setSubject(subject);
            helper.setText(text,true);
            
            emailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
