<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Grails" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<asset:stylesheet src="application.css" />
<asset:javascript src="application.js" />
<g:layoutHead />
</head>
<body>

	<div class="container">
		
		<div class="menu">
			<g:link controller="adminUsers" action="index">Users</g:link> |
			<g:link controller="adminBikes" action="index">Bikes</g:link> |
			<g:link controller="adminRides" action="index">Rides</g:link> 			
		</div>
		
		<g:if test="${flash.error}">
			<div class="message bg-danger">
				${flash.error}
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
			</div>
		</g:if>
		<g:if test="${flash.message}">
			<div class="message bg-primary"">
				${flash.message}
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
			</div>
		</g:if>
		<g:layoutBody />
	</div>
</body>
</html>
