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

	<table class="table users-table">
		<thead>
			<tr>
				<g:sortableColumn property="user" title="User" />
				<g:sortableColumn property="title" title="Title" />
				<g:sortableColumn property="sku" title="SKU" />
				<g:sortableColumn property="active" title="Active (sharing)" />
				<g:sortableColumn property="locked" title="Status" />
				<g:sortableColumn property="rating" title="Rating" />
				<th>Lock password</th>
				<th>Location</th>
			</tr>

		</thead>
		<g:each in="${bikes}" var="bike">
		
			<tr class="<g:if test="${!bike.active}">
					bg-danger
					</g:if><g:if test="${bike.locked}">
					bg-info
					</g:if>">
				<td><a href="<g:createLink action="edit" controller="adminUsers" id="${bike.user.id}"/>">
						${bike.user.username}
				</a></td>
				<td><a href="<g:createLink action="edit" id="${bike.id}"/>">
						${bike.title}
				</a></td>
				<td>
					${bike.sku}
				</td>
				<td><g:if test="${bike.active}">
					Active
					</g:if> <g:else>
					Inactive 
					</g:else></td>
				<td><g:if test="${bike.locked}">
				Locked
				
					<g:link action="stopUsage" id="${bike.id}"
							class="button button-success">Stop usage</g:link>

					</g:if> <g:else>
					Available for usage 
					</g:else></td>
				<td>
					${bike.rating}
				</td>
				<td>
					${bike.lockPassword}
				</td>
				<td>
					${bike.lat} | ${bike.lng}
				</td>

			</tr>
		</g:each>
	</table>

	<div class="pagination">
		<g:paginate total="${bikesCount}" />
	</div>

</body>
</html>