class UrlMappings {

	static mappings = {
		
		"/api/register"(controller:"users", action: "registerPhone", method:"POST")		
		"/api/verify"(controller:"users", action: "verifyCode", method:"POST")
		
		"/api/addcard"(controller:"card", action: "addCard", method:"POST")
		
		"/admin/adminBikes/add"(controller:"adminBikes", action: "add", method:"GET")
		
		"/admin/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
				
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
