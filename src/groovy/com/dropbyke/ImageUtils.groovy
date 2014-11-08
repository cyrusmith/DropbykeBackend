package com.dropbyke;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

class ImageUtils {

	private static final log = LogFactory.getLog(this)

	private static final okcontents = [
		'image/png',
		'image/jpeg',
		'image/gif'
	]

	static boolean saveRidePhotoFromMultipart(ServletContext context, CommonsMultipartFile f, long id) {

		if(!id) {
			log.error("Bike image id not set")
			return false
		}

		if (!okcontents.contains(f.getContentType())) {
			log.error("Avatar must be one of: ${okcontents}")
			return false
		}

		final String name = f.getOriginalFilename()
		try {
			InputStream input = new ByteArrayInputStream(f.getBytes());
			BufferedImage bImageFromConvert = ImageIO.read(input);

			String path = context.getRealPath("/images/rides/");
			File bikesPath = new File(path)
			if(!bikesPath.exists()) {
				bikesPath.mkdir()
			}
			ImageIO.write(bImageFromConvert, "jpg", new File(path + "/" + id+'.jpg'))
			return true
		}
		catch(Exception ex) {
			log.error(ex)
			return false
		}
	}
	
	static boolean checkRidePhotoExists(ServletContext context, long rideId) {
		String path = context.getRealPath("/images/rides/");
		File imageFile = new File(path + "/" + rideId +'.jpg')
		return imageFile.exists()
	}
	
	static boolean saveUserPhotoFromMultipart(ServletContext context, CommonsMultipartFile f, long id) {
		
		if(!id) {
			log.error("Bike image id not set")
			return false
		}
		
		if (!okcontents.contains(f.getContentType())) {
			log.error("Avatar must be one of: ${okcontents}")
			return false
		}
		
		final String name = f.getOriginalFilename()
				try {
					InputStream input = new ByteArrayInputStream(f.getBytes());
					BufferedImage bImageFromConvert = ImageIO.read(input);
					
					String path = context.getRealPath("/images/users/");
					File bikesPath = new File(path)
					if(!bikesPath.exists()) {
						bikesPath.mkdir()
					}
					ImageIO.write(bImageFromConvert, "jpg", new File(path + "/" + id+'.jpg'))
					return true
				}
		catch(Exception ex) {
			log.error(ex)
			return false
		}
	}
}
