package com.dropbyke

import com.dropbyke.FileUploadService.Folder
import com.dropbyke.command.FacebookPhotoDownloadCommand
import com.dropbyke.command.FacebookRegisterCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import groovy.util.logging.Log4j
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.validation.FieldError

@Log4j
class UsersController {

    def phoneService
    def loginService
    def springSecurityService
    def userService
    def fileUploadService
    def facebookService
    def jmsService
    def messageSource

    static allowedMethods = [registerPhone: 'POST']

    @Secured(['permitAll'])
    def loginFacebook(FacebookRegisterCommand cmd) {

        if (cmd.hasErrors()) {
            List msgs = []
            if (cmd.errors.allErrors.size() > 0) {
                cmd.errors.allErrors.each { FieldError error ->
                    msgs.add(messageSource.getMessage(error, Locale.default))
                }
            }
            return render(status: 400, contentType: "application/json") {
                ["error": msgs.size() > 0 ? msgs[0] : "Invalid params", "errors": msgs]
            }
        }

        try {

            Map userInfo = facebookService.getUserInfo(cmd.token)

            if (!userInfo && userInfo["id"]) {
                return render(status: 500, contentType: "application/json") {
                    ["error": "Failed to get info from facebook"]
                }
            }

            User user = loginService.registerFacebook(cmd.uid, userInfo["name"] ? userInfo["name"] : "", userInfo["email"] ? userInfo["email"] : "")

            if (!user) {
                return render(status: 500, contentType: "application/json") { ["error": "Failed register new user"] }
            }

            if (!fileUploadService.checkPhotoExists(Folder.USERS, user.id)) {
                try {
                    facebookService.downloadProfilePhoto(new FacebookPhotoDownloadCommand(userId: user.id, token: cmd.token))
                }
                catch (e) {

                }

                //jmsService.send(service: 'facebook', method: 'downloadProfilePhoto', new FacebookPhotoDownloadCommand(userId: user.id, token: cmd.token))
            }

            try {
                String tokenValue = loginService.loginFacebook(userInfo["id"])

                userInfo = userService.getUserInfo(user.id)

                render(status: 200, contentType: "application/json") {
                    [
                            "user_info"   : userInfo,
                            "access_token": tokenValue
                    ]
                }
            }
            catch (e) {
                return render(status: 500, contentType: "application/json") { ["error": e.message] }
            }
        }
        catch (e) {
            log.error e.message
            return render(status: 500, contentType: "application/json") { ["error": "Error during request"] }
        }
    }

    @Secured(['permitAll'])
    def sendSMS() {

        JSONObject data = request.JSON

        String phone = data.has("phone") ? data.getString("phone") : null

        if (!phone) {
            return render(status: 400, contentType: "application/json") { ["error": "Phone not set"] }
        }

        String error = "SMS submission failed"
        try {
            String key = phoneService.sendSMS(phone)
            if (key) {
                return render(status: 200, contentType: "application/json") { ["request_key": key] }
            } else {
                error = "Could not get response from SMS service"
            }
        }
        catch (Exception e) {
            log.error e.message
            error = e.message
        }

        return render(status: 500, contentType: "application/json") { ["error": error] }
    }

    @Secured(['ROLE_USER'])
    def verifySMSCode() {
        JSONObject data = request.JSON

        String code = data.has("code") ? data.getString("code") : null
        String phone = data.has("phone") ? data.getString("phone") : null
        String key = data.has("verify_key") ? data.getString("verify_key") : null

        if (!code) {
            return render(status: 400, contentType: "application/json") { ["error": "Code not set"] }
        }

        if (!key) {
            return render(status: 400, contentType: "application/json") { ["error": "Key not set"] }
        }

        try {
            if (phoneService.verifySMSCode(phone, code, key)) {
                User authenticatedUser = springSecurityService.loadCurrentUser()
                if (!userService.setPhone(authenticatedUser.id, phone)) {
                    return render(status: 500, contentType: "application/json") {
                        ["error": "Could not update user's phone number."]
                    }
                }

                def userInfo = userService.getUserInfo(authenticatedUser.id)
                render(status: 200, contentType: "application/json") {
                    [
                            "user_info": userInfo
                    ]
                }
            } else {
                return render(status: 400, contentType: "application/json") { ["error": "Invalid code"] }
            }
        }
        catch (e) {
            return render(status: 500, contentType: "application/json") { ["error": e.message] }
        }
    }

