<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="admin" />
<title>Charges</title>
</head>
<body>

	<h2>Charges</h2>

	<table class="table users-table table-striped">
		<thead>
			<tr>
				<g:sortableColumn property="user" title="User" />
				<g:sortableColumn property="bike" title="Bike" />
				<g:sortableColumn property="amount" title="Amount in USD" />
				<th>Card number</th>
				<th>Stripe charge id</th>
			</tr>

		</thead>
		<g:each in="${charges}" var="charge">
			<tr>
				<td><g:if test="${charge.user.name}">
						${charge.user.name} (${charge.user.phone})
						</g:if> <g:else>
						${charge.user.phone}
					</g:else></td>
				<td>
					${charge.ride.bike.title}
				</td>
				<td>
					${charge.amount/100}
				</td>
				<td>
					${charge.cardNumber}
				</td>
				<td>
					${charge.stripeChargeId}
				</td>
			</tr>
		</g:each>
	</table>

	<g:paginate total="$chargesCount}" />

</body>
</html>