package com.example.projekat.image;

import java.io.File;

public interface ImageService {

	byte[] resizeImage(File sourceFile, int imageSize);
}
