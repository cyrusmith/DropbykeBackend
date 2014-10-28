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
			<fieldset>
				<legend>Position</legend>
				<div class="row location-selector">
					<div class="col-xs-3">
						<label for="lat">Latitude</label>
						<g:textField type="text" class="form-control" id="lat" name="lat"
							value="${lat}" />

					</div>
					<div class="col-xs-3">
						<label for="lon">Longitude</label>
						<g:textField type="text" class="form-control" id="lon" name="lon"
							value="${lon}" />

					</div>

					<div class="col-xs-2">
						<label for="">&nbsp;</label><br> <a
							class="btn btn-primary select-onmap" data-toggle="modal"
							data-target="#mapModal"><span
							class="glyphicon glyphicon-map-marker"></span></a>
					</div>
				</div>
			</fieldset>
		</div>

		<g:if test="${hasPhoto}">
			<div class="form-group">
				<img src='<g:createLink  uri="/bikes/image/${id}"/>' />

			</div>
		</g:if>

		<div class="form-group">
			<label for="photo">Photo</label> <input type="file" name="photo"
				id="photo" />
			<p>Should be &lt; 100kB</p>
		</div>

		<div class="form-group">
			<button type="submit" class="btn btn-primary btn-lg">Submit</button>
		</div>

		<g:hiddenField name="id" value="${id}" />

	</g:uploadForm>

	<div class="modal fade" id="mapModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Choose location</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary apply-location">Apply</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>