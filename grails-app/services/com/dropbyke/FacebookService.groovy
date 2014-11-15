package com.dropbyke

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.codehaus.groovy.grails.web.context.ServletContextHolder;
import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.transaction.Transactional

@Transactional
class FacebookService {

	def getUserInfo(String token) {

		URL html = new URL("https://graph.facebook.com/me?access_token=" + token);
		URLConnection urlConnection = html.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		StringBuffer response = new StringBuffer();
		String inputLine;
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine)
		}
		reader.close();

		log.debug("Got from facebook " + response.toString())
		def result = [:]
		JSONObject json = new JSONObject(response.toString())
		for(String key in json.keys()) {
			result.put(key, json.get(key))
		}

		return result
	}

	def downloadPhoto(long userId, String token) {

		def servletContext = ServletContextHolder.servletContext

		String path = servletContext.getRealPath("/images/users/");

		InputStream inputStream = null
		BufferedImage srcImg = null
		BufferedImage bufferedThumbnail = null

		try {
			URL html = new URL("https://graph.facebook.com/me/picture?type=large&access_token=" + token);
			URLConnection urlConnection = html.openConnection();

			inputStream = urlConnection.getInputStream()
			srcImg = ImageIO.read(inputStream);

			println "Loaded: " + srcImg.getWidth()

			def destWidth = 1200

			if(srcImg.getWidth() > destWidth) {
				Image thumbnail = srcImg.getScaledInstance(destWidth, -1, Image.SCALE_SMOOTH);
				bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
						thumbnail.getHeight(null),
						BufferedImage.TYPE_INT_RGB)
				bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
				ImageIO.write(bufferedThumbnail, "jpg", new File(path + "/" + userId +'.jpg'))
			}
			else {
				ImageIO.write(srcImg, "jpg", new File(path + "/" + userId +'.jpg'))
			}
		}
		catch(e) {
			println e.message
			throw e
		}
		finally {

			try {
				if(inputStream) {
					inputStream.close()
				}
			}
			catch(e) {
			}

			try {
				if(bufferedThumbnail) {
					bufferedThumbnail.flush()
				}
			}
			catch(e) {
			}

			try {
				if(srcImg) {
					srcImg.flush()
				}
			}
			catch(e) {
			}
		}
	}
}
