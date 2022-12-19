package com.example.projekat.image;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	File upload(MultipartFile image);
}
