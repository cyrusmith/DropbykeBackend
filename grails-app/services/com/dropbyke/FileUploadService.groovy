package com.dropbyke

import java.awt.Image;
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

	public enum Folder {

		BIKES("/images/bikes"),
		RIDES("/images/rides"),
		USERS("/images/rides")

		Folder(String path) {
			this.path = path
		}
		private final String path
		public String path() {
			return path
		}
	}

	def savePhoto(MultipartFile file, Folder folder, long id) {

		if(!id) {
			log.error("Bike image id not set")
			return false
		}

		if (!okcontents.contains(file.getContentType())) {
			throw new IllegalArgumentException("Avatar must be one of: ${okcontents}")
		}

		def servletContext = ServletContextHolder.servletContext

		String path = servletContext.getRealPath(folder.path());
		File bikesPath = new File(path)
		if(!bikesPath.exists()) {
			if(!bikesPath.mkdirs()) {
				throw new Exception("Could not create dest folder " + path)
			}
		}

		InputStream inputStream = null
		BufferedImage srcImg = null
		BufferedImage bufferedThumbnail = null

		try {

			inputStream = file.getInputStream()
			srcImg = ImageIO.read(inputStream);

			println srcImg.getWidth()

			def destWidth = 1200

			if(srcImg.getWidth() > destWidth) {
				Image thumbnail = srcImg.getScaledInstance(destWidth, -1, Image.SCALE_SMOOTH);
				bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
						thumbnail.getHeight(null),
						BufferedImage.TYPE_INT_RGB)
				bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
				ImageIO.write(bufferedThumbnail, "jpg", new File(path + "/" + id +'.jpg'))
			}
			else {
				ImageIO.write(srcImg, "jpg", new File(path + "/" + id +'.jpg'))
			}
		}
		catch(e) {
			println e.message
			throw e
		}
		finally {
			if(bufferedThumbnail) {
				bufferedThumbnail.flush()
			}
			if(srcImg) {
				srcImg.flush()
			}

			if(inputStream) {
				inputStream.close()
			}
		}
	}

	def checkPhotoExists(Folder folder, long id) {
		def servletContext = ServletContextHolder.servletContext
		String path = servletContext.getRealPath(folder.path());
		File imageFile = new File(path + "/" + id +'.jpg')
		return imageFile.exists()
	}
}
