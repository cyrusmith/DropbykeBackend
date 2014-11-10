package com.dropbyke

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.codehaus.groovy.grails.web.context.ServletContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import grails.transaction.Transactional

@Transactional
class FileUploadService {

	private static final okcontents = [
		'image/png',
		'image/jpeg',
		'image/gif'
	]

	def savePhoto(MultipartFile file, String folder, long id) {

		if(!id) {
			log.error("Bike image id not set")
			return false
		}

		if (!okcontents.contains(file.getContentType())) {
			throw new IllegalArgumentException("Avatar must be one of: ${okcontents}")
		}

		def servletContext = ServletContextHolder.servletContext

		String path = servletContext.getRealPath(folder);
		File bikesPath = new File(path)
		if(!bikesPath.exists()) {
			if(!bikesPath.mkdirs()) {
				throw new Exception("Could not create dest folder " + path)
			}
		}

		InputStream inputStream = file.getInputStream()

		BufferedImage im = ImageIO.read(file.getInputStream());
		ImageIO.write(im, "jpg", new File(path + "/" + id +'.jpg'))

		inputStream.close()
	}
	
	def checkPhotoExists(String folder, long id) {
		def servletContext = ServletContextHolder.servletContext
		String path = servletContext.getRealPath(folder);
		File imageFile = new File(path + "/" + id +'.jpg')
		return imageFile.exists()
	}
	
}
