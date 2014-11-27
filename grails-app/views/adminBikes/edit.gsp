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

	<g:if test="${locked}">
		<h5>
			Bike in use
			<g:link action="stopUsage" id="${id}" class="btn btn-danger btn-sm">Stop usage</g:link>
		</h5>
		<table class="table">
			<tr>
				<th>Title</th>
				<td>
					${title}
				</td>
			</tr>
			<tr>
				<th>SKU</th>
				<td>
					${sku}
				</td>
			</tr>
			<tr>
				<th>Lock password</th>
				<td>
					${lockPassword}
				</td>
			</tr>
			<tr>
				<th>Message from last user</th>
				<td>
					${messageFromLastUser}
				</td>
			</tr>
			<tr>
				<th>Price per hour</th>
				<td>
					${priceRate}
				</td>
			</tr>
			<tr>
				<th>Location</th>
				<td>
					${lat} | ${lng}
				</td>
			</tr>
			<tr>
				<th>Address</th>
				<td>
					${address}
				</td>
			</tr>
		</table>

	</g:if>
	<g:else>

		<g:uploadForm url="${actionLink}" role="form">

			<div class="form-group">
				<button type="submit" class="btn btn-primary btn-lg">Save</button>
			</div>
			
			<div class="form-group">
				<label for="active">Active (sharing)</label>
				<g:if test="${!id}">
					<g:set var="isactive" value="true"/>
				</g:if>
				<g:else>
					<g:set var="isactive" value="${active}"/>
				</g:else>
				<g:checkBox name="active" value="1" checked="${isactive}" id="active" />
			</div>

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
				<label for="lockPassword">Lock password</label>
				<g:textField name="lockPassword" class="form-control"
					value="${lockPassword}" id="lockPassword" placeholder="" />
			</div>

			<div class="form-group">
				<label for="messageFromLastUser">Message from last user</label>
				<g:textArea name="messageFromLastUser" class="form-control"
					value="${messageFromLastUser}" id="messageFromLastUser"
					placeholder="" />
			</div>

			<div class="form-group">
				<label for="priceRate">Price</label>
				<g:textField name="priceRate" class="form-control"
					value="${priceRate}" id="priceRate"
					placeholder="Enter price per hour" />
			</div>

			<g:if test="${hasImage}">
				<div class="form-group">
					<img
						src='<g:createLink  uri="/images/bikes/${id}.jpg?=${new Date().getTime()}"/>' />

				</div>
			</g:if>

			<div class="form-group">
				<label for="photo">Photo</label> <input type="file" name="photo"
					id="photo" />
				<p>Should be &lt; 100kB, jpg, gif, png</p>
			</div>

			<div class="form-group location-start">
				<fieldset>
					<legend>Location</legend>

					<div class="form-group">
						<label for="address">Address</label>
						<g:textField class="form-control" name="address" id="address"
							data-location-address="" value="${address}" />
					</div>
					<div class="row location-selector">
						<div class="col-xs-3">
							<label for="lat">Latitude</label>
							<g:textField type="text" class="form-control"
								data-location-lat="" id="lat" name="lat" value="${lat}" />

						</div>
						<div class="col-xs-3">
							<label for="lng">Longitude</label>
							<g:textField type="text" class="form-control"
								data-location-lng="" id="lng" name="lng" value="${lng}" />

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

			<div class="form-group">
				<button type="submit" class="btn btn-primary btn-lg">Submit</button>
			</div>

			<g:hiddenField name="id" value="${id}" />

		</g:uploadForm>

	</g:else>



</body>
</html>