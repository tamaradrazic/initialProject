package com.example.projekat.image;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.projekat.errors.TooLargeException;

@Service
public class ImageServiceImpl implements ImageService{

	@Value("${image.folder}")
	private String imageFolder;
	
	@Autowired
	private MessageSource messageSource;
	
	private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Override
	public byte[] resizeImage(File sourceFile, int imageSize, Locale locale) {
		try {
			BufferedImage bufferedImage = ImageIO.read(sourceFile);
			BufferedImage outputImage = Scalr.resize(bufferedImage, imageSize);
			int height1 = bufferedImage.getHeight();
			if(height1 < imageSize) {
				String message = messageSource.getMessage("tooLargeImage", null, locale);
				throw new TooLargeException(message);
			}
			ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
			op.filter(outputImage, outputImage);
			
			String newFile = FilenameUtils.getBaseName(sourceFile.getName()) 
					+ "_" + imageSize + "." + FilenameUtils.getExtension(sourceFile.getName());
			Path path = Paths.get(imageFolder, newFile);
			File newImageFile = path.toFile();
			outputImage.flush();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(outputImage, "jpg", newImageFile);
		    ImageIO.write(outputImage, "png", baos);
		    byte[] byteArr = baos.toByteArray();
			return byteArr;
		}catch(IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
