package com.dropbyke

import com.dropbyke.command.FacebookPhotoDownloadCommand
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.json.JSONObject

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage

class FacebookService {

    def fileUploadService

    static transactional = false

    static exposes = ['jms']

    def downloadProfilePhoto(FacebookPhotoDownloadCommand cmd) {

        log.debug "Start downloadProfilePhoto"

        if (fileUploadService.checkPhotoExists(FileUploadService.Folder.USERS, cmd.userId)) {
            log.debug "Avatar already exists"
            return
        }

        def servletContext = ServletContextHolder.servletContext

        String path = servletContext.getRealPath("/images/users/");

        File dir = new File(path)

        if (!dir.exists()) {
            dir.mkdir()
        }

        InputStream inputStream = null
        BufferedImage srcImg = null
        BufferedImage bufferedThumbnail = null

        try {
            URL html = new URL("https://graph.facebook.com/me/picture?type=large&access_token=" + cmd.token);
            URLConnection urlConnection = html.openConnection();

            inputStream = urlConnection.getInputStream()
            srcImg = ImageIO.read(inputStream);

            log.debug "Loaded: " + srcImg.getWidth()

            def destWidth = 1200

            if (srcImg.getWidth() > destWidth) {
                Image thumbnail = srcImg.getScaledInstance(destWidth, -1, Image.SCALE_SMOOTH);
                bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
                        thumbnail.getHeight(null),
                        BufferedImage.TYPE_INT_RGB)
                bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
                ImageIO.write(bufferedThumbnail, "jpg", new File(path + "/" + cmd.userId + '.jpg'))
            } else {
                ImageIO.write(srcImg, "jpg", new File(path + "/" + cmd.userId + '.jpg'))
            }
        }
        catch (e) {
            log.error e.message
            throw e
        }
        finally {

            try {
                if (inputStream) {
                    inputStream.close()
                }
            }
            catch (e) {
            }

            try {
                if (bufferedThumbnail) {
                    bufferedThumbnail.flush()
                }
            }
            catch (e) {
            }

            try {
                if (srcImg) {
                    srcImg.flush()
                }
            }
            catch (e) {
            }
        }

    }

    @Transactional
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
        for (String key in json.keys()) {
            result.put(key, json.get(key))
        }

        return result
    }

    def downloadPhoto(long userId, String token) {


    }
}
