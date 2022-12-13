package com.example.projekat.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.projekat.image.FileUploadService;
import com.example.projekat.image.ImageService;

@RestController
public class ImageUploadController {

	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	ImageService imageService;
	
	@GetMapping("")
	public String uploadImage() {
		return "uploadImage";
	}
	
	@PostMapping("/uploadImage")
	public String uploadImage(@RequestParam("image")MultipartFile imageFile) {
		if(imageFile.isEmpty()) {
			return "Prazan Fajl";
		}
		File file = fileUploadService.upload(imageFile);
		if(file == null) {
			return "Prazan fajl za upload";
		}
		
		boolean resized = imageService.resizeImage(file);
		if(!resized) {
			return "nije uspesno smanjena";
		}
		return "slika je uspesno smanjena";
	}
}
