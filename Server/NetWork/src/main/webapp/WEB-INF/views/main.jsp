<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="de">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Angular Material style sheet -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/main.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/menue.css' />" rel="stylesheet"></link>
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

    <script type="text/javascript" src="<c:url value='/static/js/service/lizenzService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/hardwareService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/softwareService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/userService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/changelogService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/netzService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/viewService.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/service/messageService.js' />"></script>


    <script type="text/javascript" src="<c:url value='/static/js/directive/main.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/massage.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/dialog.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/settings.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/view.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/wizard.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/directive/component.js' />"></script>


    <script type="text/javascript" src="<c:url value='/static/js/controller/clientsController.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/controller/userController.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/controller/netzController.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/controller/serverController.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/controller/changelogController.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/controller/lizenzController.js' />"></script>
    <script type="text/javascript" src="<c:url value='/static/js/main.js' />"></script>


    <md-toolbar style="background-color: transparent">
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

    <div ng-if="showConfig.view.logo" style="padding-left: 40px;position: absolute;top:60px">
        <img src="static/img/reservix-logo.png">
    </div>

    <div ng-show="showConfig.view.changelog" >
        <view-changelog />
    </div>
    <div ng-show="showConfig.view.clientsHardware || showConfig.view.sonstigeHardware" >
        <view-hardware />
    </div>

    <div ng-if="showConfig.view.lizenz" >
        <view-lizenz  />
    </div>

    <div ng-if="showConfig.view.software"  view-software></div>
    <div ng-if="showConfig.view.user"  view-userdetails></div>
    <div ng-if="showConfig.view.setting"  view-settings></div>

    <div ng-if="showConfig.view.userwizard" view-userwizard></div>

    <div layout="row" class="fixedBottom">
        <div flex layout-margin="0"></div>
        <div layout-margin="0" style="font-size: 12px">@ Manfred Fischer - NetView Version: 1.5</div>
        <div flex layout-margin="0"></div>
        <div layout-margin="0" style="font-size: 12px">Angemeldet: {{loginUser}}</div>
    </div>
</body>

</html>