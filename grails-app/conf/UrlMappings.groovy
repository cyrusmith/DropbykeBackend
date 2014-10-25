class UrlMappings {

	static mappings = {
		
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
		"/api/register"(controller:"users", action: "registerPhone", method:"POST")		
		"/api/verify"(controller:"users", action: "verifyCode", method:"POST")
		
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
