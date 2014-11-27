<%@ page import="com.dropbyke.Bike" %>
<%@ page import="com.dropbyke.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="admin"/>
    <title>Edit user</title>
</head>

<body>

<h2>
    <a class="button" href="<g:createLink action="index" id="${user.id}"/>"><span
            class="glyphicon glyphicon-chevron-left"></span></a> Edit user
</h2>

<div class="row">

    <div class="col-sm-6">
        <g:set var="actionLink">
            <g:createLink action="${actionName}" id="${user.id}"/>
        </g:set>

        <g:uploadForm url="${actionLink}" role="form">
            <div class="form-group">
                <label for="phone">Phone</label>
                <g:textField class="form-control" name="phone" id="phone"
                             value="${user.phone}"/>
            </div>

            <div class="form-group">
                <label for="phone">Name</label>
                <g:textField class="form-control" name="name" id="name"
                             value="${user.name}"/>
            </div>

            <div class="form-group">
                <label for="phone">Email</label>
                <g:textField class="form-control" name="email" id="email"
                             value="${user.email}"/>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-lg">Save</button>
            </div>

            <g:hiddenField name="id" value="${user.id}"/>
        </g:uploadForm>
    </div>

    <div class="col-sm-offset-1 col-sm-5 widthdrawform"
         data-url="<g:createLink controller="adminOperations" action="withdraw"/>" data-user="${user.id}">

        <div class="row">
            <h4>Sharing account</h4>
            <b>Sum</b>
            <span>$<span data-sum="${user.account.sum}"><g:formatNumber number="${user.account.sum / 100.0}"
                                                                        type="number" maxFractionDigits="2"/></span>
            </span>
            <g:link controller="adminOperations" action="index" params="[userId: user.id]">View operations</g:link>
        </div>

        <div class="row">
            <div style="padding: 1rem 0">
                <button class="btn btn-warning" data-toggle-form>Withdraw funds</button>
            </div>

            <div class="form-group" data-widthdrawform-form>
                <label>Sum to widthdaraw</label>

                <div class="row">
                    <div class="col-sm-4">
                        <input class="form-control" data-widthdraw-sum-input type="number"/>
                    </div>

                    <div class="col-sm-4">
                        <button class="btn btn-danger" data-submit>Submit</button>
                    </div>
                </div>
            </div>

        </div>

        <div class="row">

            <h4>Cards</h4>
            <table class="table table-striped">
                <tr>
                    <td>Number</td>
                    <td>Name</td>
                    <td>Expires</td>
                    <td>CVC</td>
                </tr>
                <g:each in="${user.cards}" var="card">
                    <tr>
                        <td>${card.number}</td>
                        <td>${card.name}</td>
                        <td>${card.expire}</td>
                        <td>${card.cvc}</td>
                    </tr>
                </g:each>

            </table>
        </div>

    </div>

</div>

</body>
</html>