<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="admin"/>
    <title>Users</title>
</head>

<body>

<h2>
    Users <a type="button" class="btn btn-primary"
             href="<g:createLink action="add"/>"><span
            class="glyphicon glyphicon-plus"></span> Add user
</a>
</h2>

<table class="table users-table table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="phone" title="Phone"/>
        <g:sortableColumn property="email" title="Email"/>
        <g:sortableColumn property="name" title="Name"/>
        <g:sortableColumn property="account.sum" title="Sum to pay, \$"/>
        <g:sortableColumn property="isOnline" title="Status"/>
        <g:sortableColumn property="bikes.count" title="Bikes"/>
        <!--  <th>Actions</th>-->
    </tr>

    </thead>

    <g:each in="${users}" var="user">
        <tr>
            <td><g:link action="edit" id="${user.id}">
                ${user.username}
            </g:link></td>
            <td>
                ${user.email ?: "not set"}
            </td>
            <td>
                ${user.name}
            </td>
            <td>
                ${user.account.sum/100} <g:link action="index" controller="adminOperations" params="[userId: user.id]">
                    View operations
                </g:link>
            </td>
            <td><g:if test="${user.isOnline}">
                <span
                        class="glyphicon glyphicon-user user-status-online text-success"></span>
            </g:if> <g:else>
                <span
                        class="glyphicon glyphicon-user user-status-offline text-danger"></span>
            </g:else></td>
            <td><g:link action="index" controller="adminBikes" params="[userId: user.id]">
                ${user.bikes.size()}
            </g:link></td>
        </tr>
    </g:each>
</table>
<g:paginate total="${usersCount}"/>

</body>
</html>