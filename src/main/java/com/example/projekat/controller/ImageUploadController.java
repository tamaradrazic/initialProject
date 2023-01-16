package com.example.projekat.controller;

import java.io.File;
import java.util.Base64;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.projekat.errors.TooLargeException;
import com.example.projekat.image.FileUploadService;
import com.example.projekat.image.ImageService;

/**
 * ImageUploadController The ImageUploadController class implements method for
 * upload and resize image
 * 
 * @author tamara
 * @since 2022-12-13
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/images")
public class ImageUploadController {

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	ImageService imageService;

	@Autowired
	private MessageSource messageSource;

	public static final int MAX_SIZE = 16000000;

	/**
	 * This method is used to upload and resize forwarded image.
	 * 
	 * @param image     This is the first parameter to uploadImage method, and it
	 *                  represents image in <code> MultipartFile </code> format
	 * @param imageSize This is the second parameter to uploadImage method, it
	 *                  represents new size of image
	 * @return <code>ResponseEntity<String></code> This return ResponseEntity with
	 *         image in Base64 format and HTTP status
	 */

	@PostMapping("/uploadImage")
	public ResponseEntity<String> uploadImage(@RequestHeader(name = "ACCEPT_LANGUAGE", required = false) String al,
			Locale locale, @RequestBody MultipartFile image, @RequestParam("imageSize") int imageSize) {
		File file;
		try {
			file = fileUploadService.upload(image);
			if (!FilenameUtils.getExtension(file.getName()).equals("jfif")) {
				String message = messageSource.getMessage("unsupportedMediaType", null, locale);
				return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
			}
		} catch (Exception e) {
			String message = messageSource.getMessage("notFound", null, locale);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}

		if (image == null) {
			String message = messageSource.getMessage("noContent", null, locale);
			return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
		}
		System.out.print(image.getSize());
		if (image.getSize() > MAX_SIZE) {
			String message = messageSource.getMessage("maxSize", null, locale);
			throw new TooLargeException(message);
		}

		byte[] resized = imageService.resizeImage(file, imageSize, locale);

		if (resized != null) {
			String bajt = Base64.getEncoder().encodeToString(resized);
			return new ResponseEntity<String>(bajt, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}
