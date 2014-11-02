<%@page import="com.dropbyke.Bike"%>
<%@page import="com.dropbyke.User"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Edit user</title>
</head>
<body>

	<h2>
		<a class="button" href="<g:createLink action="index" id="${id}" />"><span
			class="glyphicon glyphicon-chevron-left"></span></a> Edit user
	</h2>
	<g:set var="actionLink">
		<g:createLink action="${actionName}" id="${id}" />
	</g:set>

	<g:uploadForm url="${actionLink}" role="form">
		<div class="form-group">
			<label for="phone">Phone</label>
			<g:textField class="form-control" name="phone" id="phone"
				value="${phone}" />
		</div>
		
		<div class="form-group">
			<label for="phone">Name</label>
			<g:textField class="form-control" name="name" id="name"
				value="${name}" />
		</div>
		
		<div class="form-group">
			<label for="phone">Email</label>
			<g:textField class="form-control" name="email" id="email"
				value="${email}" />
		</div>

		<div class="form-group">
			<button type="submit" class="btn btn-primary btn-lg">Submit</button>
		</div>

		<g:hiddenField name="id" value="${id}" />
	</g:uploadForm>



</body>
</html>