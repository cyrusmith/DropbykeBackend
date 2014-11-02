<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Bike</title>

</head>
<body>

	<h2>
		<a class="button" href="<g:createLink action="index" id="${id}" />"><span
			class="glyphicon glyphicon-chevron-left"></span></a> Bike
	</h2>

	<g:set var="actionLink">
		<g:createLink action="${actionName}" id="${id}" />
	</g:set>

	<g:uploadForm url="${actionLink}" role="form">
		<div class="form-group">
			<label for="title">Title</label>
			<g:textField name="title" class="form-control" value="${title}"
				id="title" placeholder="Enter bike model or/and name" />
		</div>

		<div class="form-group">
			<label for="sku">SKU</label>
			<g:textField name="sku" class="form-control" value="${sku}" id="sku"
				placeholder="Enter bike sku" />
		</div>

		<div class="form-group">
			<button type="submit" class="btn btn-primary btn-lg">Submit</button>
		</div>

		<g:hiddenField name="id" value="${id}" />

	</g:uploadForm>

</body>
</html>