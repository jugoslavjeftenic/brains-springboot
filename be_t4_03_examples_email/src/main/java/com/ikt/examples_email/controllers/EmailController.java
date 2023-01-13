package com.ikt.examples_email.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.examples_email.dto.EmailDTO;
import com.ikt.examples_email.services.EmailService;

@RestController
@RequestMapping(path = "api/v1/email")
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/simple")
	public String sendSimpleMailMessage(@RequestBody EmailDTO emailDTO) {
		if (emailDTO.getTo() == null || emailDTO.getTo() == "") {
			return "Please provide a value for TO";
		}
		if (emailDTO.getSubject() == null || emailDTO.getSubject() == "") {
			return "Please provide a value for SUBJECT";
		}
		if (emailDTO.getText() == null || emailDTO.getText() == "") {
			return "Please provide a value for TEXT";
		}
		emailService.sendSimpleMessage(emailDTO);
		return "Your email has been sent";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/template")
	public String sendTemplateMailMessage(@RequestBody EmailDTO emailDTO) {
		if (emailDTO.getTo() == null || emailDTO.getTo() == "") {
			return "Please provide a value for TO";
		}
		if (emailDTO.getSubject() == null || emailDTO.getSubject() == "") {
			return "Please provide a value for SUBJECT";
		}
		if (emailDTO.getText() == null || emailDTO.getText() == "") {
			return "Please provide a value for TEXT";
		}
		emailService.sendTemplateMessage(emailDTO);
		return "Your email has been sent";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/attachment")
	public String sendMessageWithAttachment(@RequestBody EmailDTO emailDTO) {
		if (emailDTO.getTo() == null || emailDTO.getTo() == "") {
			return "Please provide a value for TO";
		}
		if (emailDTO.getSubject() == null || emailDTO.getSubject() == "") {
			return "Please provide a value for SUBJECT";
		}
		if (emailDTO.getText() == null || emailDTO.getText() == "") {
			return "Please provide a value for TEXT";
		}
		emailService.sendMessageWithAttachment(emailDTO, "C:\\folder\\file.ext");
		return "Your email has been sent";
	}
}
