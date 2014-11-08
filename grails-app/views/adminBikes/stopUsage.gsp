<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Stop bike ride</title>
</head>
<body>

	<h2>
		<a class="button"
			href="<g:createLink action="index" id="${bike.id}" />"><span
			class="glyphicon glyphicon-chevron-left"></span></a> Stop ride
	</h2>

	<g:set var="actionLink">
		<g:createLink action="${actionName}" id="${bike.id}" />
	</g:set>

	<g:uploadForm url="${actionLink}" role="form">

		<div class="form-group">
			<b>User</b>:
			<g:link controller="adminUsers" action="edit" id="${user.id}">
				${
					user.phone
				}
				${user.name}
			</g:link>
		</div>

		<div class="form-group">
			<b>Bike</b>:
			<g:link controller="adminBikes" action="edit" id="${bike.id}">
				${bike.title}
			</g:link>
		</div>

		<div class="form-group">
			<label for="stopTime">Start timestamp</label>
			<p>
				${ride.startTime}
			</p>
		</div>

		<div class="form-group">
			<label for="stopTime">Stop timestamp</label>
			<g:textField class="form-control datetimepicker" name="stopTime"
				id="stopTime" value="${ride.stopTime}" />
		</div>


		<div class="form-group location-start">
			<fieldset>
				<legend>Start position</legend>
				<div class="form-group">
					<label for="startAddress">Start address</label>
					<p>
						${ride.startAddress}
					</p>
				</div>
				<div class="row location-selector">
					<div class="col-xs-3">
						<label for="startLat">Start latitude</label>
						<p>
							${ride.startLat}
						</p>
					</div>
					<div class="col-xs-3">
						<label for="startLng">Start longitude</label>
						<p>
							${ride.startLng}
						</p>
					</div>

				</div>
			</fieldset>

		</div>

		<div class="form-group location-stop">
			<fieldset>
				<legend>Stop position</legend>
				<div class="form-group">
					<label for="stopAddress">Stop address</label>
					<g:textField class="form-control" name="stopAddress"
						id="stopAddress" value="${ride.stopAddress}" />
				</div>

				<div class="row location-selector">
					<div class="col-xs-3">
						<label for="stopLat">Stop latitude</label>
						<g:textField type="text" class="form-control" data-location-lat=""
							id="stopLat" name="stopLat" value="${ride.stopLat}" />

					</div>
					<div class="col-xs-3">
						<label for="stopLng">Stop longitude</label>
						<g:textField type="text" class="form-control" data-location-lng=""
							id="stopLng" name="stopLng" value="${ride.stopLng}" />

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
			<label for="lockPassword">Lock password</label>
			<g:textField class="form-control" name="lockPassword"
				id="lockPassword" value="${bike.lockPassword}" />
		</div>
		<div class="form-group">
			<label for="message">Message from last user</label>
			<g:textArea class="form-control" name="message" id="message"
				value="${bike.messageFromLastUser}" />
		</div>

		<g:if test="${ride.id}">
			<div class="form-group">
				<img
					src='<g:createLink  uri="/images/rides/${ride.id}.jpg?=${new Date().getTime()}"/>' />

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

		<g:hiddenField name="id" value="${bike.id}" />
	</g:uploadForm>





</body>
</html>