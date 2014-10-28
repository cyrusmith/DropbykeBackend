<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Bikes</title>
</head>
<body>
	<div class="container">

		<h2>Bikes</h2>
		<div class="row">

			<a type="button" class="btn btn-primary" href="<g:createLink action="add"/>">
				<span class="glyphicon glyphicon-plus"></span> Add bike
			</a>
		</div>



		<table class="table users-table table-striped">
			<thead>
				<tr>
					<th>Title</th>
					<th>Status</th>
					<th>Location</th>
				</tr>

			</thead>
			<g:each in="${bikes}" var="bike">
				<tr>
					<td>
						${bike.title}
					</td>
					<td>
						${bike.riding}
					</td>
					<td>
						${bike.lat} | ${bike.lon}
					</td>
				</tr>
			</g:each>
		</table>
		<g:paginate max="2" next="Forward" prev="Back" maxsteps="0"
			controller="adminBikes" action="index" total="${bikesCount}" />

	</div>
</body>
</html>