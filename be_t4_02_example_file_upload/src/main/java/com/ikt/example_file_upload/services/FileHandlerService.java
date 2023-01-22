package com.ikt.example_file_upload.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface FileHandlerService {

	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes);
}
