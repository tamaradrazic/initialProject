package com.example.projekat.image;

import java.io.File;
import java.util.Locale;

public interface ImageService {

	byte[] resizeImage(File sourceFile, int imageSize, Locale locale);
}
