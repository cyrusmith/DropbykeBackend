package com.dropbyke.command

/**
 * Created by cyrusmith on 12.12.2014.
 */
class FacebookPhotoDownloadCommand implements Serializable {
    long userId
    String token

    static constraints = {
        userId(blank: false)
        token(blank: false)
    }
}
