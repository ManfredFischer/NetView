<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/login.css' />" rel="stylesheet"></link>
</head>
<body >

<div style="padding: 50">
    <img src="static/img/reservix-logo.png">
</div>

<form action="login" method="post">
    <div class="page">
        <md-card md-theme-watch style="padding: 10px;background-color:#FCFCFC;border-radius: 10px">
            <md-card-actions layout="row" layout-align="end center">
                <div layout-gt-sm="column">
                    <input type="text" required name="username" class="css-input" ng-model="username"
                           placeholder="Username..."/>
                    <input type="password" required name="password" class="css-input" ng-model="password"
                           placeholder="Password..."/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" id="token"/>
                    <button type="submit">Login</button>
                </div>
            </md-card-actions>
        </md-card>
    </div>
</form>

</body>
</html>