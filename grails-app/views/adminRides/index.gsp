<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Rides</title>
</head>
<body>

	<h2>
		Rides <a type="button" class="btn btn-primary"
			href="<g:createLink action="add"/>"> <span
			class="glyphicon glyphicon-plus"></span> Add ride
		</a>
	</h2>

	<table class="table users-table table-striped">
		<thead>
			<tr>
				<g:sortableColumn property="id" title="#" />
				<g:sortableColumn property="bike" title="Bike" />
				<g:sortableColumn property="user" title="User" />
				<g:sortableColumn property="startTime" title="Start time" />
				<g:sortableColumn property="stopTime" title="Stop time" />
				<th>Stop location</th>
				<th>Lock password</th>
			</tr>

		</thead>
		<g:each in="${rides}" var="ride">
			<tr>
				<td><a href="<g:createLink action="edit" id="${ride.id}"/>">
						${ride.id}
				</a></td>
				<td><g:link controller="adminBikes" action="edit"
						id="${ride.bike.id}">
						${ride.bike.title}
					</g:link></td>
				<td><g:link controller="adminUsers" action="edit"
						id="${ride.user.id}">
						<g:if test="${ride.user.name}">
							${ride.user.name} (${ride.user.phone})
						</g:if>
						<g:else>
							${ride.user.phone}
						</g:else>

					</g:link></td>
				<td>
					${ride.startTime}
				</td>
				<td>
					${ride.stopTime}
				</td>
				<td>
					${ride.stopLat}|${ride.stopLng}
				</td>
				<td>
					${ride.lockPassword}
				</td>
			</tr>
		</g:each>
	</table>

	<g:paginate total="${ridesCount}" />

</body>
</html>