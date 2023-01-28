package com.ikt.t4.example_file_upload.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {

	private static String UPLOAD_DIRECTORY = "e:\\Downloads\\";
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
		logger.info("SingleFileUpload method invoked" + file.getOriginalFilename());
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select file to upload!");
			return "redirect:uploadStatus";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_DIRECTORY + file.getOriginalFilename());
			Files.write(path, bytes);
			logger.info("File uploaded and stored succesfully");
			redirectAttributes.addFlashAttribute("message", "You have succesfully uploaded " + file.getOriginalFilename());
		} catch (IOException e) {
			logger.error("An exception ocured while uploading a file");
			e.printStackTrace();
		}
		return "redirect:uploadStatus";
	}

}
