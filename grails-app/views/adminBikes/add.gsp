<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Add bike</title>
</head>
<body>

	<div class="container">

		<g:if test="${flash.error}">
			<div class="message bg-danger">
				${flash.error}
				<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
		</g:if>
		<g:if test="${flash.message}">
			<div class="message bg-primary"">
				${flash.message}
				<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			</div>
		</g:if>

		<h2>Add bike</h2>

		<g:uploadForm action="add" role="form">
			<div class="form-group">
				<label for="title">Title</label> 
				<g:textField name="title" class="form-control" value="$title" id="title" placeholder="Enter bike model or/and name"/>
			</div>

			<div class="form-group">
				<fieldset>
					<legend>Position</legend>
					<div class="row">
						<div class="col-xs-2">
							<label for="lat">Latitude</label> <input type="text"
								class="form-control" id="lat" name="lat">

						</div>
						<div class="col-xs-2">
							<label for="lon">Longitude</label> <input type="text"
								class="form-control" id="lon" name="lon">

						</div>

						<div class="col-xs-2">
							<label for="">&nbsp;</label><br> <a class="btn btn-primary"><span
								class="glyphicon glyphicon-map-marker"></span></a>
						</div>
					</div>
				</fieldset>
			</div>

			<div class="form-group">
				<label for="photo">Photo</label> <input type="file" name="photo"
					id="photo" />
			</div>

			<div class="form-group">
				<button type="submit" class="btn btn-primary btn-lg">Submit</button>
			</div>

		</g:uploadForm>

	</div>
</body>
</html>