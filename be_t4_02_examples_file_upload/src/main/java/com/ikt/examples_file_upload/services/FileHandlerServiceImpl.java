package com.ikt.examples_file_upload.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {

	private static String UPLOAD_DIRECTORY = "e:\\Downloads\\";
	
	@Override
	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select file to upload!");
			return "redirect:uploadStatus";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_DIRECTORY + file.getOriginalFilename());
			// TODO Domaci: generisati naziv upload-ovane datoteke (timestamp, guid + ekstenzija substr, regex)
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message", "You have succesfully uploaded " + file.getOriginalFilename());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:uploadStatus";
	}

}
