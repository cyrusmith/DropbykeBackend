<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Users</title>
</head>
<body>
	<div class="container">



		<table class="table users-table table-striped">
			<thead>
				<tr>
					<th>Phone</th>
					<th>Email</th>
					<th>Name</th>
					<th>Status</th>
					<th>Actions</th>
				</tr>

			</thead>
			<g:each in="${users}" var="user">
				<tr>
					<td>
						${user.phone}
					</td>
					<td>
						${user.email?:"not set"}
					</td>
					<td>
						${user.username}
					</td>
					<td><g:if test="${true}">
							<span class="glyphicon glyphicon-user user-status-online text-success"></span>
						</g:if> 
						<g:else>
							<span class="glyphicon glyphicon-user user-status-offline text-danger"></span>
						</g:else></td>
					<td>
						<button type="button" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-log-out"></span> Log out</button>
							
							<button type="button" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash"></span> Delete</button>
					</td>
				</tr>
			</g:each>
		</table>
		<g:paginate max="2" next="Forward" prev="Back" maxsteps="0"
			controller="adminUsers" action="index" total="${usersCount}" />

	</div>
</body>
</html>