<%--
  Created by IntelliJ IDEA.
  User: cyrusmith
  Date: 26.11.2014
  Time: 18:02
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin"/>
    <title>Add ride</title>
</head>

<body>

<g:uploadForm action="addRides" role="form">

    <p>
        User id: <g:textField name="userId" type="number" value="2"/>
    </p>

    <p>
        Bike id: <g:textField name="bikeId" type="number" value="1"/>
    </p>

    <p>
        Start address: <g:textField name="startAddress" value="ул. Бейвеля, 20, Челябинск, Челябинская область"/>  Start lat: <g:textField name="startLat" value="55.194722"/> Start lng: <g:textField name="startLng" value="61.282952"/> Start time: <g:textField name="startTime" type="number" value="${(int)Math.floor(new java.util.Date().getTime()/1000 - 1000)}" class="datetimepicker"/>
    </p>

    <p>
        Stop address: <g:textField name="stopAddress" value="ул. 40-летия Победы, 10"/>  Stop lat: <g:textField name="stopLat" value="55.190463"/> Stop lng: <g:textField name="stopLng" value="61.292303"/> Stop time: <g:textField name="stopTime" type="number" value="${(int)Math.floor(new java.util.Date().getTime()/1000)}" class="datetimepicker"/>
    </p>

    <p>
        Sum <g:textField name="sum" type="number" value="500"/>
    </p>

    <p>
        Message <g:textArea name="message" value="Was  not able to reach the speed of light"/>
    </p>

    <g:submitButton name="submit" value="Submit"/>

</g:uploadForm>

</body>
</html>