<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="de">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Angular Material style sheet -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/main.css' />" rel="stylesheet"></link>
</head>

<body class="body" ng-app="MainPage" ng-cloak ng-controller="ListCtrl as ctrl">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-animate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-aria.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-messages.min.js"></script>
    <script src="static/framework/ng-file-upload-shim.min.js"></script>
    <script src="static/framework/ng-file-upload.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>
    <script type="text/javascript" src="<c:url value='/static/js/initial.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/data/loadValues.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/SoftwareService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/HardwareService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/UserService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/main.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/dialog.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/settings.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/view.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/wizard.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/main.js' />"></script>

    <md-toolbar style="background-color: transparent;">
        <div my-menue></div>
        <div layout-gt-sm="row" ng-show="showMainConfig.showLocation">
            <div flex-gt-sm></div>
            <div style="position: relative;top: 10%; transform: translateY(15%);margin-right: 10px">
                <select ng-Model="selectedLocation">
                    <option ng-repeat="location in LocationList" value={{location}}>{{location.displayname}}</option>
                </select>
            </div>
        </div>
    </md-toolbar>

    <div style="padding-left: 50px;position: absolute">
        <img src="static/img/reservix-logo.png">
    </div>

    <div ng-show="isLizenzVewaltungSelected" my-lizenz></div>
    <div ng-show="isNetzwerkVewaltungSelected" my-hardware></div>
    <div ng-show="isServerVerwaltungSelected" my-netz></div>
    <div ng-show="isMobileVewaltungSelected" my-mobile></div>
    <div ng-show="isSoftwareSelected" my-software></div>
    <div ng-show="isUserVerwaltungSelected" my-userdetails></div>
    <div ng-show="isSettingsSelected" my-settings></div>
    <div ng-show="showMainConfig.showChangeLogView" my-changelogview></div>
    <div ng-show="showMainConfig.showGoogle" my-overview></div>
    <div ng-show="showMainConfig.showAddUserWizard" my-userwizard></div>

</body>

</html>