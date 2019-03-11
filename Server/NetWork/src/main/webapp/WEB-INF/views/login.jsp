<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html >
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Angular Material style sheet -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/login.css' />"  rel="stylesheet"></link>

   
</head>
<body ng-app="BlankApp" ng-cloak>
<div ng-controller="DemoBasicCtrl as ctrl" ng-cloak>
        <div layout="row" layout-align="space-between start">
            <div></div>
            <div>
            
                <form action="login" method="post" class="form-inline my-2 my-lg-0">
                <md-input-container class="md-caption">
                 <label>Username</label>
                 <input required name="username"  ng-model="username">
                </md-input-container>
                <md-input-container class="md-caption">
                 <label>Password</label>
                 <input required name="password" type="password" ng-model="password">
				</md-input-container>
				
                 <input type="hidden" name="_csrf" value="${_csrf.token}" id="token"/>
				<md-input-container class="md-caption">
                <md-button type="submit">Login</md-button>
                </md-input-container> 
                </form>                         
            </div>
        </div>

    <md-content class="page-container">
        <md-card class="page">
            <p>Change Password</p>
            <md-input-container class="md-caption">
                <label>Username</label>
                <input required name="changeUsername" ng-model="project.username">
            </md-input-container>
            <md-input-container class="md-caption">
                <label>Password</label>
                <input type="password" required name="changePassword" ng-model="project.cpw">
            </md-input-container>
            <md-input-container class="md-caption">
                <label>new Password</label>
                <input type="password" required name="changeNewPssword" ng-model="project.cnpw">
            </md-input-container>
            <md-input-container class="md-caption">
                <label>replay Password</label>
                <input type="password" required name="changeReplayPassword" ng-model="project.crpw">
            </md-input-container>
            <md-button ng-click="change()">Change</md-button>
        </md-card>
    </md-content>
</div>



<!-- Angular Material requires Angular.js Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-aria.min.js"></script>

<!-- Angular Material Library -->
<script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>

<!-- Your application bootstrap  -->
<script type="text/javascript" src="<c:url value='/static/js/login.js' />"> </script>

</body>
</html>