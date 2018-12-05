<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="de">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Angular Material style sheet -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
    <link href="<c:url value='/static/css/main.css' />" rel="stylesheet"></link>
</head>

<body class="body" ng-app="MainPage" ng-cloak ng-controller="ListCtrl as ctrl">
    <md-toolbar class="md-menu-toolbar">
        <div layout="row" style="background:#151515">
        	 <md-menu-bar>
        	 
        	     <md-menu>
                    <a href="main" class="menueStart" >
                        <img src="/NetView/static/img/reservix.jpg" width="30px" height="30px" style="border-radius:10px 25%;vertical-align:middle;margin-right:5px"></img>
                    </a>
                </md-menu>
        	    
           
                <md-menu>
                    <div ng-click="$mdMenu.open()" class="menue" >
                        Start
                    </div>
                    <md-menu-content>
                        <md-menu-item>
                            <md-button ng-click="showSettings()">
                                Settings
                            </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <md-button ng-click="logout()">Logout</md-button> 
                        </md-menu-item>
                    </md-menu-content>
                </md-menu>
                <md-menu>
                    <div ng-click="$mdMenu.open()" class="menue">
                        Netzwerk
                    </div>
                    <md-menu-content>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showNetzwerk')">
                                <img src="/NetView/static/img/netzwerk.jpg" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> Hardware
                            </md-button>
                        </md-menu-item>
						<md-menu-item>
                           <md-button ng-click="ctrl.selectMenu('showLizenz')">
                               <img src="/NetView/static/img/lizenz.png" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> Lizenzen
                           </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('serverAdd')">
                                <img src="/NetView/static/img/hardwareadd.png" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> Hardware hinzuf�gen
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('lizenzAdd')">
                                <img src="/NetView/static/img/lizenzadd.jpg" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> Lizenz hinzufügen
                            </md-button>
                        </md-menu-item>
                    </md-menu-content>
                </md-menu>
                <md-menu>
                    <div ng-click="$mdMenu.open()" class="menue">
                        User
                    </div>
                    <md-menu-content>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showUser')">
                                <img src="/NetView/static/img/user.png" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> Anzeigen
                            </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showCreateUser')">
                                <img src="/NetView/static/img/adduser.png" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> User hinzufügen
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showUpdateUser')">
                                <img src="/NetView/static/img/updateuser.png" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> User aktualisieren
                            </md-button>
                        </md-menu-item>
                        <md-menu-item>
                            <md-button ng-click="ctrl.selectMenu('showDeleteUser')">
                                <img src="/NetView/static/img/deleteUser.png" width="20px" height="20px" style="vertical-align:middle;margin-right:10px"></img> User entfernen
                            </md-button>
                        </md-menu-item>
                </md-menu>
                <md-menu class="menue">
                    <div ng-click="$mdMenu.open()" >
                        Daten
                    </div>
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
         <div layout-gt-sm="row" style="background:#F2F2F2;padding:20px;font-size:24px;border-bottom: 1px solid #D8D8D8">
      		<img ng-src={{menueIcon}} width="30px" height="30px" style="border-radius:10px 25%;vertical-align:middle;margin-right:15px"></img><span> {{infoFeld}}</span>
    	</div>
        
    </md-toolbar>
    
   
    
    <md-content class="md-padding" ng-show="isLizenzVewaltungSelected">
        <div class="page">
         	 <md-input-container class="md-block" flex-gt-sm>
            		<label>Search (Kategorie, Name, Key)</label>
            		<input ng-model="searchLizenz">
        	</md-input-container>
            
            <div class="ng-scope layout-row " style="padding-left:10px">
                    <div flex="10 " class="flex ">Name</div>
                    <div flex="10 " class="flex ">Kategorie</div>
                    <div flex="10 " class="flex ">Status</div>
                    <div class="flex ">Key</div>
            </div>
            <hr />
                
            <md-list ng-controller="ListCtrl" ng-cloak>
                <md-list-item ng-repeat="lizenz in ctrl.getLizenz()" class="md-1-line">
                    <div flex="10" class="md-list-item-text flex" layout="column">
                        {{ lizenz.name }}
                    </div>
                    <div flex="10" class="md-list-item-text flex" layout="column">
                        {{ lizenz.categorie }}
                    </div>
                    <div flex="10" class="md-list-item-text flex" layout="column">
                        {{ lizenz.state }}
                    </div>
                    <div class="md-list-item-text flex" layout="column">
                        {{ lizenz.key }}
                    </div>
                    <div flex="10" class="md-list-item-text flex" layout="column">
                     <md-button class="md-raised" style="margin-bottom:10px" ng-click="" md-colors="{color: 'red'}">Dell</md-button>
                    </div> 
                </md-list-item>
            </md-list>
             <div ng-show="lizenzViewPages">
            <hr />
            <div class="ng-scope layout-row " style="padding-left:10px">
                    <div flex="10 " class="flex "><md-button class="md-raised" style="margin-bottom:10px" ng-click="" md-colors="{color: 'green'}">Prev</md-button></div>
                    <div class="flex "></div>
                    <div flex="10 " class="flex "><md-button class="md-raised" style="margin-bottom:10px" ng-click="" md-colors="{color: 'green'}">Next</md-button></div>
            </div>
            </div>
        </div>
    </md-content>

    <md-content ng-show="isNetzwerkVewaltungSelected">
        <div class="page">
            <md-input-container class="md-block" flex-gt-sm>
            		<label>Search...</label>
            		<input ng-model="searchHost">
        	</md-input-container>
            
            <md-list ng-controller="ListCtrl" ng-cloak>
                <md-list-item ng-repeat="hw in ctrl.getNetzwerkHost()" ng-click="showHardwareInformation(hw)" class="md-2-line">
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
                    <div flex="10" class="md-list-item-text" layout="column">
                     <md-button class="md-raised" style="margin-bottom:10px" ng-click="delHardwareInformation(hw)" md-colors="{color: 'red'}">Dell</md-button>
                    </div> 
                </md-list-item>
            </md-list>
            
            <div ng-show="hardwareViewPages">
            <hr />
            <div class="ng-scope layout-row " style="padding-left:10px">
                    <div flex="10 " class="flex "><md-button class="md-raised" style="margin-bottom:10px" ng-click="ctrl.prevNetzwerkPage()" md-colors="{color: 'green'}">Prev</md-button></div>
                    <div class="flex "></div>
                    <div flex="10 " class="flex "><md-button class="md-raised" style="margin-bottom:10px" ng-click="ctrl.nextNetzwerkPage()" md-colors="{color: 'green'}">Next</md-button></div>
            </div>
            </div>
        </div>
    </md-content>

    <md-content class="md-padding" ng-show="isUserVerwaltungSelected">
       
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


    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-animate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-aria.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-messages.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>

    <script type="text/javascript" src="<c:url value='/static/js/main.js' />"></script>
</body>

</html>