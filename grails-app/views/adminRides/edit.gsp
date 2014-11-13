<%@page import="com.dropbyke.Bike"%>
<%@page import="com.dropbyke.User"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Edit ride</title>
</head>
<body>

	<h2>
		<a class="button" href="<g:createLink action="index" id="${ride?.id}" />"><span
			class="glyphicon glyphicon-chevron-left"></span></a> Edit ride
	</h2>
	<p>Since this is for initial setup only I do not check overlapping
		ride time, do not validate start and stop times etc. It is all up to
		you.</p>
	<g:set var="actionLink">
		<g:createLink action="${actionName}" id="${ride?.id}" />
	</g:set>

	<g:uploadForm url="${actionLink}" role="form">
		<div class="form-group">
			<label for="user">User</label>
			<g:select id="user" name="user.id" from="${User.list()}"
				class="form-control" optionValue="phone" value="${ride?.user?.id}"
				optionKey="id" />
		</div>

		<div class="form-group">
			<label for="bike">Bike</label>
			<g:select id="bike" name="bike.id" from="${Bike.list()}"
				class="form-control" optionValue="title" value="${ride?.bike?.id}"
				optionKey="id" />
		</div>

		<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<label for="startTime">Start timestamp</label>
					<g:textField class="form-control datetimepicker" name="startTime"
						id="startTime" value="${ride?.startTime}" />
				</div>


			</div>

			<div>

				<div class="col-sm-6">
					<div class="form-group">
						<label for="stopTime">Stop timestamp</label>
						<g:textField class="form-control datetimepicker" name="stopTime"
							id="stopTime" value="${ride?.stopTime}" />
					</div>
				</div>


			</div>

		</div>

		<div class="form-group location-start">
			<fieldset>
				<legend>Start position</legend>
				<div class="form-group">
					<label for="startAddress">Start address</label>
					<g:textField class="form-control" name="startAddress"
						id="startAddress" value="${ride?.startAddress}" />
				</div>
				<div class="row location-selector">
					<div class="col-xs-3">
						<label for="startLat">Start latitude</label>
						<g:textField type="text" class="form-control" data-location-lat=""
							id="startLat" name="startLat" value="${ride?.startLat}" />

					</div>
					<div class="col-xs-3">
						<label for="startLng">Start longitude</label>
						<g:textField type="text" class="form-control" data-location-lng=""
							id="startLng" name="startLng" value="${ride?.startLng}" />

					</div>

					<div class="col-xs-2">
						<label for="">&nbsp;</label><br> <a
							class="btn btn-primary select-onmap" data-toggle="modal"
							data-target="#mapModalStart"><span
							class="glyphicon glyphicon-map-marker"></span></a>
					</div>
				</div>
			</fieldset>

			<div class="modal fade" id="mapModalStart" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Cancel</button>
							<button type="button" class="btn btn-primary apply-location">Apply</button>
						</div>
					</div>
				</div>
			</div>


		</div>

		<div class="form-group location-stop">
			<fieldset>
				<legend>Stop position</legend>
				<div class="form-group">
					<label for="stopAddress">Stop address</label>
					<g:textField class="form-control" name="stopAddress"
						id="stopAddress" value="${ride?.stopAddress}" />
				</div>

				<div class="row location-selector">
					<div class="col-xs-3">
						<label for="stopLat">Stop latitude</label>
						<g:textField type="text" class="form-control" data-location-lat=""
							id="stopLat" name="stopLat" value="${ride?.stopLat}" />

					</div>
					<div class="col-xs-3">
						<label for="stopLng">Stop longitude</label>
						<g:textField type="text" class="form-control" data-location-lng=""
							id="stopLng" name="stopLng" value="${ride?.stopLng}" />

					</div>

					<div class="col-xs-2">
						<label for="">&nbsp;</label><br> <a
							class="btn btn-primary select-onmap" data-toggle="modal"
							data-target="#mapModalStop"><span
							class="glyphicon glyphicon-map-marker"></span></a>
					</div>
				</div>
			</fieldset>

			<div class="modal fade" id="mapModalStop" tabindex="-1" role="dialog"
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
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Cancel</button>
							<button type="button" class="btn btn-primary apply-location">Apply</button>
						</div>
					</div>
				</div>
			</div>

		</div>

		<div class="form-group">
			<label for="message">Message from user</label>
			<g:textArea class="form-control" name="message" id="message"
				value="${ride?.message}" />
		</div>

		<g:if test="${ride?.id}">
			<div class="form-group">
				<img
					src='<g:createLink  uri="/images/rides/${ride?.id}.jpg?=${new Date().getTime()}"/>' />

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

		<g:hiddenField name="id" value="${ride?.id}" />
	</g:uploadForm>



</body>
</html>