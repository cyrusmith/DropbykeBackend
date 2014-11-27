<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="admin"/>
    <title>Sharing operations</title>
</head>

<body>

<h2>
    Sharing operations <g:if test="${user}">
    for user
    <a href="<g:createLink action="edit" id="${user.id}" controller="adminUsers"/>">${user.name} ${user.phone}</a>

</g:if>

</a>
</h2>

<table class="table users-table table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="op.account.user" title="User"/>
        <g:sortableColumn property="class" title="Type"/>
        <g:sortableColumn property="amount" title="Amount"/>
        <g:sortableColumn property="sumBefore" title="Account before"/>
        <g:sortableColumn property="sumAfter" title="Account after"/>
        <g:sortableColumn property="created" title="Date"/>
    </tr>

    </thead>
    <g:each in="${operations}" var="op">
        <tr>
            <td>
                <g:link controller="adminUsers" action="edit" id="${op.account.user.id}">
                    ${op.account.user.name} ${op.account.user.phone}
                </g:link>
            </td>
            <td>
                <g:if test="${op instanceof com.dropbyke.money.CheckoutOperation}">
                    Income
                </g:if>
                <g:elseif test="${op instanceof com.dropbyke.money.WithdrawOperation}">
                    Widthdraw
                </g:elseif>
            </td>
            <td>
                <g:formatNumber number="${op.amount / 100.0}" type="number" maxFractionDigits="2"/>

            </td>
            <td>
                <g:formatNumber number="${op.sumBefore / 100.0}" type="number" maxFractionDigits="2"/>

            </td>
            <td>
                <g:formatNumber number="${op.sumAfter / 100.0}" type="number" maxFractionDigits="2"/>

            </td>
            <td>
                ${op.created}
            </td>
        </tr>
    </g:each>
</table>
<g:paginate total="${opsCount}"/>

</body>
</html>