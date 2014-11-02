<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Bikes</title>
</head>
<body>

	<h2>
		Bikes <a type="button" class="btn btn-primary"
			href="<g:createLink action="add"/>"> <span
			class="glyphicon glyphicon-plus"></span> Add bike
		</a>
	</h2>

	<table class="table users-table table-striped">
		<thead>
			<tr>
				<g:sortableColumn property="title" title="Title" />
				<g:sortableColumn property="sku" title="SKU" />
				<g:sortableColumn property="locked" title="Status" />
			</tr>

		</thead>
		<g:each in="${bikes}" var="bike">
			<tr>
				<td><a href="<g:createLink action="edit" id="${bike.id}"/>">
						${bike.title}
				</a></td>
				<td>
					${bike.sku}
				</td>
				<td><g:if test="${bike.locked}">
				Locked
				
					<g:link action="unlock" id="${bike.id}"
							class="button button-success">Unlock</g:link>



					</g:if> <g:else>
					Free <g:link controller="adminRides" action="add"
							id="${bike.id}" class="button button-success">Start ride</g:link>
					</g:else></td>

			</tr>
		</g:each>
	</table>

	<g:paginate total="${bikesCount}" />

</body>
</html>