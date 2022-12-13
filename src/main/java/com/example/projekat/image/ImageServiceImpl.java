package com.example.projekat.image;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{

	@Value("${image.folder}")
	private String imageFolder;
	
	@Value("${image.size}")
	private Integer imageSize;
	
	private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Override
	public boolean resizeImage(File sourceFile) {
		try {
			BufferedImage bufferedImage = ImageIO.read(sourceFile);
			BufferedImage outputImage = Scalr.resize(bufferedImage, imageSize);
			
			ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
			op.filter(outputImage, outputImage);
			
			String newFile = FilenameUtils.getBaseName(sourceFile.getName()) 
					+ "_" + imageSize.toString() + "." + FilenameUtils.getExtension(sourceFile.getName());
			Path path = Paths.get(imageFolder, newFile);
			File newImageFile = path.toFile();
			ImageIO.write(outputImage, "jpg", newImageFile);
			outputImage.flush();
			return true;
		}catch(IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}
