class UrlMappings {

	static mappings = {
		
		"/api/loginFacebook"(controller:"users", action: "loginFacebook", method:"POST")		
		"/api/register"(controller:"users", action: "sendSMS", method:"POST")		
		"/api/verify"(controller:"users", action: "verifySMSCodeAndRegister", method:"POST")
		"/api/verifyAuthenticated"(controller:"users", action: "verifySMSCode", method:"POST")
		"/api/addcard"(controller:"card", action: "addCard", method:"POST")

		"/api/bikes/"(controller: "bikes", action: "bikesInArea", method: "GET")
		"/api/bikes/startusage"(controller: "bikes", action: "startUsage", method: "POST")		
		"/api/bikes/$id"(controller: "bikes", action: "view", method: "GET")
		
		"/api/rides/photo"(controller: "rides", action: "uploadPhoto", method: "POST")
		"/api/rides/stop"(controller: "rides", action: "stopRide", method: "POST")
		"/api/rides/checkout"(controller: "rides", action: "checkout", method: "POST")
		"/api/rides/$id"(controller: "rides", action: "viewRide", method: "GET")
		
		"/api/profile/photo"(controller: "users", action: "uploadPhoto", method: "POST")		
		"/api/profile/logout"(controller: "users", action: "logout", method: "POST")		
		"/api/profile/"(controller: "users", action: "viewProfile", method: "GET")
		"/api/profile/"(controller: "users", action: "updateProfile", method: "POST")		
				
		"/admin/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
				
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
