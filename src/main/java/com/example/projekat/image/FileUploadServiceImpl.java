package com.example.projekat.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImpl implements FileUploadService{

	@Value("${image.folder}")
	private String imageFolder;
	
	private Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
	
	@Override
	public File upload(MultipartFile image) {
		try {
			Path path = Paths.get(imageFolder, image.getOriginalFilename());
			Files.write(path, image.getBytes());
			return path.toFile();
		}catch(IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	
}
