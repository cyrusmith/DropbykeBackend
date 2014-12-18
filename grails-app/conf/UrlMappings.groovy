class UrlMappings {

	static mappings = {

		"/api/loginFacebook"(controller:"users", action: "loginFacebook", method:"POST")
		"/api/register"(controller:"users", action: "sendSMS", method:"POST")
		"/api/verify"(controller:"users", action: "verifySMSCodeAndRegister", method:"POST")
		"/api/verifyAuthenticated"(controller:"users", action: "verifySMSCode", method:"POST")
		"/api/aditcard"(controller:"card", action: "editCard", method:"POST")

		"/api/bikes/"(controller: "bikes", action: "bikesInArea", method: "GET")
		"/api/bikes/startusage"(controller: "bikes", action: "startUsage", method: "POST")
		"/api/bikes/distancevalid"(controller: "bikes", action: "isValidDistance", method: "GET")
		"/api/bikes/$id"(controller: "bikes", action: "view", method: "GET")

		"/api/rides/photo"(controller: "rides", action: "uploadPhoto", method: "POST")
		"/api/rides/stop"(controller: "rides", action: "stopRide", method: "POST")
		"/api/rides/checkout"(controller: "rides", action: "checkout", method: "POST")
		"/api/rides/$id"(controller: "rides", action: "viewRide", method: "GET")

		"/api/profile/photo"(controller: "users", action: "uploadPhoto", method: "POST")
		"/api/profile/logout"(controller: "users", action: "logout", method: "POST")
		"/api/profile/"(controller: "users", action: "viewProfile", method: "GET")
		"/api/profile/"(controller: "users", action: "updateProfile", method: "POST")

		"/api/share/bikes/"(controller: "bikes", action: "bikesList", method: "GET") //list of bikes
		"/api/share/bikes/"(controller: "bikes", action: "addBike", method: "POST") //add new bike
		"/api/share/bikes/$id"(controller: "bikes", action: "editBike", method: "POST") //edit bike
		"/api/share/bike/$id"(controller: "bikes", action: "bikeInfo", method: "GET")
		"/api/share/bike/$id/userphoto"(controller: "bikes", action: "addUserPhoto", method: "POST")

		"/api/share/history"(controller: "rides", action: "listOwnersRides", method: "GET")
		"/api/share/history/$id"(controller: "rides", action: "viewOwnersRide", method: "GET")

		"/admin/$controller/$action?/$id?(.$format)?"{ constraints { // apply constraints here
			}  }

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
