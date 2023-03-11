package com.ikt.t99.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	public JavaMailSender javaMailSender;
	
	@Override
	public void sendSimpleEmailMessage(String to, String subject, String text) {
		
		logger.info("Pozvan je metod sendSimpleEmailMessage() sa To {}, Subject {} i Text {}", to, subject, text);
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		javaMailSender.send(simpleMailMessage);
	}
}