    @Secured(['permitAll'])
    def verifySMSCodeAndRegister() {

        JSONObject data = request.JSON

        String code = data.has("code") ? data.getString("code") : null
        String phone = data.has("phone") ? data.getString("phone") : null
        String key = data.has("verify_key") ? data.getString("verify_key") : null

        if (!code) {
            return render(status: 400, contentType: "application/json") { ["error": "Code not set"] }
        }

        if (!key) {
            return render(status: 400, contentType: "application/json") { ["error": "Key not set"] }
        }

        try {
            if (phoneService.verifySMSCode(phone, code, key)) {
                User user = loginService.register(phone)
                String tokenValue = loginService.loginPhone(user.phone)
                def userInfo = userService.getUserInfo(user.id)
                render(status: 200, contentType: "application/json") {
                    [
                            "user_info"   : userInfo,
                            "access_token": tokenValue
                    ]
                }
            } else {
                return render(status: 400, contentType: "application/json") { ["error": "Invalid code"] }
            }
        }
        catch (e) {
            log.error e.getClass()
            log.error e.message
            return render(status: 500, contentType: "application/json") { ["error": e.message] }
        }
    }

    @Secured(['ROLE_USER'])
    def viewProfile() {

        def authenticatedUser = springSecurityService.loadCurrentUser()
        def info = userService.getUserInfo(authenticatedUser.id)
        info["timestamp"] = System.currentTimeMillis()

        boolean hasPhoto = false
        if (info.ride) {
            hasPhoto = fileUploadService.checkPhotoExists(Folder.RIDES, info.ride.id)
        }

        grails.converters.JSON.registerObjectMarshaller(Ride, { Ride ride ->
            return [
                    id          : ride.id,
                    charged     : ride.charged,
                    complete    : ride.complete,
                    distance    : ride.distance,
                    message     : ride.message,
                    startAddress: ride.startAddress,
                    startLat    : ride.startLat,
                    startLng    : ride.startLng,
                    startTime   : ride.startTime,
                    stopAddress : ride.stopAddress,
                    stopLat     : ride.stopLat,
                    stopLng     : ride.stopLng,
                    stopTime    : ride.stopTime,
                    sum         : ride.sum,
                    hasPhoto    : hasPhoto
            ]
        })

        render(status: 200, contentType: "application/json") { info }
    }

    @Secured(['ROLE_USER'])
    def logout() {
        User authUser = springSecurityService.loadCurrentUser()
        if (loginService.logout(authUser.phone)) {
            return render(status: 200, contentType: "application/json") {
                []
            }
        }
        return render(status: 500, contentType: "application/json") {
        }
    }

    @Secured(['ROLE_USER'])
    @Transactional
    def updateProfile() {
        JSONObject data = request.JSON

        User authUser = springSecurityService.loadCurrentUser()
        def name = data.has("name") ? data.getString("name") : ""
        def email = data.has("email") ? data.getString("email") : ""
        def shareFacebook = data.has("shareFacebook") ? data.getBoolean("shareFacebook") : true

        User user = User.get(authUser.id)

        if (!user) {
            return render(status: 401, contentType: "application/json") { ["error": "User not authenticated"] }
        }

        if (name) {
            user.name = name
        }

        if (email) {
            user.email = email
        }

        user.shareFacebook = shareFacebook

        if (email || name) {
            user.editedOnce = true
            if (user.save()) {
                return render(status: 200, contentType: "application/json") { ["profile": user] }
            } else {
                return render(status: 500, contentType: "application/json") { ["error": "Could not save user"] }
            }
        }
        return render(status: 400, contentType: "application/json") { ["error": "No arguments set"] }
    }

    @Secured(['ROLE_USER'])
    def uploadPhoto() {
        def authenticatedUser = springSecurityService.loadCurrentUser()
        def photo = request.getFile('photo')
        if (photo && !photo.isEmpty()) {
            try {
                fileUploadService.savePhoto(photo, Folder.USERS, authenticatedUser.id)
                return render(status: 200, contentType: "application/json") {
                }
            }
            catch (e) {
                return render(status: 500, contentType: "application/json") {
                    ["error": "Failed to save uploaded file: " + e.message]
                }
            }
        }
        return render(status: 400, contentType: "application/json") { ["error": "No file"] }
    }

    @Secured(['permitAll'])
    def validatePhoneNumber() {
        String phone = params.containsKey('phone') ? params['phone'] : ''

        if (!phone) {
            return render(status: 400, contentType: "application/json") { ["error": "No phone number"] }
        }

        try {
            if (phoneService.validatePhoneNumber(phone)) {
                return render(status: 200, contentType: "application/json") {}
            }
        }
        catch (Exception e) {
            return render(status: 400, contentType: "application/json") { ["error": e.getMessage()] }
        }

    }

}