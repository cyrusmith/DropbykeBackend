class UrlMappings {

	static mappings = {
		
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
		"/api/register"(controller:"users", action: "registerPhone", method:"POST")		
		"/api/verify"(controller:"users", action: "verifyCode", method:"POST")
		
		"/api/addcard"(controller:"card", action: "addCard", method:"POST")
		
		"/admin/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
