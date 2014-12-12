package com.dropbyke.command

/**
 * Created by cyrusmith on 12.12.2014.
 */
@grails.validation.Validateable
class FacebookRegisterCommand {
    String uid
    String token

    static constraints = {
        uid(blank: false)
        token(blank: false)
    }
}
