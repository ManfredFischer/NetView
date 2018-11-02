<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Angular Material style sheet -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/main.css' />" rel="stylesheet"></link>
</head>

<body class="body" ng-app="MainPage" ng-cloak ng-controller="ListCtrl as ctrl">
    <md-toolbar class="md-menu-toolbar">
        <div layout="row">
            <md-menu-bar>
                <md-menu>
                    <button ng-click="$mdMenu.open()">
                        Start
                    </button>
                    <md-menu-content>
                        <md-menu-item>
                            <md-button ng-click="showSettings()">
                                Settings
                            </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <form action="logout" method="post">
                                <md-button type="submit">Logout</md-button>
                            </form>
                        </md-menu-item>
                    </md-menu-content>
                </md-menu>
                <md-menu>
                    <button ng-click="$mdMenu.open()">
                        Netzwerk Verwaltung
                    </button>
                    <md-menu-content>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showNetzwerk')">
                                Anzeigen
                            </md-button>
                        </md-menu-item>
						<md-menu-item>
                           <md-button ng-click="ctrl.selectMenu('showLizenz')">
                               Lizenz Anzeigen
                           </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('serverAdd')">
                                Server hinzuf�gen
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('lizenzAdd')">
                                Lizenz hinzufügen
                            </md-button>
                        </md-menu-item>
                    </md-menu-content>
                </md-menu>
                <md-menu>
                    <button ng-click="$mdMenu.open()">
                        User Verwaltung
                    </button>
                    <md-menu-content>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showUser')">
                                Anzeigen
                            </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showCreateUser')">
                                User hinzufügen
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showUpdateUser')">
                                User aktualisieren
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showDeleteUser')">
                                User entfernen
                            </md-button>
                        </md-menu-item>
                </md-menu>
                <md-menu>
                    <button ng-click="$mdMenu.open()">
                        Daten Verwalten
                    </button>
                    <md-menu-content>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showVorlagen')">
                                Vorlagen
                            </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showLaptops')">
                                Laptops
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('schowLocation')">
                                Standorte
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showHandy')">
                                Handys
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showDepartment')">
                                Abteilung
                            </md-button>
                        </md-menu-item>
                </md-menu>
            </md-menu-bar>
        </div>
    </md-toolbar>
    
    <md-toolbar style="color:'green'">
     <div class="md-toolbar-tools">
      <h2 class="md-flex">{{infoFeld}}</h2>
     </div>
    </md-toolbar>
    
    <md-content class="md-padding" ng-show="isLizenzVewaltungSelected">
        <div class="page">
        
             <form ng-submit="ctrl.selectUpdateUser(ctrl.searchText,$event)">
               <md-autocomplete md-item-text="item.display" md-search-text="ctrl.searchUserText"
                  md-items="item in ctrl.querySearch(ctrl.searchUserText)" placeholder="Search...">
                 <md-item-template>
                    <span md-highlight-text="ctrl.searchUserText" md-highlight-flags="^i">{{item.display}}</span>
                 </md-item-template>
              </md-autocomplete>
            </form>
            
            <md-list ng-controller="ListCtrl" ng-cloak>
                <md-list-item ng-repeat="hw in hardware" ng-click="showHardwareInformation(hw)" class="md-2-line">
                    <div flex="10" class="md-list-item-text" layout="column">
                        <h3>{{ hw.hostname }}</h3>
                        <h4>{{ hw.aktivUsername }}</h4>
                    </div>
                    <div flex="30" class="md-list-item-text" layout="column">
                        <h3>{{ hw.model}}</h3>
                        <h4>{{ hw.sn }}</h4>
                    </div>
                    <div class="md-list-item-text" layout="column">
                        <h3>{{ hw.cpu}}</h3>
                        <h4>{{ hw.ram }}</h4>
                    </div>
                </md-list-item>
            </md-list>
        </div>
    </md-content>

    <md-content class="md-padding" ng-show="isNetzwerkVewaltungSelected">
        <div class="page">
        
             <form ng-submit="ctrl.selectUpdateUser(ctrl.searchText,$event)">
               <md-autocomplete md-item-text="item.display" md-search-text="ctrl.searchUserText"
                  md-items="item in ctrl.querySearch(ctrl.searchUserText)" placeholder="Search...">
                 <md-item-template>
                    <span md-highlight-text="ctrl.searchUserText" md-highlight-flags="^i">{{item.display}}</span>
                 </md-item-template>
              </md-autocomplete>
            </form>
            
            <md-list ng-controller="ListCtrl" ng-cloak>
                <md-list-item ng-repeat="hw in hardware" ng-click="showHardwareInformation(hw)" class="md-2-line">
                    <div flex="10" class="md-list-item-text" layout="column">
                        <h3>{{ hw.hostname }}</h3>
                        <h4>{{ hw.aktivUsername }}</h4>
                    </div>
                    <div flex="30" class="md-list-item-text" layout="column">
                        <h3>{{ hw.model}}</h3>
                        <h4>{{ hw.sn }}</h4>
                    </div>
                    <div class="md-list-item-text" layout="column">
                        <h3>{{ hw.cpu}}</h3>
                        <h4>{{ hw.ram }}</h4>
                    </div>
                </md-list-item>
            </md-list>
        </div>
    </md-content>

    <md-content class="md-padding" ng-show="isUserVerwaltungSelected">
        <div class="page">
            <md-content class="page-container-tab">
                <div layout-gt-sm="row" layout-align="center center">

                    <div>
                        <md-card class="page-tab">
                            <div layout-gt-sm="row" layout-align="space-between center">
								
                                    <md-button class="md-raised" ng-click="showUserInformation()" md-colors="{color: 'green'}">Details</md-button>
                                
                                    
                                        <md-button ng-show="isUserVerwaltungUpdateSelected" ng-disabled="true" ng-click="ctrl.updateUserInformation()" type="submit"
                                            class="md-raised">Aktualisieren</md-button>
                                        <md-button ng-show="isUserVerwaltungCreateSelected" ng-disabled="true" ng-click="ctrl.createUserInformation()" class="md-raised">Hinzufügen</md-button>
                                        <md-button ng-show="isUserDisableSelected" ng-disabled="true" ng-click="ctrl.disableUser(false)" class="md-raised">Austritt</md-button>
                                        <md-button ng-show="isUserDisableSelected" ng-disabled="true" ng-click="ctrl.disableUser(true)" class="md-raised">Sofort</md-button>
                                    
                                                               
                            </div>
                            <md-divider></md-divider>
                            <hr/>                            
                            <form ng-submit="ctrl.selectUpdateUser(ctrl.searchText,$event)">
                                <md-autocomplete ng-show="showUserSearch" md-item-text="item.display" md-search-text="ctrl.searchUserText"
                                    md-items="item in ctrl.querySearch(ctrl.searchUserText)" placeholder="Username...">
                                    <md-item-template>
                                        <span md-highlight-text="ctrl.searchUserText" md-highlight-flags="^i">{{item.display}}</span>
                                    </md-item-template>
                                </md-autocomplete>
                            </form>

                            <div layout-gt-sm="row" ng-show="showUserInfos">
                                <md-input-container class="md-block" flex-gt-sm>
                                    <label>Vorname</label>
                                    <input ng-model="userInformation.firstname"> </md-input-container>
                                <md-input-container class="md-block" flex-gt-sm>
                                    <label>Nachname</label>
                                    <input ng-model="userInformation.lastname">
                                </md-input-container>
                            </div>

                            <div layout-gt-sm="row" ng-show="showUserInfos">
                                <md-input-container style="width: 100%">
                                    <label>Abteilung</label>
                                    <md-select ng-Model="laptop.lid">
                                        <md-option ng-repeat="laptop in departmentList" value="{{ laptop.id }}">{{laptop.model }}</md-option>
                                    </md-select>
                                </md-input-container>
                            </div>

                            <div layout-gt-sm="row"  ng-show="showUserInfos">
                                <md-input-container style="width: 100%">
                                    <label>Standort</label>
                                    <md-select ng-Model="userInformation.lid">
                                        <md-option ng-repeat="location in LocationList" value="{{ location.lid }}">{{location.displayname }}</md-option>
                                    </md-select>
                                </md-input-container>
                            </div>
                            
                            <div layout-gt-sm="row"  ng-show="showUserDisableDate" style="width: 100%">
                                <md-datepicker ng-model="ctrl.myDate" md-placeholder="Austrittsdatum"></md-datepicker>                         
                            </div>
                        </md-card>
                    </div>
                    <div class="my-lg-0" style="padding-left:20px" ng-show="isUserVerwaltungCreateSelected">
                        <md-card class="page-tab" style="width:400px;padding:20px">
                            <div layout-gt-sm="row" layout-align="space-between center">
                                <div>Zubeh�r</div>
                                <md-switch class="md-secondary"> Vorhanden </md-switch>
                            </div>
                            <div layout-gt-sm="row">
                                <md-input-container style="width: 100%">
                                    <label>Monitor Anzahl</label>
                                    <md-select ng-Model="selectedMonitorCount">
                                        <md-option ng-repeat="monitor in monitorCount" value="{{ monitor }}">{{monitor }}</md-option>
                                    </md-select>
                                </md-input-container>
                            </div>
                            <div layout-gt-sm="row">
                                <md-input-container style="width: 100%">
                                    <label>Laptop</label>
                                    <md-select ng-Model="selectedLaptop">
                                        <md-option ng-repeat="laptop in laptopList" value="{{ laptop.id }}">{{laptop.name }}</md-option>
                                    </md-select>
                                </md-input-container>
                            </div>
                            <div layout-gt-sm="row">
                                <md-input-container style="width: 100%">
                                    <label>Handy</label>
                                    <md-select ng-Model="selectedHandy">
                                        <md-option ng-repeat="handy in HandyList" value="{{ handy.id }}">{{handy.name }}</md-option>
                                    </md-select>
                                </md-input-container>
                            </div>
                            <div>
                                <md-checkbox md-no-ink ng-model="selectedPC" class="md-primary">
                                    PC
                                </md-checkbox>
                            </div>
                            <div>
                                <md-checkbox md-no-ink ng-model="selectedTelephon" class="md-primary">
                                    Telefon
                                </md-checkbox>
                            </div>
                        </md-card>
                    </div>
                </div>
            </md-content>
        </div>
    </md-content>


    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-animate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-aria.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-messages.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>

    <script type="text/javascript" src="<c:url value='/static/js/main.js' />"></script>
</body>

</html>