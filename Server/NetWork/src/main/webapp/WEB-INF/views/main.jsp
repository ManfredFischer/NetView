<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Angular Material style sheet -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/main.css' />" rel="stylesheet"></link>
</head>
<body class="body" ng-app="MainPage" ng-cloak>
   <div ng-controller="ListCtrl as ctrl" ng-cloak>
              

    <md-content class="page-container">
    
    	<div layout-gt-sm="row" layout-align="space-between center">
    	<div class="my-lg-0">
          <md-button type="submit">Settings</md-button>
        </div>
    	
    	<form action="logout" method="post" class="my-lg-0">
          <md-button type="submit" class="md-raised">Logout</md-button>
        </form> 
        </div>
        <md-card class="page">
            <md-content>
                <md-tabs md-dynamic-height  md-border-bottom>
                 <md-tab label="Netzwerk Informationen">
                        <md-content class="md-padding">
                           <form ng-submit="ctrl.selectHardware(ctrl.searchHardwareText,$event)">
                                        <md-autocomplete 
                                                md-item-text="item.display"
                                                md-search-text="ctrl.searchHardwareText"
                                                md-items="item in ctrl.queryHardwareSearch(ctrl.searchHardwareText)"
                                                placeholder="Search...">
                                            <md-item-template>
                                                <span md-highlight-text="ctrl.searchHardwareText" md-highlight-flags="^i">{{item.display}}</span>
                                            </md-item-template>
                                        </md-autocomplete>
                            </form>                        
                        
                            <md-list ng-controller="ListCtrl" ng-cloak>
                                <md-list-item ng-repeat="hw in hardware" ng-click="showHardwareInformation(hw)"
                                              class="md-2-line">
                                    <div flex="20" class="md-list-item-text" layout="column">
                                        <h3>{{ hw.hostname }}</h3>
                                        <h4>{{ hw.aktivUsername }}</h4>
                                    </div>
                                    <div class="md-list-item-text" layout="column">
                                        <h3>{{ hw.model}}</h3>
                                        <h4>{{ hw.sn }}</h4>
                                    </div>
                                    <div class="md-list-item-text" layout="column">
                                        <h3>{{ hw.cpu}}</h3>
                                        <h3>{{ hw.ram }}</h4>
                                    </div>
                                </md-list-item>
                            </md-list>
                        </md-content>
                    </md-tab>
                    
                    <md-tab label="User Verwaltung">
                        <md-content class="page-container-tab">
                            <md-card class="page-tab">
                            	<div layout-gt-sm="row" layout-align="space-between center">
                            	  <div class="my-lg-0" ng-show="config.create">
                                   </div> 
                            	  <div class="my-lg-0" ng-show="!config.create">
                                    <md-button ng-click="showUserInformation()" class="md-raised" md-colors="{color: 'green', 'border-color': 'primary-600'}">Details</md-button>
                                    <md-button ng-click="clearUserInformaiton()" class="md-raised" md-colors="{color: 'red', 'border-color': 'primary-600'}">Clear</md-button>
                                  </div>  
 								  <md-switch ng-change="changeUserUpdateInsert()" class="md-secondary" ng-model="config.create"> Erstellen </md-switch>
                                </div>
                                 
                                  <form ng-submit="ctrl.selectUpdateUser(ctrl.searchText,$event)">
                                        <md-autocomplete ng-show="!config.create"
                                                md-item-text="item.display"
                                                md-search-text="ctrl.searchUserText"
                                                md-items="item in ctrl.querySearch(ctrl.searchUserText)"
                                                placeholder="Username...">
                                            <md-item-template>
                                                <span md-highlight-text="ctrl.searchUserText" md-highlight-flags="^i">{{item.display}}</span>
                                            </md-item-template>
                                        </md-autocomplete>
                                 </form>
                                    
                                 <div>
                                    <div layout-gt-sm="row">
                                        <md-input-container class="md-block" flex-gt-sm>
                                            <label>First name</label>
                                            <input ng-model="userInformation.firstname">
                                        </md-input-container>

                                        <md-input-container class="md-block" flex-gt-sm>
                                            <label>Last name</label>
                                            <input ng-model="userInformation.lastname">
                                        </md-input-container>
                                    </div>

                                    <div layout-gt-sm="row">
                                        <md-input-container class="md-block" flex-gt-sm>
                                            <label>Department</label>
                                            <input ng-model="userInformation.department">
                                        </md-input-container>

                                        <md-input-container class="md-block" flex-gt-sm>
                                            <label>Position</label>
                                            <input ng-model="userInformation.title">
                                        </md-input-container>
                                    </div>
                                    <div layout-gt-sm="row">
                                        <md-input-container class="md-block" flex-gt-sm>
                                            <label>Telefon</label>
                                            <input ng-model="userInformation.telephoneNumber">
                                        </md-input-container>
                                        
                                        <md-input-container class="md-block" flex-gt-sm>
                                            <label>Mobile</label>
                                            <input ng-model="userInformation.mobile">
                                        </md-input-container>
                                    </div>
									<div layout-gt-sm="row">
                                     <md-input-container style="width: 100%">
                                        <label>Standort</label>
                                         <md-select ng-Model="userInformation.lid" name="favoriteColor" ng-model="favoriteColor">
    										 <md-option ng-repeat="location in LocationList" value="{{ location.lid }}">{{ location.displayname }}</md-option>
                                         </md-select>
                                     </md-input-container>
                                     <md-input-container class="md-block" flex-gt-sm>
                                         <md-button ng-show="!config.create" ng-disabled="config.userdetails" ng-click="ctrl.updateUserInformation()" type="submit" class="md-raised" >Refresh</md-button>
                                         <md-button ng-show="config.create" ng-click="ctrl.createUserInformation()" type="submit">Hinzufügen</md-button>
                                     </md-input-container>
									</div>
                                </div>
                            </md-card>
                        </md-content>
                    </md-tab>

                </md-tabs>
            </md-content>
        </md-card>
    </md-content>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-aria.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-messages.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>

<script type="text/javascript" src="<c:url value='/static/js/main.js' />"></script>

</body>
</html>