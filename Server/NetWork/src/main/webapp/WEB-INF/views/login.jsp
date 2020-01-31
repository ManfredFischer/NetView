<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Angular Material style sheet -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/login.css' />" rel="stylesheet"></link>


</head>
<body ng-app="BlankApp" ng-cloak ng-controller="DemoBasicCtrl as ctrl">

<div style="padding: 50">
    <img src="static/img/reservix-logo.png">
</div>

<form action="login" method="post">
    <div class="page">
        <md-card md-theme-watch style="padding: 10px">
            <md-card-actions layout="row" layout-align="end center">
                <div layout-gt-sm="column">
                    <input type="text" required name="username" class="css-input" ng-model="username"
                           placeholder="Username..."/>
                    <input type="password" required name="password" class="css-input" ng-model="password"
                           placeholder="Password..."/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" id="token"/>
                    <md-button type="submit">Login</md-button>
                </div>
            </md-card-actions>
        </md-card>
    </div>


</form>


<!-- Angular Material requires Angular.js Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-aria.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-messages.min.js"></script>
<script data-require="jquery@*" data-semver="2.1.1"
        src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script src="static/framework/ng-file-upload-shim.min.js"></script>
<script src="static/framework/ng-file-upload.min.js"></script>

<!-- Angular Material Library -->
<script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>

<!-- Your application bootstrap  -->
<script type="text/javascript" src="<c:url value='/static/js/login.js' />"></script>

</body>
</html>