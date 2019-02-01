// @ts-ignore
angular.module('MainPage', ['ngMaterial','ngFileUpload'])
	.controller('ListCtrl',['$scope','$mdDialog','$http','Upload',
		 function ($scope, $mdDialog, $http,Upload) {
		    
			$scope.translation;
			$http.get('static/translation/translation.json',{header : {'Content-Type' : 'application/json; charset=utf-8'}}).then(function(res){
				var language = navigator.language.split("-")[0]
				$scope.translation = res.data[language];     
				$scope.title = $scope.translation.shortcurt.clients
	        });
		
			$scope.people = [];
			$scope.menueIcon = "/NetView/static/img/netzwerk.jpg";
			$scope.doaminSelected = {};
			$scope.userVerwaltung = {
				mode: "Update"
			}
			$scope.selectedMonitorCount = 2;
			$scope.selectedPC = false;
			$scope.selectedTelephone = false;
			$scope.selectedHandy = -1;
			$scope.selectedLaptop = -1;
			
			$scope.userVerwaltung = false;
			$scope.systemVerwaltung = true;
			
			$scope.selectedLizenzColor = '#000000';
			$scope.selectedUserColor = '#000000';
			$scope.selectedMobileColor = '#000000';
			$scope.selectedServerColor = '#000000';
			$scope.selectedHardwareColor = '#f98e22';
			
			$scope.categorien = [{
				name : 'Server'
			},{
				name : 'Client'
			}];
			
			$scope.title = "";
			
			$scope.serverVerwaltungSelectedLocation = 1;
			$scope.isLizenzVewaltungSelected = false;
			$scope.isNetzwerkVewaltungSelected = true;
			$scope.isUserVerwaltungSelected = false;
			$scope.isUserVerwaltungUpdateSelected = false;
			$scope.isUserVerwaltungCreateSelected = false;
			$scope.isServerVerwaltungSelected = false;
			$scope.isMobileVewaltungSelected = false;
			$scope.infoFeld = "Hardware";
			
			$scope.showUserRefreshButton = false;
			$scope.showUserCreateButton = false;
			$scope.showUserClearButton = false;
			$scope.showUserInfos = true;
			$scope.showUserSearch = true;
			$scope.showUserDisableDate = false;
			$scope.isUserDisableSelected = false;
			
			$scope.valueSearchNetzwerkVerwaltung = "";


			$scope.monitorCount = [1, 2, 3, 4];
			$scope.laptopList = [{
				id: -1,
				name: '--'
			}];
			$scope.HandyList = [{
				id: -1,
				name: '--'
			}];
			$scope.LocationList = [{
				displayname :  "--"
			}];

			$scope.hardwareViewPages = [];
			$scope.clientPage = 1;
			$scope.hardwarePage = 1;
			$scope.settings = {};
			$scope.hardware = [];
			$scope.hardwareView = [];
			$scope.hardwareLizenzen;
			$scope.selectedUser = "";
			$scope.searchUpdateName = '';
			$scope.storeUserInformation = {};
			$scope.selectedHardware;
			$scope.searchHostClient = "";
			$scope.searchHostServer = "";
			$scope.userInformation = {};
			$scope.userInformationAktiv = {};
			
			$scope.hardwareClientViewPages = false;
			$scope.hardwareServerViewPages = false;
			$scope.lizenzen = [];
			$scope.lizenzenFrei = [];
			$scope.lizenz = {
					name : '',
					key : '',
					categorie : ''	
			};
			
			$scope.useList = [
				{
					name: 'All'
				},{
					name: 'frei'
				},{
					name: 'verwendet'
				},{
					name: 'mehrfach'
				}
			]
			$scope.showAllUsed = 'All';
			$scope.showClientLocation = "--";
			$scope.importHardwareFiles;
			$scope.importLizenzFiles;
			$scope.importMobileFiles;
			$scope.lizenzViewPages = false;
			$scope.lizenzPages ={};
			$scope.lizenzPage = 1;
			$scope.searchLizenz = '';
			$scope.selectedHardwareNewLizenz = -1;

			$scope.showHardwareActiv = false;
			
			$scope.showHardwareViewOwner = false;
			$scope.showHardwareViewUser = false;
			$scope.showHardwareViewHardware = false;
			$scope.showHardwareViewLizenz = false;
			
			$scope.showAllServer = false;
			$scope.showAllClients = false;
			$scope.showAllLizenz = false;
			
			$scope.hardwareServerPages = {};
			$scope.hardwareClientsPages = {};
			
			$scope.departmentList = [{
				did : -1,
				displayname : 'neue Abteilung'
			}];
			$scope.departmentValue = -1;
			
			$scope.showLocation = "--";
			
			$scope.helpImport = function(){
				var anchor = angular.element('<a/>');
			    anchor.attr({
			         href: '/NetView/static/img/Import.csv',
			         target: '_blank'
			     })[0].click();
			}
			
			$scope.hardwareInsert = {
					hostname : '',
					ip : '',
					bs : '',
					description : '',
					model : '',
					sn : '',
					cpu : '',
					ram : '',
					location : 1,
					categorie : 'Server'
			}

			$scope.config = {
				create: false,
				userdetails: true
			};

			$scope.addLizenz = function(){
				$http({
					method: 'POST',
					scope: $scope,
					url: 'lizenz',
					data : $scope.lizenz,
				}).then(function successCallback(response) {
					$scope.lizenz = {
							name : '',
							key : '',
							categorie : ''	
					}
				});			
			}
			
			
			$scope.addLizenzToHardware = function(lizenz, hardware, hardwareLizenzen, lizenzenFrei){
				var lizenzData = JSON.parse(lizenz);
				if (lizenzData.lid != undefined){
					$http({
						method: 'POST',
						params: {
							'lid' : lizenzData.lid,
							'hid' : hardware.hid,
						},
						url: 'hardware/lizenz'
					}).then(function successCallback(response) {
						
						for( var i = 0; i < lizenzenFrei.length; i++){ 
							   if ( lizenzenFrei[i]["lid"] === lizenzData.lid) {
								  lizenzenFrei.splice(i, 1); 
							   }
						}
						
						hardwareLizenzen.push(lizenzData);
						hardware.push(lizenzData);
						lizenz = "";
					});
				}
			}
			
			$scope.addHardware = function(hardware){
				$http({
					method: 'POST',
					data: hardware,
					url: 'hardware'
				}).then(function successCallback(response) {
					
					
				});
			}
			
			
			$scope.findUser = function (user) {
				return $scope.findUserByName(user);
			}

			$scope.findUserByName = function (user) {
				return user.displayName.toLowerCase() == $scope.searchUserText.toLowerCase()
			}

			$scope.findHardware = function (hardware) {
				return hardware.hostname.toLowerCase() == $scope.selectedHardware.toLowerCase();
			}

			$scope.findHardwareByOwner = function (hardware) {

				if (hardware.owner == undefined) return false;

				var user = $scope.userInformation.displayName.split(" ");
				if (user.length > 1) {
					user = user[0] + "." + user[1];
				}
				return hardware.owner.toLowerCase() == user.toLowerCase();
			}

			$scope.findHardwareByAktivUsername = function (hardware) {
				var user = $scope.userInformation.displayName.split(" ");
				if (user.length > 1) {
					user = user[0] + "." + user[1];
				}
				if (hardware.aktivUsername == undefined){
					return false;
				}
				return hardware.aktivUsername.toLowerCase() == user.toLowerCase();
			}

			$scope.clearUserInformaiton = function () {
				$scope.config.userdetails = true;
				$scope.userInformation = {};
			}

			$scope.clearUserInformaiton();

			$http({
				method: 'GET',
				scope: $scope,
				url: 'location'
			}).then(function successCallback(response) {
					response.data.forEach(function (data) {
						response.config.scope.LocationList.push(data);
					});
				},
				// @ts-ignore
				function errorCallback(response) {
					$mdDialog.show($mdDialog.alert()
						.title('Fehler').textContent(
							'Fehler beim Laden der Daten')
						.ariaLabel('Error').ok('OK')
						.targetEvent(event));
				}
			);
						
			$http({
				method: 'GET',
				scope: $scope,
				sync: false,
				url: 'ldap/user'
			}).then(
				function successCallback(response) {
					response.data.forEach(function (data) {
						if (data.lid != -1) {
							$scope.selectedItem = data.lid;
						} else {
							data.lid = 1;
						}
						data.img = 'static/img/Mitarbeiter/' + data.firstname + ' ' + data.lastname + '.jpg'

						response.config.scope.people.push(data);

					});

					$scope.stateUser = loadAllUser($scope);

				});
			
			$scope.showImport = function(){
				$mdDialog.show({
					scope: $scope.$new(),
					templateUrl: 'static/dialog/Import.html',
					clickOutsideToClose: true,
					fullscreen: $scope.customFullscreen
				});
				
			}
			
			$scope.sendImport = function(importHardwareData, importLizenzData, importMobileData){
				if (importHardwareData != undefined && importHardwareData.length > 0 ){
				 Upload.upload({
			        url: 'import/hardware',// additional data to send
			        file: importHardwareData[0]
			     });
				}
				if (importLizenzData != undefined && importLizenzData.length > 0){
				 Upload.upload({
			        url: 'import/lizenz',// additional data to send
			        file: importLizenzData[0]
			     });
				}
				if (importMobileData != undefined && importMobileData.length > 0){
				 Upload.upload({
			        url: 'import/mobile',// additional data to send
			        file: importMobileData[0]
			     });
				}
			}
			
			$scope.show = function(netz, server, lizenz, mobile, user, cHW, cS, cL, cM,cU){
				$scope.isNetzwerkVewaltungSelected = netz;
				$scope.isUserVerwaltungSelected = user;
				$scope.isServerVerwaltungSelected = server;
				$scope.isLizenzVewaltungSelected = lizenz;
				$scope.isMobileVerwaltungSelected = mobile;
				$scope.selectedHardwareColor = cHW;
				$scope.selectedServerColor = cS;
				$scope.selectedLizenzColor = cL;
				$scope.selectedUserColor = cU;
				$scope.selectedMobileColor = cM;
			}
			
			$scope.selectLizenzAsUsed = false;
			
			$scope.updateLizenzState = function(selectLizenzAsUsed, state){
				
				if (!state){
					selectLizenzAsUsed.state = 1;
				}else{
					selectLizenzAsUsed.state = 0;
				}
				
				$http({
					method: 'PUT',
					url: 'lizenz/state',
					data : selectLizenzAsUsed,
					scope: $scope,
				 }).then(function successCallback(response) {
					 	selectLizenzAsUsed.state = response.config.data.state;
					 	if (response.config.data.state == 0){
						  selectLizenzAsUsed.stateInfo = "frei";
						} else if (response.config.data.state == 1){
							selectLizenzAsUsed.stateInfo = "verwendet"
						} else if (response.config.data.state == 2){
							selectLizenzAsUsed.stateInfo = "mehrfach";
						}
				 });
			}
			
			$scope.loadLizenz = function(state){
				$http({
					method: 'GET',
					scope: $scope,
					params : {
						"state" : state
					},
					url: 'lizenz'
				 }).then(function successCallback(response) {
					 response.config.scope.lizenzen = [];
						response.data.forEach(function (data) {
							if (data.state == 0){
								data.stateInfo = "frei";
								response.config.scope.lizenzenFrei.push(data);
							} else if (data.state == 1){
								data.stateInfo = "verwendet"
							} else if (data.state == 2){
								data.stateInfo = "mehrfach";
							}
							response.config.scope.lizenzen.push(data);
						});
						
						$scope.setLizenzViewPages(response.config.scope.lizenzen);
					}
				 );
			}

			$scope.selectMenu = function (selectMenue) {


				$scope.showUserRefreshButton = false;
				$scope.showUserCreateButton = false;
				$scope.showUserClearButton = false;
				$scope.showUserInfos = true;
				$scope.showUserSearch = true;
				$scope.showUserDisableDate = false;
				$scope.isUserDisableSelected = false;

				if (selectMenue == 'showLizenz') {
					$scope.userVerwaltung = false;
					$scope.systemVerwaltung = true;
					$scope.loadLizenz("-1");
					$scope.show(false,false, true ,false,false, '#000000','#000000','#f98e22','#000000','#000000');
				} else if (selectMenue == 'showClients') {
					$scope.userVerwaltung = false;
					$scope.systemVerwaltung = true;
					$scope.loadHardware('clients');
					$scope.title = $scope.translation.shortcurt.clients
					$scope.show(true,false, false ,false,false, '#f98e22','#000000','#000000','#000000','#000000');
				} else if (selectMenue == 'showServer') {
					$scope.userVerwaltung = false;
					$scope.systemVerwaltung = true;
					$scope.loadHardware('sonstiges');
					$scope.title = $scope.translation.shortcurt.netz
					$scope.show(false,true, false ,false,false, '#000000','#f98e22','#000000','#000000','#000000');
				} else if (selectMenue == 'showMobile') {
					$scope.userVerwaltung = false;
					$scope.systemVerwaltung = true;
					$scope.title = "Mobilefunk"
					$scope.show(false,false, false ,true,false, '#000000','#000000','#000000','#f98e22','#000000');
				} else if (selectMenue == 'showUpdateUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true, '#000000','#000000','#000000','#000000','#f98e22');					
					$scope.isUserVerwaltungUpdateSelected = true;
					$scope.isUserVerwaltungCreateSelected = false;
					$scope.infoFeld = "User Aktualisieren";
					$scope.menueIcon = "/NetView/static/img/updateuser.png";
				} else if (selectMenue == 'showCreateUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true, '#000000','#000000','#000000','#000000','#f98e22');				
					$scope.showUserSearch = false;
					$scope.isUserVerwaltungUpdateSelected = false;
					$scope.isUserVerwaltungCreateSelected = true;
					$scope.infoFeld = "User hinzufügen";
					$scope.menueIcon = "/NetView/static/img/adduser.png";
				} else if (selectMenue == 'showUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true, '#000000','#000000','#000000','#000000','#f98e22');
					$scope.isUserVerwaltungUpdateSelected = false;
					$scope.isUserVerwaltungCreateSelected = false;
					$scope.infoFeld = "User";
					$scope.menueIcon = "/NetView/static/img/user.png";
				} else if (selectMenue == 'showDeleteUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true, '#000000','#000000','#000000','#000000','#f98e22');
					$scope.isUserVerwaltungUpdateSelected = false;
					$scope.isUserVerwaltungCreateSelected = false;
					$scope.showUserInfos = false;
					$scope.showUserSearch = true;
					$scope.showUserDisableDate = true;
					$scope.isUserDisableSelected = true;
					$scope.infoFeld = "User Austritt";
					$scope.menueIcon = "/NetView/static/img/deleteUser.png";
				} else if (selectMenue == 'showVorlagen') {
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/vorlagen.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
						});
				} else if (selectMenue == 'showLaptops') {
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/laptops.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
						});
				} else if (selectMenue == 'showLocation') {
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/standort.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
						});
				} else if (selectMenue == 'showHandy') {
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/handys.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
						});
				} else if (selectMenue == 'showDepartment') {
					$scope.loadDepartment();
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/abteilung.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
						});
				} else if (selectMenue == 'serverAdd') {
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/hardware.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
						});
				} else if (selectMenue == 'lizenzAdd') {
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/lizenzen.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
						});
				}
			}

			// @ts-ignore
			$scope.selectUpdateUser = function (name, ev) {
				$scope.config.userdetails = false;
				$scope.selectedUser = $scope.ctrl.searchUserText
				$scope.userInformation = $scope.people.find($scope.findUser);
			};

			$scope.changeUserUpdateInsert = function () {
				if ($scope.config.create) {
					$scope.storeUserInformation = $scope.userInformation;
					$scope.clearUserInformaiton();
				} else {
					$scope.config.userdetails = false;
					$scope.userInformation = $scope.storeUserInformation;
				}
			}

			$scope.self = this;
			
			$scope.delHardware = function (hardware, ev) {
				var confirm = $mdDialog.confirm()
		          .title('Hardwar entfernen ('+hardware.hostname+')')
		          .textContent('Sind Sie sicher?')
		          .targetEvent(ev)
		          .ok('Ja')
		          .cancel('Nein');
				
				$mdDialog.show(confirm).then(function() {
					$http({
						method: 'DELETE',
						params: {
							'hid' : hardware.hid,
						},
						scope : $scope,
						url: 'hardware'
					}).then(function successCallback(response) {
						$scope.removeItemFromArrayDictonariy($scope.hardware,hardware.hid, 'hid');
					});
				 });
				
			}
			
			$scope.removeItemFromArrayDictonariy = function(values, remove, searchName){
				for( var i = 0; i < values.length; i++){ 
				   if ( values[i][searchName] === remove) {
					 values.splice(i, 1); 
				   }
				}
			}
			
			$scope.delLizenz = function (lizenz, ev) {
				var confirm = $mdDialog.confirm()
		          .title('Lizenz entfernen ('+lizenz.name+')')
		          .textContent('Sind Sie sicher?')
		          .targetEvent(ev)
		          .ok('Ja')
		          .cancel('Nein');
				
				$mdDialog.show(confirm).then(function() {
				$http({
					method: 'DELETE',
					params: {
						'lid' : lizenz.lid,
					},
					url: 'lizenz'
				}).then(function successCallback(response) {
					$scope.removeItemFromArrayDictonariy($scope.lizenzen,lizenz.lid, 'lid');
					$scope.hardware.forEach(data => {
						$scope.removeItemFromArrayDictonariy(data.lizenz,lizenz.lid, 'lid');
					});
				});
				});
			}
			
			$scope.delHardwareLizenz = function (lizenz, hardware, ev) {
				var confirm = $mdDialog.confirm()
		          .title('Hardware lizenz entfernen ('+lizenz.name+')')
		          .textContent('Sind Sie sicher?')
		          .targetEvent(ev)
		          .ok('Ja')
		          .cancel('Nein');
				
				$mdDialog.show(confirm).then(function() {
				  $http({
					method: 'DELETE',
					params: {
						'lid' : lizenz.lid,
						'hid' : hardware.hid,
					},
					url: 'hardware/lizenz'
				}).then(function successCallback(response) {
					$scope.removeItemFromArrayDictonariy(hardware.lizenz,lizenz.lid, 'lid');
				});
			});
			}
			
			$scope.logout = function(){
				$http({
					method: 'POST',
					url: 'logout'
				}).then(
						function successCallback(response) {
							window.location = "NetView/login"
						});
			}
			
			$scope.showLizenzInformation = function(lizenz){
				$scope.lizenzSelected = lizenz;
				$scope.lizenzHostList = lizenz.hardware;
				if (lizenz.state > 0){
					$scope.selectLizenzAsUsed = true;
				}else{
					$scope.selectLizenzAsUsed = false;
				}
				$mdDialog.show({
					scope: $scope.$new(),
					templateUrl: 'static/dialog/lizenzenView.html',
					clickOutsideToClose: true,
					fullscreen: $scope.customFullscreen
				});
			}
			
			$scope.showHardwareInformation = function (hid) {
				
				$scope.showHardwareViewOwner = false;
				$scope.showHardwareViewHardware = false;
				$scope.showHardwareViewLizenz = false;
				$scope.showHardwareViewHardwareList = false;
				$scope.showHardwareViewUser = false;
				
				$http({
					method: 'GET',
					scope: $scope,
					sync: true,
					url: 'hardware/'+hid
				}).then(function successCallback(response) {
					$scope.loadLizenz("0");
					$scope.hardwareInformation = response.data;
					
					$scope.showHardwareViewHardware = true;
					$scope.showHardwareViewLizenz = true;
					
					
					if (hardware.ownerInformation != undefined && hardware.ownerInformation.displayName != undefined){
						$scope.showHardwareViewOwner = true;
						$scope.hardwareOwner = hardware.ownerInformation;
						
					}
					
					if (hardware.inUseInformation != undefined && hardware.inUseInformation.displayName != undefined){
						$scope.showHardwareViewUser = true;
						$scope.hardwareUser = hardware.inUseInformation;
					}

					$scope.hardwareLizenzen = $scope.hardwareInformation.lizenz;
	
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/hardwareView.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
					});
				});
			};

			$scope.showSettings = function () {
				$http({
					method: 'GET',
					scope: $scope,
					url: 'settings'
				}).then(
					function successCallback(response) {
						response.config.scope.settings = response.data;

						$mdDialog.show({
								scope: $scope.$new(),
								templateUrl: 'static/dialog/settings.html',
								clickOutsideToClose: true
							});
					});
			}

			$scope.updateSettings = function () {

				$http.post("settings", $scope.settings, {
					'Content-Type': 'application/json'
				});
			}

			$scope.showCreateDetails = function () {
				$scope.userVerwaltung.mode = "showAdd"
			}

			$scope.showLizenzDetails = true;
			
			$scope.showUserInformation = function (uid) {

				$scope.showHardwareViewOwner = false;
				$scope.showHardwareViewHardware = false;
				$scope.showHardwareViewLizenz = false;
				$scope.showHardwareViewHardwareList = false;
				$scope.showHardwareViewUser = false;
				
				$http({
					method: 'GET',
					scope: $scope,
					sync: true,
					url: 'user/'+uid
				}).then(function successCallback(response) {
					$scope.loadLizenz("0");
					$scope.showHardwareViewUser = true;
					$scope.hardwareUser = response.data
					$scope.hardwareList = $scope.showHardware;
					
					if ($scope.hardwareUser.aktivHardware != undefined){
						$scope.showHardwareActiv = false;
						$scope.hardwareList.push($scope.hardwareUser.aktivHardware);
					}
					
					if ($scope.hardwareList.length > 0){

						$scope.showHardwareViewHardware = true;
						
						if ($scope.hardwareList.length > 1){
							$scope.showHardwareViewHardwareList = true;
						}
					}
					
					$mdDialog.show({
							scope: $scope.$new(),
							templateUrl: 'static/dialog/hardwareView.html',
							clickOutsideToClose: true,
							fullscreen: $scope.customFullscreen
					});
				});

				
			};

		
			
			
			$scope.selectDepartment = function(departmentValue){
				if (departmentValue.id == -1){
					$scope.department = {
							did : -1,
							displayname : '',
							name : '',
							position : ''
					}
				}else{
					$scope.department = departmentValue;
				}
			}
			
			$scope.department = {
					did : -1,
					displayname : '',
					name : '',
					position : ''
			}
			
			$scope.loadDepartment = function(){
				$http({
					method: 'GET',
					scope: $scope,
					url: 'department'
				}).then(function successCallback(response) {
						response.config.scope.departmentList = [{
							did : -1,
							displayname : 'neue Abteilung'
						}];
						
						response.data.forEach(function (data) {
							response.config.scope.departmentList.push(data);
						});
					},
					// @ts-ignore
					function errorCallback(response) {
						$mdDialog.show($mdDialog.alert()
							.title('Fehler').textContent(
								'Fehler beim Laden der Daten')
							.ariaLabel('Error').ok('OK')
							.targetEvent(event));
					}
				);
			}
			
			$scope.addOrUpdateDepartment = function(){
				var method = 'POST';
				if ($scope.department.did != -1){
					method = 'PUT';
				}
				
				$http({
					method: method,
					scope: $scope,
					url: 'department',
					data : $scope.department,
				}).then(function successCallback(response) {
					$scope.department = {
							displayname : '',
							name : '',
							position : ''
					}
				});	
			}
			
			$scope.loadHardware = function(categorie){
				$http({
					method: 'GET',
					scope: $scope,
					sync: false,
					params : {
						"categorie" : categorie
					},
					url: 'hardware'
				}).then(function successCallback(response) {
					response.config.scope.hardware = [];
						response.data.forEach(function (data) {
							response.config.scope.hardware.push(data);
						});
						
						response.config.scope.setHardwareViewPages(response.data);
				});
			}
			
			$scope.loadHardware('clients');
			
			$scope.setHardwareViewPages = function(hardware, values){
				var view = false;
				if (values != undefined){
					var result = {};
					if (hardware.length >= 11){
						view = true;
						
						var page = [];
						var pageAmount = 7;
						var pageSize=1;
						for (var i=0;i<hardware.length;i++){
							if (pageAmount > 0){
								page.push(hardware[i]); 
								pageAmount--;
							}else{
								values[pageSize] = page;
								pageSize++;
								pageAmount = 7;
								var page = [];
							}
						}
						
						if (Object.keys(page).length > 0)
							values[pageSize] = page;
						
					}else{
						view = false;	
						values[1] = hardware;
					}
				}
				
				
				return view;
			}
			
			$scope.clientMaxPage = 1;
			
			$scope.setLizenzViewPages = function(lizenzen){
				var result = {};
				$scope.lizenzPages = [];
				if (lizenzen.length >= 11){
					$scope.lizenzViewPages = true;	
					var page = [];
					var pageAmount = 9;
					var pageSize=1;
					for (var i=0;i<lizenzen.length;i++){
						if (pageAmount > 0){
							page.push(lizenzen[i]); 
							pageAmount--;
						}else{
							$scope.lizenzPages[pageSize] = page;
							pageSize++;
							pageAmount = 9;
							var page = [];
						}
					}
					
					if (Object.keys(page).length > 0)
					  $scope.lizenzPages[pageSize] = page;
					
				}else{
					$scope.lizenzViewPages = false;
					$scope.lizenzPages[1] = lizenzen;
				}
			}

			$scope.updateUserInformation = function () {
				$http.put("ldap/user", $scope.userInformation, {
					'Content-Type': 'application/json'
				});
			}

			$scope.createUserInformation = function () {
				var httpRequest = $http.post("ldap/user", $scope.user, {
					'Content-Type': 'application/json'
				}).then($scope.httpRequestSuccess,
					$scope.httpRequestError);
				$scope.sendInformation(httpRequest);
			}

			$scope.createUserInformation = function () {
				$http.post("ldap/user", $scope.user, {
					'Content-Type': 'application/json'
				}).then($scope.httpRequestSuccess,
					$scope.httpRequestError);

			}

			// @ts-ignore
			$scope.httpRequestSuccess = function (response) {
				$mdDialog.show($mdDialog.alert().title('Erfolgreich')
					.textContent('Erfolgreich durchgefuehrt').ok(
						'OK').targetEvent(event));
				$scope.resetInformation();
			}

			// @ts-ignore
			$scope.httpRequestError = function (response) {
				$mdDialog
					.show($mdDialog
						.alert()
						.title('Fehler')
						.textContent(
							'Es ist leider ein Fehler entstanden')
						.ariaLabel('Error').ok('OK')
						.targetEvent(event));
			}

			$scope.sendInformation = function (httpRequest) {
				if ($scope.user.firstname != "" &&
					$scope.user.lastname != "" &&
					$scope.user.lid != 0) {
					httpRequest;
				} else {
					$mdDialog
						.show($mdDialog
							.alert()
							.title('Fehler')
							.textContent(
								'Bitte alle Notwendigen Informationen eintragen')
							.ok('OK').targetEvent(event));
				}
			}
			
			
			$scope.nextPage = function(view){
				if (view == 'hardware'){
					if (Object.keys($scope.hardwareServerPages).length > $scope.netzwerkPage){
						$scope.netzwerkPage++;
					}
				}else if(view == 'client'){
					if (Object.keys($scope.hardwareClientsPages).length > $scope.clientPage){
						$scope.clientPage++;
					}
				}else if(view == 'lizenz'){
					if (Object.keys($scope.lizenzPages).length > $scope.lizenzPage){
						$scope.lizenzPage++;
					}
				}
				
				
			}
			
			
			$scope.setLizenzState = function(info){
				if (info != true){
					$scope.lizenz.state = 1;
				}else{
					$scope.lizenz.state = 0;
				}
			}
			
			$scope.prevPage = function(view){
				if (view == 'hardware'){
					if (1 < Object.keys($scope.hardwareServerPages).length && $scope.hardwarePage != 1){
						$scope.hardwarePage--;
					}
				}else if(view == 'client'){
					if (1 < Object.keys($scope.hardwareClientsPages).length && $scope.clientPage != 1){
						$scope.clientPage--;
					}
				}else if(view == 'lizenz'){
					if (1 < Object.keys($scope.lizenzPages).length && $scope.lizenzPage != 1){
						$scope.lizenzPage--;
					}
				}
				
			}
			
			this.getNetzwerkHost = function(modus){	
				var lowerCaseSearchHost = '';
			    var filterHardware = []
			    var server = false;
			   
			    if (modus.toLowerCase() == 'server'){
				 server = true;
				 lowerCaseSearchHost = $scope.searchHostServer.toLowerCase();
			    }else{
				 lowerCaseSearchHost = $scope.searchHostClient.toLowerCase(); 
			    }
			 
			 for (var i=0;i<$scope.hardware.length;i++){
				if ($scope.hardware[i].categorie != undefined){
				 if (server){
					 if ($scope.hardware[i].categorie.toLowerCase() != 'client'){
					  if ($scope.showLocation != "--" && $scope.showLocation != ""){
							if ($scope.hardware[i].location.toString() == $scope.showLocation){
								 filterHardware.push($scope.hardware[i]); 
							 }	
					   }else {
					      filterHardware.push($scope.hardware[i]);
					   }
					 }
				 }else {
					 if ($scope.hardware[i].categorie.toLowerCase() == 'client'){
						 if ($scope.showLocation != "--" && $scope.showLocation != ""){
								if ($scope.hardware[i].location.toString() == $scope.showLocation){
									 filterHardware.push($scope.hardware[i]); 
								 }	
							}else{
						      filterHardware.push($scope.hardware[i]);
							} 											 
					 } 
				 }
				}
			 }
			 
			 if (lowerCaseSearchHost != ''){					
				var result = new Array();
				for (var i=0;i<filterHardware.length;i++){
					if (filterHardware[i].hostname != null && filterHardware[i].hostname.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].owner != null && filterHardware[i].owner.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].aktivUsername != null && filterHardware[i].aktivUsername.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].sn != null && filterHardware[i].sn.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].model != null && filterHardware[i].model.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].ram != null && filterHardware[i].ram.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].categorie != null && filterHardware[i].categorie.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].description != null && filterHardware[i].description.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					} else if (filterHardware[i].department != null && filterHardware[i].department.toLowerCase().indexOf(lowerCaseSearchHost) != -1){
						result.push(filterHardware[i]);
					}
					
					
					   if ("freiburg".indexOf(lowerCaseSearchHost) > -1) {
							  if (filterHardware[i].location != null && filterHardware[i].location == 1){
									result.push(filterHardware[i]);
							} 
					   }
					   
					   if ("frankfurt".indexOf(lowerCaseSearchHost) > -1) {
							  if (filterHardware[i].location != null && filterHardware[i].location == 2){
									result.push(filterHardware[i]);
							} 
					   }
					   
					   if ("wien".indexOf(lowerCaseSearchHost) > -1) {
							  if (filterHardware[i].location != null && filterHardware[i].location == 5){
									result.push(filterHardware[i]);
							} 
					   }
					   
					   if ("hamburg".indexOf(lowerCaseSearchHost) > -1) {
							  if (filterHardware[i].location != null && filterHardware[i].location == 7){
									result.push(filterHardware[i]);
							} 
					   }
					   
					   if ("berlin".indexOf(lowerCaseSearchHost) > -1) {
							  if (filterHardware[i].location != null && filterHardware[i].location == 3){
									result.push(filterHardware[i]);
							} 
					   }
					   
					   if ("leipzig".indexOf(lowerCaseSearchHost) > -1) {
							  if (filterHardware[i].location != null && filterHardware[i].location == 6){
									result.push(filterHardware[i]);
							} 
					   }
					   
					   if ("olenburg".indexOf(lowerCaseSearchHost) > -1) {
							  if (filterHardware[i].location != null && filterHardware[i].location == 4){
									result.push(filterHardware[i]);
							} 
					   }
					
					
					if (filterHardware[i].location != null && filterHardware[i].location == location){
						result.push(filterHardware[i]);
					} 
				}
				
				filterHardware = result;
				
			 }
			 
			 
			
			 if (server){
				if ($scope.showAllServer){
					$scope.hardwareServerViewPages = false;
					return filterHardware;
				}
				$scope.hardwareServerPages = [];
				$scope.hardwareServerViewPages = $scope.setHardwareViewPages(filterHardware, $scope.hardwareServerPages);
				$scope.hardwareMaxPage = Object.keys($scope.hardwareServerPages).length;
				return $scope.hardwareServerPages[$scope.hardwarePage];
			 } else {
				if ($scope.showAllClients){
					$scope.hardwareClientViewPages = false;
					return filterHardware;
				}
				$scope.hardwareClientsPages = [];
				$scope.hardwareClientViewPages = $scope.setHardwareViewPages(filterHardware, $scope.hardwareClientsPages);
				$scope.clientMaxPage = Object.keys($scope.hardwareClientsPages).length;
				return $scope.hardwareClientsPages[$scope.clientPage];
			 }
			}
			
			$scope.setClientsPage = function(){
				$scope.clientPage = 1;
			}
			
			$scope.setLocationPage = function(){
				$scope.clientPage = 1;
				$scope.hardwarePage = 1;
			}
			
			$scope.setHardwarePage = function(){
				$scope.hardwarePage = 1;
			}
			
			$scope.setLizenzPage = function(){
				$scope.lizenzPage = 1;
			}
			
			this.getLizenz = function(){
				 var lowerCaseSearchLizenz = $scope.searchLizenz.toLowerCase();
				 var lizenzData = [];
				 
				 for (var i=0;i<$scope.lizenzen.length;i++){
					 if ($scope.showAllUsed == 'All'){
						 lizenzData.push($scope.lizenzen[i])
					 }else{
						if ($scope.lizenzen[i].stateInfo == $scope.showAllUsed) {
							lizenzData.push($scope.lizenzen[i]);
						}
					 }
				 }
				 
				 
				 if (lowerCaseSearchLizenz != ''){	
					var result = new Array();
					for (var i=0;i<lizenzData.length;i++){
						if (lizenzData[i].name != null && lizenzData[i].name.toLowerCase().indexOf(lowerCaseSearchLizenz) != -1){
							result.push(lizenzData[i]);
						} else if (lizenzData[i].key != null && lizenzData[i].key.toLowerCase().indexOf(lowerCaseSearchLizenz) != -1){
							result.push(lizenzData[i]);
						} else if (lizenzData[i].categorie != null && lizenzData[i].categorie.toLowerCase().indexOf(lowerCaseSearchLizenz) != -1){
							result.push(lizenzData[i]);
						} else if (lizenzData[i].stateInfo != null && lizenzData[i].stateInfo.toLowerCase().indexOf(lowerCaseSearchLizenz) != -1){
							result.push(lizenzData[i]);
						} 
					}
					
					if ($scope.showAllLizenz){
						$scope.lizenzViewPages = false;
						return result;
					}
					
					$scope.setLizenzViewPages(result);
					$scope.lizenzMaxPage = Object.keys($scope.lizenzPages).length;
					
					return $scope.lizenzPages[$scope.lizenzPage];	
				 }else{
					if ($scope.showAllLizenz){
						$scope.lizenzViewPages = false;
						return lizenzData;
					}
					
					$scope.setLizenzViewPages(lizenzData);
					$scope.lizenzMaxPage = Object.keys($scope.lizenzPages).length;
					return $scope.lizenzPages[$scope.lizenzPage];	
				 }
			}

			$scope.querySearch = querySearch;

			$scope.resetInformation = function () {
				$scope.user = {};
			}

			function querySearch(query) {
				var results = query ? $scope.stateUser.filter(createFilterFor(query)) : $scope.stateUser,
					deferred;
				if ($scope.simulateQuery) {
					deferred = $q.defer();
					$timeout(function () {
						deferred.resolve(results);
					}, Math.random() * 1000, false);
					return deferred.promise;
				} else {
					return results;
				}
			}

			function createFilterFor(query) {
				// @ts-ignore
				var lowercaseQuery = angular.lowercase(query);
				return function filterFn(stateUser) {
					return (stateUser.value.indexOf(lowercaseQuery) > -1);
				};
			}
			

			// @ts-ignore
			function loadAllUser(scope) {
				return $scope.people.map(function (people) {
					return {
						value: people.displayName.toLowerCase(),
						display: people.displayName
					};
				});
			}

			

		}]).directive('myLizenz', function() {
			  return {
			    templateUrl: 'static/html/lizenz.html'
			  };
		}).directive('myNetz', function() {
			  return {
				    templateUrl: 'static/html/netz.html'
				  };
		}).directive('myHardware', function() {
			  return {
				    templateUrl: 'static/html/hardware.html'
				  };
		}).directive('myUserdetails', function() {
			  return {
				    templateUrl: 'static/html/UserDetails.html'
				  };
		}).directive('myMenue', function() {
			  return {
				    templateUrl: 'static/html/menue.html'
				  };
		}).directive('myShurtcurt', function() {
			  return {
				    templateUrl: 'static/html/shurtcurt.html'
				  };
		}).directive('myMobile', function() {
			  return {
				    templateUrl: 'static/html/mobile.html'
				  };
		});