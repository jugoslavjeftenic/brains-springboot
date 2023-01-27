package com.ikt.t4.example_file_upload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ikt.t4.example_file_upload.services.FileHandlerService;

@Controller
@RequestMapping(path = "/")
public class UploadController {

	@Autowired
	private FileHandlerService fileHandlerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "upload";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
	
	// TODO Domaci: v1 - FileUpload uraditi kroz RESTful aplikaciju i upload-ovati datoteku kroz postman
	// TODO Domaci: v2 - RedirectAttributes staviti u kontroler a ne u servise
	// TODO Domaci: nazive datoteka generisati (timestamp ili guid) i dodati ekspenziju (bonus: RegEx)
	
	// RedirectAttributes redirectAttributes je potreban za Server Pages, ne i za REST endpoint
	@RequestMapping(method = RequestMethod.POST, path = "/upload")
	public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		return fileHandlerService.singleFileUpload(file, redirectAttributes);
	}
}
