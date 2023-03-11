package com.ikt.t04.example_email.services;

import com.ikt.t04.example_email.dto.EmailDTO;

public interface EmailService {

	public void sendSimpleMessage(EmailDTO emailDTO);
	public void sendTemplateMessage(EmailDTO emailDTO);
	public void sendMessageWithAttachment(EmailDTO emailDTO, String pathToAttachment);
}
