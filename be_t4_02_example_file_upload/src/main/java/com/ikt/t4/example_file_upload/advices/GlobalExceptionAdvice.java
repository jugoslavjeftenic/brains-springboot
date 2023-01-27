package com.ikt.t4.example_file_upload.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(MultipartException.class)
	public String handleMultipartException(MultipartException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message",	e.getCause().getMessage());
		return "redirect:uploadStatus";
	}
}
