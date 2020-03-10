// @ts-ignore
app.controller('ListCtrl', function ($scope,initServer, $mdDialog,$timeout, $http,Upload, data, softwareService,userService) {

			this.checkLoading = initServer;

			$scope.mainThis = this;

			data.loadLocation($scope);
			data.loadUser($scope);
			data.loadChangelog($scope);
			data.loadLizenz($scope,"-1");

			var DynamicItems = function(data) {
				if (data != undefined)
					this.data = data;
				else
					this.data = [];
				this.loadedPages = {};
				this.numItems = 0;
				this.PAGE_SIZE = 50;

				this.fetchNumItems_(this.data.length);
			};

			DynamicItems.prototype.getItemAtIndex = function(index) {
				var pageNumber = Math.floor(index / this.PAGE_SIZE);
				var page = this.loadedPages[pageNumber];
				if (page) {
					return page[index % this.PAGE_SIZE];
				} else if (page !== null) {
					this.fetchPage_(pageNumber);
				}
			};

			DynamicItems.prototype.getLength = function() {
				return this.numItems;
			};

			DynamicItems.prototype.fetchPage_ = function(pageNumber) {
				this.loadedPages[pageNumber] = null;
				$timeout(angular.noop, 300).then(angular.bind(this, function() {
					this.loadedPages[pageNumber] = [];
					var pageOffset = pageNumber * this.PAGE_SIZE;
					for (var i = pageOffset; i < pageOffset + this.PAGE_SIZE; i++) {
						this.loadedPages[pageNumber].push(this.data[i]);
					}
				}));
			};

			DynamicItems.prototype.fetchNumItems_ = function(amount) {
				$timeout(angular.noop, 300).then(angular.bind(this, function() {
					this.numItems = amount;
				}));
			};

			$scope.userService = userService;

			$scope.security = {
					settings : false,
					verwaltung : true,
					verwaltungConfig : {
						clients : true,
						netz : true,
						mobile : false,
						lizenz : true,
						addHardware : true,
						addLizenz : true,
						changelog : true
					},
					config : {
						mobile : true						
					},
					user : true,
					userConfig : {
						users : true,
						addUser : false,
						deleteUser : false,
						updateUser : false
					},
					software : false,
					softwareConfig : {
						software : true,
						addSoftware : true,
						delSoftware : true
					}
			}

			$scope.addContractView = false;
			$scope.addHandyView = false;
			
			$scope.addContract = {
					number : '',
					sim : '',
					pin : '',
					puk : '',
					ultraOne : '',
					ultraTwo : ''
			}
			
			
			$scope.searchUserMobileText = "";
			$scope.handyModelId = '';
			
			$scope.handy = {
					imei : '',
					date : '',
					description : ''
			}
			
			// ********************
			$scope.handyModel = {};
			$scope.handyModelList = [];
			$scope.resetHandyModel = function(){
				$scope.handyModel = {
						displayName : '',
						model : '',
						color : '',
						store : ''
				}
			}
			$scope.resetHandyModel();
			
			$scope.addHandyModel = function(){
				$http({
					method: 'POST',
					scope: $scope,
					url: 'handyModel',
					data : $scope.handyModel
				}).then(function successCallback(response) {
					$scope.handyModelList.push(response.data);
					$scope.resetHandyModel();
				});	
			}
			
			$scope.deleteHandyModel = function(handyModel){
				$http({
					method: 'DELETE',
					scope: $scope,
					url: 'handyModel/'+ handyModel.hmid,
				}).then(function successCallback(response) {
					$scope.removeItemFromArrayDictonariy($scope.handyModelList,handyModel.hmid, 'hmid');
				});	
			}
			
			$scope.deleteContract = function(contract){
				$http({
					method: 'DELETE',
					scope: $scope,
					url: 'contract/'+ contract.cid,
				}).then(function successCallback(response) {
					$scope.removeItemFromArrayDictonariy($scope.contractList,contract.cid, 'cid');
				});	
			}
			
			this.getHandyList = function(){
				if ($scope.handyModelList != undefined && $scope.handyModelList.length > 0)
				 return $scope.handyModelList;	
				else
				 return []; 
			}
			
			this.getContractList = function(){
				if ($scope.contractList != undefined && $scope.contractList.length > 0)
				 return $scope.contractList;	
				else
				 return []; 
			}
			
			// ********************
			$scope.contractList = [];
			$scope.newContract = {};
			$scope.resetNewContract = function(){
				$scope.newContract = {
						name : '',
						contract : '',
						model : '',
						color : '',
						store : ''
				}
			}
			$scope.resetNewContract();
			
			$scope.addNewContract = function(){
				$http({
					method: 'POST',
					scope: $scope,
					url: 'contract',
					data : $scope.newContract
				}).then(function successCallback(response) {
					$scope.contractList.push(response.data);
					$scope.resetNewContract();
				});	
			}
			
			// ********************
			
			    
		      
			$scope.settingsConfig = {};
			$scope.resetSettingsConfig = function(){
				$scope.settingsConfig = {
						converter : false,
						user : false,
						data : false
				}
			}
			$scope.resetSettingsConfig();
			
			$scope.selectSetting = function(select){
				
				$scope.resetSettingsConfig();
				switch(select){
					case 0 : 
						$scope.settingsConfig.data = true;
				 		break;
					case 1 :
						$scope.settingsConfig.user = true;
						break;
					case 3 :
						$scope.settingsConfig.converter = true;
					break;
				}
			}
			
			$scope.settingsViewList = [{
				name : "Daten",
				id : 0
			},{
				name : "User",
				id : 1
			}];
			
			this.getSettingsViewList = function(){
				return $scope.settingsViewList;
			}
		      
			
			$scope.hostname = "";
			
			$scope.openTeamViewer = function(hw, username){

				if (hw != undefined){
					$scope.teamviewerID = hw.hostname;
					window.open("teamviewer10://control?device="+$scope.teamviewerID+"&authorization=password");
				}else if (username != undefined){

                    $http.get("ldap/user/"+username+"/hostname",{header : {'Content-Type' : 'application/json; charset=utf-8'}}).then(function(response){
                        window.open("teamviewer10://control?device="+response.data.hostname+"&authorization=password");
                    });
                }else {
					if ($scope.teamviewerID != "" && $scope.teamviewerID != undefined)
						window.open("teamviewer10://control?device="+$scope.teamviewerID+"&authorization=password");
				}


                $scope.teamviewerID = "";
			};

			$scope.openTelefon = function(number){
			    if (number != undefined && number != ""){
                    window.open('tel:'+number);
                }else {
			        if ($scope.telefonNumber != "" && $scope.telefonNumber != undefined){
                        window.open('tel:'+$scope.telefonNumber);
                    }
                }
            };

			$scope.addWizardUser = function(){
			    debugger;
                $http({
                    method: 'POST',
                    scope: $scope,
                    url: 'ldap/user/ad',
                    data : $scope.addUserWizardData,
                }).then(function successCallback(response) {
                    $scope.lizenz = {
                        name : '',
                        key : '',
                        categorie : ''
                    }
                });
            }
			
			softwareService.mdDialog = $mdDialog;
			softwareService.scopeMain = $scope;
			this.softwareService = softwareService;
		    $scope.softwareService = softwareService;
		    
			$http.get('static/translation/translation.json',{header : {'Content-Type' : 'application/json; charset=utf-8'}}).then(function(res){
				var language = navigator.language.split("-")[0]
				$scope.translation = res.data[language];     
				$scope.title = $scope.translation.shortcurt.clients
	        });
			
			$scope.overview = {
					adUserAmount : '',
					rentHardwareAmount : '',
					hardwareAmount : '',
					hardwareDisableAmount : '',
					lizenzAmount : '',
					lizenzErrorAmount : ''
			}
		
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
			$scope.telefonNumber = "";

			
			$scope.categorien = [{
				name : 'Server'
			},{
				name : 'Client'
			},{
				name : 'Drucker'
			},{
				name : 'Video'
			},{
				name : 'Access Point'
			},{
				name : 'Switch'
			},{
				name : 'Router'
			},{
				name : 'Telefonie'
			},{
				name : 'Lifesize'
			}];
			
			$scope.aktivList = [{
				value : 'All'
			},{
				value : 'Aktiv'
			},{
				value : 'Inaktiv'
			},{
				value : 'Unbekannt'
			}];
			
			$scope.categorieList = [{
				value : 'All'
			},{
				value : 'Server'
			},{
				value : 'Drucker'
			},{
				value : 'Video'
			},{
				value : 'Access Point'
			},{
				value : 'Switch'
			},{
				value : 'Router'
			},{
				value : 'Telefonie'
			},{
				value : 'Lifesize'
			}];
			
			$scope.title = "";
			
			$scope.serverVerwaltungSelectedLocation = 1;
			$scope.isLizenzVewaltungSelected = false;
			$scope.isNetzwerkVewaltungSelected = false;
			$scope.isOverview = false;
			$scope.isUserVerwaltungSelected = true;
			$scope.isUserVerwaltungUpdateSelected = false;
			$scope.isUserVerwaltungCreateSelected = false;
			$scope.isServerVerwaltungSelected = false;
			$scope.isMobileVewaltungSelected = false;
			$scope.isSoftwareSelected = false;
			
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
				displayname :  "Standort..."
			}];
			$scope.selectedLocation = "{\"displayname\":\"Standort...\"}";

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
			$scope.lizenz;
			
			$scope.resetLizenz = function(){
				$scope.lizenz = {
						name : '',
						key : '',
						allowamount : '',
						categorie : ''	
				};
			}
			
			$scope.resetLizenz();
			
			$scope.useList = [
				{
					name: 'All'
				},{
					name: 'frei'
				},{
					name: 'verwendet'
				},{
					name: 'reserviert'
				},{
					name: 'mehrfach'
				}
			]
			$scope.showAllUsed = 'All';
			$scope.showClientLocation = "--";
			$scope.lizenzViewPages = false;
			$scope.lizenzPages ={};
			$scope.lizenzPage = 1;
			$scope.searchLizenz = '';
			$scope.selectedHardwareNewLizenz = -1;
			
			$scope.searchMobile = '';
			$scope.mobilePage = 0;
			$scope.mobileMaxPage = 0;
			$scope.aktivMobileShow = 0;

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
			$scope.mobileUserList = [];
			
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
			$scope.hardwareInsert;

			$scope.resetHardwareInsert = function(){
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
			}
			
			$scope.resetHardwareInsert();
			
			$scope.config = {
				create: false,
				userdetails: true
			};
			
			$scope.settingsView ={}
			
			$scope.dialogConfig ={}
			
			$scope.resetViews = function (){
				$scope.showLizenzHardwareView  = false;
				$scope.dialogConfig ={
						actionUpdateShow : false,
						actionAddShow : false,
						actionDeleteShow : false,
						dialogChangelog : false,
						showDialogSoftware : false,
						dialogMobile : false,
						showChanglog : false,
						showDialogHardware : false,
						showDialogLizenzen : false,
						showLizenzen : false,
						showHardware : false,
						actionMgmtShow : false,
						actionUploadShow : false,
						dialogUpload : false,
                        showTelefon : false,
				}
			}
			
			$scope.resetViews();
			
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

			$scope.updateLizenz = function(){
				$http({
					method: 'PUT',
					scope: $scope,
					url: 'lizenz',
					data : $scope.lizenz,
				}).then(function successCallback(response) {

				});
			}
			
			$scope.mobileUserData = {
					name : '',
					number : '',
					sim : '',
					pin : '',
					puk : '',
					ultraOne : '',
					ultraTwo : '',
					handy : {
						imei : '',
						deliveryDate : '',
						description : '',
						handyModel : {}
					},
					contract : undefined
					
			}
			
			$scope.addMobileUser = function(){
				$scope.mobileUserData.contract = JSON.parse($scope.mobileUserData.contract);
				$scope.mobileUserData.handy.handyModel = JSON.parse($scope.mobileUserData.handy.handyModel);
				$http({
					method: 'POST',
					scope: $scope,
					url: 'mobileUser',
					data : $scope.mobileUserData,
				}).then(function successCallback(response) {
					$scope.lizenz = {
							name : '',
							key : '',
							categorie : ''	
					}
				});			
			}
			
			$scope.getBackHardware = function(hardwareRentHID, username){
				$scope.messageValue = {
						hid: hardwareRentHID,
						username : username
				}
				$http({
					method: 'PUT',
					data : $scope.messageValue,
					scope : $scope,
					url: 'ldap/user/rent/back'
				}).then(function successCallback(response) {
					$scope.changeValueFromArrayDictonariy($scope.hardware,$scope.messageValue.hid, 'hid','verliehen', false);
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
			
			$scope.addHardware = function(){
				$http({
					method: 'POST',
					data: $scope.hardwareInsert,
					url: 'hardware'
				}).then(function successCallback(response) {
					$scope.hardware.push(response.data)
					$scope.resetHardwareInsert();
				});
			}
			
			$scope.updateHardware = function(){
				$http({
					method: 'POST',
					data: $scope.hardwareInformation,
					url: 'hardware'
				}).then(function successCallback(response) {
					$scope.hardware.push(response.data)
					$scope.resetHardwareInsert();
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



			$scope.showImport = function(){
				$scope.resetViews();
				$scope.dialogConfig.dialogUpload = true;
				$scope.dialogConfig.actionUploadShow = true;
				$scope.dialogConfig.actionUploadDialog = $scope.sendImport;
				$scope.showAbstractInformation("Upload","Upload.png");
			}
			
			$scope.sendImport = function(importHardwareFiles, importLizenzFiles, importMobileFiles){
				if ($scope.importHardwareFiles != undefined && $scope.importHardwareFiles.length > 0 ){
				 Upload.upload({
			        url: 'import/hardware',
			        file: $scope.importHardwareFiles[0]
			     });
				}
				if ($scope.importLizenzFiles != undefined && $scope.importLizenzFiles.length > 0){
				 Upload.upload({
			        url: 'import/lizenz',
			        file: $scope.importLizenzData[0]
			     });
				}
				if ($scope.importMobileFiles != undefined && $scope.importMobileFiles.length > 0){
				 Upload.upload({
			        url: 'import/mobile',
			        file: $scope.importMobileData[0]
			     });
				}
			}

			$scope.show = function(netz, server, lizenz, mobile, user){
				$scope.isNetzwerkVewaltungSelected = netz;
				$scope.isUserVerwaltungSelected = user;
				$scope.isServerVerwaltungSelected = server;
				$scope.isLizenzVewaltungSelected = lizenz;
				$scope.isMobileVerwaltungSelected = mobile;
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
						} else if (response.config.data.state == 3){
							selectLizenzAsUsed.stateInfo = "reserviert";
						}
				 });
			}

			$scope.showMainConfig = {
				showChangeLogView : false,
				showAddUserWizard : false,
				showAddUserWizardInformation : false,
				showAddUserWizardHardware : false,
				showAddUserWizardOverview : false,
				showAddUserWizardPermission : false,
                showLocation : false,
                showGoogle : false
			};

			$scope.showUserAddWizardConfig = {
				showAddUserWizardBack : false,
				showAddUserWizardNext : true,
				showAddUserWizardAddUser : false
			};

			$scope.startWizardAddUser = function (){
				$scope.resetView();
				$scope.showMainConfig.showAddUserWizard = true;
				$scope.showMainConfig.showAddUserWizardInformation = true;
			};



			$scope.nextWizardAddUser = function (){
				if ($scope.showMainConfig.showAddUserWizardInformation){
					$scope.resetView();
					$scope.showMainConfig.showAddUserWizard = true;
					$scope.showMainConfig.showAddUserWizardPermission = true;
					$scope.showUserAddWizardConfig.showAddUserWizardBack = true;
				} else if ($scope.showMainConfig.showAddUserWizardPermission){
					$scope.resetView();
					$scope.showMainConfig.showAddUserWizard = true;
                    $scope.showMainConfig.showAddUserWizardOverview = true;
					$scope.showUserAddWizardConfig.showAddUserWizardBack = true;
                    $scope.showUserAddWizardConfig.showAddUserWizardAddUser = true;
                    $scope.showUserAddWizardConfig.showAddUserWizardNext = false;
				} else if ($scope.showMainConfig.showAddUserWizardHardware){
					$scope.resetView();
					$scope.showMainConfig.showAddUserWizard = true;
					$scope.showMainConfig.showAddUserWizardOverview = true;
					$scope.showUserAddWizardConfig.showAddUserWizardBack = true;
					$scope.showUserAddWizardConfig.showAddUserWizardAddUser = true;
					$scope.showUserAddWizardConfig.showAddUserWizardNext = false;
				}
			}

			$scope.prevWizardAddUser = function (){
				if ($scope.showMainConfig.showAddUserWizardHardware){
					$scope.resetView();
					$scope.showMainConfig.showAddUserWizard = true;
					$scope.showMainConfig.showAddUserWizardPermission = true;
					$scope.showUserAddWizardConfig.showAddUserWizardBack = true;
				} else if ($scope.showMainConfig.showAddUserWizardPermission){
					$scope.resetView();
					$scope.showMainConfig.showAddUserWizard = true;
					$scope.showMainConfig.showAddUserWizardInformation = true;
					$scope.showUserAddWizardConfig.showAddUserWizardNext = true;
				} else if ($scope.showMainConfig.showAddUserWizardOverview){
					$scope.resetView();
					$scope.showMainConfig.showAddUserWizard = true;
					$scope.showMainConfig.showAddUserWizardPermission = true;
					$scope.showUserAddWizardConfig.showAddUserWizardBack = true;
					$scope.showUserAddWizardConfig.showAddUserWizardNext = true;
				}
			}


			$scope.addUserWizardData = {
			    duplicateFrom : '',
				user : {
					firstname : '',
					lastname : '',
					phone : '',
					mobile : '',
					department : {},
					location : {}
				},
				permission : {
					reservixIntern : false,
					reservixExtern : false,
					confluence : false,
					jira : false
				}
			};

			
			$scope.resetView = function(){
				$scope.showUserRefreshButton = false;
				$scope.showUserCreateButton = false;
				$scope.showUserClearButton = false;
				$scope.showUserInfos = true;
				$scope.showUserSearch = true;
				$scope.showUserDisableDate = false;
				$scope.isSettingsSelected = false;
				$scope.isChangelogSelected = false;
				$scope.isLizenzVewaltungSelected = false;
				$scope.isNetzwerkVewaltungSelected = false;
				$scope.isUserVerwaltungSelected = false;
				$scope.isUserVerwaltungUpdateSelected = false;
				$scope.isUserVerwaltungCreateSelected = false;
				$scope.isServerVerwaltungSelected = false;
				$scope.isMobileVewaltungSelected = false;
				$scope.isSoftwareSelected = false;
				$scope.userVerwaltung = false;
				$scope.systemVerwaltung = false;
				$scope.showMainConfig = {
					showChangeLogView : false,
					showAddUserWizard : false,
					showAddUserWizardInformation : false,
					showAddUserWizardHardware : false,
					showAddUserWizardOverview : false,
                    showLocation : false,
                    showGoogle : false
				};
				$scope.showUserAddWizardConfig = {
					showAddUserWizardBack : false,
					showAddUserWizardNext : true,
					showAddUserWizardAddUser : false
				};
			};

			$scope.changelogInformation = {};
			$scope.changelogList = [];

			$scope.resetChangelogInformation = function(){
				$scope.changelogInformation = {
				    title : '',
					username : '',
					system : '',
					changelog : ''
				}
			};


			$scope.resetChangelogInformation();

			$scope.findHardwareByName = function(name){
				for (var index=0;index<$scope.hardware.length;index++){
					if ($scope.hardware[index].hostname.toLowerCase() == name.toLowerCase()){
						return $scope.hardware[index];
					}
				}

				return "-1";
			};

			$scope.addChangelogHardware = function(hardware){
				$scope.changelogInformation.hid = hardware.hid;
				$scope.changelogInformation.system = hardware.hostname;
				$scope.postChangelog($scope.changelogInformation,$scope.hardwareInformation.changelogList)
			};

			$scope.addChangelog = function(){
				var host = $scope.findHardwareByName($scope.changelogInformation.system);

				if (host != '-1'){
					$scope.changelogInformation.system = host.hostname;
					$scope.changelogInformation.hid = host.hid;
				}

				$scope.postChangelog($scope.changelogInformation,$scope.hardwareInformation.changelogList);
			};

			$scope.postChangelog = function(changelogData, changelogList){
				$http({
					method: 'POST',
					scope: $scope,
					url: 'changelog',
					data : changelogData,
				}).then(function successCallback(response) {
					var dateInfo = new Date(response.data.date);
					response.data.date = dateInfo.getDay() +"."+dateInfo.getMonth()+"."+dateInfo.getFullYear()+" "+dateInfo.getHours()+":"+dateInfo.getMinutes()+":"+dateInfo.getSeconds();
					$scope.hardwareInformation.changelogList.push(response.data);
					$scope.mainThis.dynamicItemsChangelogHardware.addData(response.data)
					$scope.resetChangelogInformation();
				});
			}

			$scope.changelogView = {};
			$scope.changelogHWView = false;
			$scope.changelogHWData = "";
			$scope.lastChangelogView = undefined;
            $scope.showHardwareChangelog = function(changelog){
                $scope.changelogHWData = changelog.changelog;

                if ($scope.lastChangelogView !== undefined && $scope.lastChangelogView !== changelog && $scope.changelogHWView){
                    if (!$scope.changelogHWView){
                        $scope.changelogHWView = false;
                    }else{
                        $scope.changelogHWView = true;
                    }
                }else {
                    if ($scope.changelogHWView){
                        $scope.changelogHWView = false;
                    }else{
                        $scope.changelogHWView = true;
                    }
                }

                $scope.lastChangelogView = changelog;
            }

			$scope.showChangelog = function (changelog) {
				$scope.resetViews();
				$scope.changelogView = changelog;
				$scope.dialogConfig.showChangelog = true;
				$scope.showAbstractInformation("Changelog - "+changelog.title,"Changelog.png");
			};

			$scope.changelogRows = 14;
			$scope.changelogPage = 1;
			$scope.aktivChangelogShow = 1;
			$scope.changelogPages = [];

			this.getChangeLogList = function(){
				if ($scope.changelogRows == 'All'){
					return $scope.changelogList;
				}
				var filterChangelog = $scope.changelogList;
				$scope.changelogPages = [];
				$scope.setChangelogViewPages(filterChangelog, $scope.changelogPages,$scope.changelogRows);
				$scope.changelogMaxPage = Object.keys($scope.changelogPages).length;
				$scope.aktivChangelogShow = Object.keys($scope.changelogPages[$scope.changelogPage] != undefined ? $scope.changelogPages[$scope.changelogPage] : 1).length;
				return $scope.changelogPages[$scope.changelogPage];
			};


			$scope.selectMenu = function (selectMenue) {
				
				if (selectMenue.indexOf("show") > -1){
					$scope.resetView();
				}

                if (selectMenue == 'showGoogle') {
                    $scope.showMainConfig.showGoogle = true;
                } else if (selectMenue == 'showChangeLogView') {
					$scope.showMainConfig.showChangeLogView = true;
					data.loadHardware($scope,'sonstiges');
				} else if (selectMenue == 'showLizenz') {
					data.loadLizenz($scope,"-1");
					$scope.isLizenzVewaltungSelected = true;
				}else if (selectMenue == 'showSettings') {
					$scope.isSettingsSelected = true;
				} else if (selectMenue == 'showClients') {
					$scope.systemVerwaltung = true;
					data.loadHardware($scope,'clients');
					$scope.isNetzwerkVewaltungSelected = true;
					$scope.showMainConfig.showLocation = true;
				} else if (selectMenue == 'showServer') {
					$scope.systemVerwaltung = true;
					data.loadHardware($scope,'sonstiges');
					$scope.isServerVerwaltungSelected = true;
                    $scope.showMainConfig.showLocation = true;
				} else if (selectMenue == 'showMobile') {
					$scope.systemVerwaltung = true;
					$scope.isMobileVewaltungSelected = true;
				} else if (selectMenue == 'showSoftware') {
					$scope.systemVerwaltung = true;
					$scope.isSoftwareSelected = true;
				} else if (selectMenue == 'showUpdateUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true);
					$scope.isUserVerwaltungUpdateSelected = true;
					$scope.isUserVerwaltungCreateSelected = false;
					$scope.infoFeld = "User Aktualisieren";
					$scope.menueIcon = "/NetView/static/img/updateuser.png";
				} else if (selectMenue == 'showCreateUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true);
					$scope.showUserSearch = false;
					$scope.isUserVerwaltungUpdateSelected = false;
					$scope.isUserVerwaltungCreateSelected = true;
					$scope.infoFeld = "User hinzufügen";
					$scope.menueIcon = "/NetView/static/img/adduser.png";
				} else if (selectMenue == 'showUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true);
					$scope.isUserVerwaltungUpdateSelected = false;
					$scope.isUserVerwaltungCreateSelected = false;
					$scope.infoFeld = "User";
					$scope.menueIcon = "/NetView/static/img/user.png";
				} else if (selectMenue == 'showDeleteUser') {
					$scope.userVerwaltung = true;
					$scope.systemVerwaltung = false;
					$scope.show(false,false, false ,false,true);
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

					$scope.resetViews();
					$scope.dialogConfig.dialogHardware = true;
					$scope.dialogConfig.actionAddShow = true;
					$scope.dialogConfig.actionAddDialog = $scope.addHardware;
					$scope.showAbstractInformation("Hardware Hinzufuegen","addHardware.png");
				}else if (selectMenue == 'lizenzAdd') {
					$scope.resetLizenz();
					$scope.resetViews();
					$scope.dialogConfig.dialogLizenzen = true;
					$scope.dialogConfig.actionAddShow = true;
					$scope.dialogConfig.actionAddDialog = $scope.addLizenz;
					$scope.showAbstractInformation("Lizenz hinzufuegen","Lizenz.png");
				}else if (selectMenue == 'mobileUserAdd'){
					$scope.resetViews();
					$scope.dialogConfig.dialogMobile = true;
					$scope.dialogConfig.actionAddShow = true;
					$scope.dialogConfig.actionAddDialog = $scope.addMobileUser;
					$scope.showAbstractInformation("Mobile User hinzufuegen","Lizenz.png");
				}else if (selectMenue == 'addChangeLog'){
					$scope.resetViews();
					$scope.dialogConfig.dialogChanglog = true;
					$scope.dialogConfig.actionAddShow = true;
					$scope.dialogConfig.actionAddDialog = $scope.addChangelog;
					$scope.showAbstractInformation("Changlog hinzufügen","Changelog.png");
				}
			}
			
			
			$scope.showAbstractInformation = function(title, img){

				$scope.titleImg = "static/img/"+img;
				$scope.titleDialog = title;
				
				$mdDialog.show({
						scope: $scope.$new(),
						templateUrl: 'static/dialog/AbstractDialog.html',
						clickOutsideToClose: true,
						fullscreen: $scope.customFullscreen
					});
			}

			$scope.showUserTelefon = false;
			$scope.showUserTV = false;
			$scope.showUserDetails = false;

			// @ts-ignore
			$scope.selectUpdateUser = function (searchUserText) {
                $scope.showUserTV = false;
                $scope.showUserTelefon = false;
                $scope.config.userdetails = false;
                $scope.showUserDetails = true;

                $scope.userInformation = $scope.findUserDetailsByDisplayname(searchUserText);

                $http.get("ldap/user/"+$scope.userInformation.uid+"/hostname",{header : {'Content-Type' : 'application/json; charset=utf-8'}}).then(function(response){
                    if (response.data.hostname != '' && response.data.hostname != undefined){
                        $scope.teamviewerID = response.data.hostname;
                        $scope.showUserTV = true;
                    }
                });


				if ($scope.userInformation != undefined && $scope.userInformation != "" && $scope.userInformation.telephoneNumber != "" || $scope.userInformation.mobile != "" ){
				    $scope.showUserTelefon = true;
                }else{
                    $scope.showUserDetails = false;
                    $scope.userInformation = undefined;
                }
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
			
			
			$scope.messageValue;
			
			$scope.delHardware = function (hardware, ev) {
				
				$scope.dialogMessageReset();
				
				$scope.configMessageDialog.dialogYesNoMessage = true;
				$scope.configMessageDialog.hardwareDelete = true;
				if (hardware != undefined && hardware.hostname != undefined)
				 $scope.configMessageDialog.titleMessageDialog = "Hardware entfernen ("+hardware.hostname+")";
				else
					$scope.configMessageDialog.titleMessageDialog = "Hardware entfernen";
				
				$scope.configMessageDialog.dialogMessage = "Sind Sie sicher das die Hardware entfernt werden soll?"
				$scope.messageValue = hardware;
				
				$mdDialog.show({
					scope: $scope.$new(),
					templateUrl: 'static/dialog/ask/MessageDialog.html',
					clickOutsideToClose: true,
					controller: DialogController,
					fullscreen: $scope.customFullscreen
				})		
				
				
			};

			$scope.archivHardware = function (hardware, reaktivieren) {

				$scope.dialogMessageReset();

				$scope.configMessageDialog.dialogYesNoMessage = true;
				$scope.configMessageDialog.hardwareArchiv = true;

				if (reaktivieren)
					$scope.configMessageDialog.titleMessageDialog = "Hardware lagern? ("+hardware.hostname+")";
				else
					$scope.configMessageDialog.titleMessageDialog = "Hardware reaktivieren? ("+hardware.hostname+")";

				$scope.messageValue = hardware;

				$mdDialog.show({
					scope: $scope.$new(),
					templateUrl: 'static/dialog/ask/MessageDialog.html',
					clickOutsideToClose: true,
					controller: DialogController,
					fullscreen: $scope.customFullscreen
				})


			};
			
			$scope.searchMessageUserValue;
			
			$scope.selectMessagUser = function(item){
				$scope.searchMessageUserValue = item;
		    }
			
			$scope.ctrl.searchMessageUserText = "";
			
			function DialogController($scope, $mdDialog) {
				$scope.archivHardwareDialog = function() {
					$http({
						method: 'PUT',
						params: {
							'hid' : $scope.messageValue.hid,
						},
						scope : $scope,
						url: 'hardware/archiv'
					}).then(function successCallback(response) {
						if ($scope.hardwareLager)
							$scope.changeItemFromArrayDictonariy($scope.hardware,$scope.messageValue.hid, 'hid',"status","0");
						else
							$scope.changeItemFromArrayDictonariy($scope.hardware,$scope.messageValue.hid, 'hid',"status","1");

					});

					$mdDialog.hide();
				};


				$scope.deleteHardwareDialog = function() {
			    	$http({
						method: 'DELETE',
						params: {
							'hid' : $scope.messageValue.hid,
						},
						scope : $scope,
						url: 'hardware'
					}).then(function successCallback(response) {
						$scope.removeItemFromArrayDictonariy($scope.hardware,$scope.messageValue.hid, 'hid');
					});
			    	
			    	$mdDialog.hide();
			    };
			    
			    $scope.changeManagemerHardwareDialog = function(){
			    	var userinfo = $scope.findUserDetailsByDisplayname($scope.searchMessageUserValue);
			    	$scope.messageValue = {
			    		uid : userinfo.uid,
			    		hid : $scope.hardwareInformation.hid
			    	}
			    	
			    	$http({
		    			method: 'PUT',
		    			data : $scope.messageValue,
		    			scope : $scope,
		    			url: 'hardware/owner'
		    		}).then(function successCallback(response) {
		    			$mdDialog.hide();
				    	$scope.showHardwareInformation($scope.hardwareInformation.hid);
		    		});
			    	
			    	
			    }
			    
			    $scope.changeManagemerHardwareDialogCancel = function(){
			    	$mdDialog.hide();
			    	$scope.showHardwareInformation($scope.hardwareInformation.hid);
			    }
			    
			    $scope.rentHardwareDialog = function(rentDate) {
			    	var userinfo = $scope.findUserDetailsByDisplayname($scope.searchMessageUserValue);
			    	
			    	if (userinfo['uid'] != undefined && rentDate != "" && rentDate != undefined){
			    		$scope.messageValue.uid = userinfo.uid;
			    		$scope.messageValue.date = new Date(rentDate).getTime();
			    		$http({
			    			method: 'PUT',
			    			data : $scope.messageValue,
			    			scope : $scope,
			    			url: 'ldap/user/rent'
			    		}).then(function successCallback(response) {
			    			$scope.changeValueFromArrayDictonariy($scope.hardware,$scope.messageValue.hid, 'hid','verliehen', true);
			    			$scope.changeValueFromArrayDictonariy($scope.hardware,$scope.messageValue.hid, 'hid','verliehenAn', $scope.messageValue.uid);
			    			$scope.changeValueFromArrayDictonariy($scope.hardware,$scope.messageValue.hid, 'hid','verliehenBis', $scope.formatDate(new Date($scope.messageValue.date)));
			    		});
			    	
			    		$mdDialog.hide();
			    	}
			    }
			    
			    $scope.cancel = function(){
			    	$mdDialog.hide();
			    }
			  }
			
			$scope.changeManagement = function(){
				$scope.dialogMessageReset();
				
				$scope.configMessageDialog.dialogUserMessage = true;
				$scope.configMessageDialog.titleMessageDialog = "Verwantworlicher - "+$scope.hardwareInformation.hostname;
				$scope.configMessageDialog.setHardwareManagerDilog = true;			
				
				$mdDialog.show({
					scope: $scope.$new(),
					templateUrl: 'static/dialog/ask/MessageDialog.html',
					clickOutsideToClose: false,
					controller: DialogController
				});
			}
			
			$scope.formatDate = function(date) {
			    var d = date.getDate();
			    var m = date.getMonth() + 1; //Month from 0 to 11
			    var y = date.getFullYear();
			    return (d <= 9 ? '0' + d : d) + '.' + (m<=9 ? '0' + m : m) + '.' + y;
			}
			
			$scope.findUserDetailsByDisplayname = function(username){
				$scope.searchUserText = username;
		    	return $scope.people.find($scope.findUser);
			}
			
			$scope.changeValueFromArrayDictonariy = function(hardwareList, id, searchName, name, value){
				for( var i = 0; i < hardwareList.length; i++){ 
				   if ( hardwareList[i][searchName] === id) {
					   hardwareList[i][name] = value;
				   }
				}
			}
			
			$scope.removeItemFromArrayDictonariy = function(values, remove, searchName){
				for( var i = 0; i < values.length; i++){ 
				   if ( values[i][searchName] === remove) {
					 values.splice(i, 1); 
				   }
				}
			}

			$scope.changeItemFromArrayDictonariy = function(values, change, searchName, changeField, newValue){
				for( var i = 0; i < values.length; i++){
					if ( values[i][searchName] === change) {
						values[i][changeField] = newValue;
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
						'lid' : lizenz.lid
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
						'hid' : hardware.hid
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
			$scope.showLizenzHardwareView = true;
			$scope.showLizenzInformation = function(lizenz){
				
				$scope.lizenz = lizenz;
				$scope.lizenzHostList = $scope.lizenz.hardware;
				
				$scope.resetViews();

				$scope.dialogConfig.actionUpdateDialog = $scope.updateLizenz;
				$scope.dialogConfig.actionUpdateShow = true;
				$scope.dialogConfig.dialogLizenzen = true;
				$scope.showLizenzHardwareView = true;
				
				$scope.showAbstractInformation("Lizenz","Lizenz.png");
				
			};

			$scope.teamviewerID = "";
			
			$scope.showUserInformation = function (uid) {

				$scope.resetHardwareView();
				
				var username = uid.split(".")[0]+'_'+uid.split(".")[1];
				$http({
					method: 'GET',
					scope: $scope,
					sync: true,
					url: 'ldap/user/'+username
				}).then(function successCallback(response) {
					data.loadLizenz($scope,"0");
                    $scope.resetViews();

					$scope.teamviewerID = "";
					$scope.showHardwareUser = true;
					$scope.showHardwareUserDetails = true;
					$scope.hardwareUser = response.data.userData
					$scope.hardwareAktivList = response.data.aktivHardware;
					$scope.hardwareOwnerList = response.data.ownerHardware;
					$scope.hardwareLizenzen = response.data.lizenz;
					$scope.hardwareUserDetails = response.data.ldapUser == null ? {} :response.data.ldapUser;
					$scope.hardwareUserDetails["username"] = response.data.userData.uid;
					
					$scope.listHardwareInformationList=true;
					
					if ($scope.hardwareAktivList != undefined && $scope.hardwareAktivList.length > 0){
						$scope.showHardwareViewInformation = true;
						$scope.hardwareInformation = $scope.hardwareAktivList[0];
                        $scope.teamviewerID = $scope.hardwareInformation.hostname;
                    }
					
					if ($scope.hardwareOwnerList != undefined && $scope.hardwareOwnerList.length > 0){
						$scope.showHardwareOwner = true;
						$scope.hardwareOwner = $scope.hardwareOwnerList[0];
						if ($scope.teamviewerID == ""){
                            $scope.teamviewerID = $scope.hardwareOwner.hostname;
                        }
					}


                    $scope.showTelefon($scope.hardwareUser);
					$scope.dialogConfig.showHardware = true;

					if ($scope.teamviewerID != ""){
                        $scope.dialogConfig.actionTVShow = true;
                    }

					$scope.showAbstractInformation($scope.hardwareUser.displayName,"Mitarbeiter/"+$scope.hardwareUser.displayName+".jpg");
					
				});
			};

			$scope.showMobileInformation = function (mid) {

				$scope.resetHardwareView();
				
				$http({
					method: 'GET',
					scope: $scope,
					sync: true,
					url: 'mobile/'+mid
				}).then(function successCallback(response) {
					data.loadLizenz($scope,"0");
					$scope.mobileInformation = response.data;
					
					
					if ($scope.mobileInformation.user != undefined && $scope.hardwareInformation.ownerInformation.displayName != undefined){
						$scope.showHardwareUserOwner = true;
						$scope.hardwareOwner = $scope.hardwareInformation.ownerInformation;
						
					}
					
					if ($scope.mobileInformation.hardwareOwner != undefined){
						$scope.showHardwareUser = true;
						$scope.hardwareUser = $scope.hardwareInformation.inUseInformation;
					}
					
					if ($scope.mobileInformation.hardwareAktiv != undefined){
						$scope.showHardwareUser = true;
						$scope.hardwareUser = $scope.hardwareInformation.inUseInformation;
					}

					$scope.hardwareLizenzen = $scope.hardwareInformation.lizenz;
					
					$scope.resetViews();
					
					
					$scope.showAbstractInformation("Mobile","Hardware.png");
				});
			}
			
			$scope.showHardwareInformation = function (hid, editable) {

				$scope.resetHardwareView();
				
				$http({
					method: 'GET',
					scope: $scope,
					sync: true,
					url: 'hardware/'+hid
				}).then(function successCallback(response) {
					data.loadLizenz($scope,"0");
					$scope.hardwareInformation = response.data;
					$scope.showHardwareViewInformation = true;
					$scope.showHardwareViewLizenz = true;
                    $scope.teamviewerID = $scope.hardwareInformation.hostname;

					if ($scope.hardwareInformation.changelogList != undefined){
						$scope.hardwareInformation.changelogList.forEach(function (data) {
							data.date = new Date(data.date).toLocaleDateString();
						});
					}

                    $scope.mainThis.dynamicItemsChangelogHardware = new DynamicItems($scope.hardwareInformation.changelogList);
					$scope.mainThis.dynamicItemsLizenzHardware = new DynamicItems($scope.hardwareInformation.lizenz);
					$scope.mainThis.dynamicItemsHardware = new DynamicItems($scope.hardware);

                    $scope.resetViews();

					if ($scope.hardwareInformation.ownerInformation != undefined && $scope.hardwareInformation.ownerInformation.displayName != undefined){
						$scope.showHardwareUserOwner = true;
						$scope.hardwareOwner = $scope.hardwareInformation.ownerInformation;
						if ($scope.hardwareOwner.changelogList != undefined){
							$scope.hardwareOwner.changelogList.forEach(function (data) {
								data.date = new Date(data.date).toLocaleDateString();
							});
						}
                        $scope.mainThis.dynamicItemsChangelogHardwareOwner = new DynamicItems($scope.hardwareOwner.changelogList);

					}

					if ($scope.hardwareInformation.inUseInformation != undefined && $scope.hardwareInformation.inUseInformation.displayName != undefined){
						$scope.showHardwareUser = true;
                        $scope.showTelefon($scope.hardwareInformation.inUseInformation);
						$scope.hardwareUser = $scope.hardwareInformation.inUseInformation;
                    }

					$scope.dialogConfig.actionUpdateShow = editable;
					$scope.dialogConfig.actionUpdateDialog = $scope.updateHardware;

					$scope.dialogConfig.actionMgmtShow = true;
					$scope.dialogConfig.showHardware = true;
					$scope.dialogConfig.actionTVShow = true;
					$scope.dialogConfig.actionMgmtDialog = $scope.changeManagement;

					var title = $scope.hardwareInformation.hostname +" ("+$scope.hardwareInformation.lastLogin+")";
					if ($scope.hardwareInformation.lastLogin == undefined){
						title = $scope.hardwareInformation.hostname;
					}

					$scope.showAbstractInformation(title,"Hardware.png");
				});
			};	

			$scope.showTelefon = function (user){
                if (user.telephoneNumber != undefined && user.telephoneNumber != ""){
                    $scope.dialogConfig.showTelefon = true;
                    $scope.telefonNumber = user.telephoneNumber;
                } else if (user.mobile != undefined && user.mobile != "") {
                    $scope.dialogConfig.showTelefon = true;
                    $scope.telefonNumber = user.mobile;
                }
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
			
			
			
			
			$scope.dialogMessageReset = function(){
				$scope.configMessageDialog = {
				  hardwareDelete: false,
				  hardwareArchiv : false,
				  dialogUserMessage : false,
				  dialogYesNoMessage : false,
				  actionUserMessageCancel : undefined,
				  actionYesNoMessageNo : undefined,
				  actionYesNoMessageYes : undefined,
				  actionUserMessageOK : undefined,
				  titleMessageDialog : "",
				  setHardwareManagerDilog : false,
				  showMessageDialogDate : false
				}
			}
			
			$scope.dialogMessageReset();
			
			$scope.dialogUserMessageValue;
			
			this.messageUserDate = "";
			
			
			
			$scope.rentHardware = function(hw){
				$scope.dialogMessageReset();
				$scope.configMessageDialog.rentHardwareDilog = true;
				$scope.configMessageDialog.dialogUserMessage = true;
				$scope.configMessageDialog.showMessageDialogDate = true;
				$scope.configMessageDialog.titleMessageDialog = "Verliehen an";
				$scope.messageValue = {};
				$scope.messageValue.hid = hw.hid;
				
				
				$mdDialog.show({
					scope: $scope.$new(),
					templateUrl: 'static/dialog/ask/MessageDialog.html',
					clickOutsideToClose: true,
					controller: DialogController,
					fullscreen: $scope.customFullscreen
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
						
			$scope.changeValueHW = function(selectedHardware){
				$scope.hardwareOwner = JSON.parse(selectedHardware);
				if ($scope.hardwareInformation.hostname == "")
				 $scope.teamviewerID =  $scope.hardwareOwner.hostname;
			}
			
			$scope.hardwareOwner = {}
			$scope.hardwareUserDetails = {};
			
			$scope.resetHardwareView = function(){
				$scope.showHardwareUser = false;
				$scope.showHardwareUserOwner = false;
				$scope.showHardwareViewInformation = false;
				$scope.showHardwareOwner = false;
				$scope.showHardwareViewLizenz = false;
				$scope.listHardwareInformationList=false;
				$scope.showHardwareUserDetails = false;				
			}
			
			$scope.saveUserDetails = function(hardwareUserDetails){
				$http.post("ldap/user", hardwareUserDetails, {
					'Content-Type': 'application/json'
				});
			}

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


			$scope.hardwareRows ='14';
			$scope.clientsRows = '9';
			$scope.mobilesRows = '14';
			$scope.lizenzRows ='14';
			
			$scope.hardwareRowsList = [
				{value :9},
				{value :14},
				{value :50},
				{value :100},
				{value :200},
				{value :'All'}
				];



			$scope.setChangelogViewPages = function(changelog, values,rows){
				if (values != undefined){
					if (changelog.length >= 11){
						var page = [];
						var pageAmount = rows;
						var pageSize=1;
						for (var i=0;i<changelog.length;i++){
							if (pageAmount > 0){
								page.push(changelog[i]);
								pageAmount--;
							}else{
								values[pageSize] = page;
								page = [];
								pageSize++;
								pageAmount = rows;
								i=i-1;
							}
						}

						if (Object.keys(page).length > 0)
							values[pageSize] = page;

					}else{
						values[1] = changelog;
					}
				}
			}
			
			$scope.setMobileViewPages = function(data, values, rows){
				if (values != undefined){
					if (data.length >= 11){						
						var page = [];
						var pageAmount = rows;
						var pageSize=1;
						for (var i=0;i<data.length;i++){
							if (pageAmount > 0){
								page.push(mobile[i]); 
								pageAmount--;
							}else{
								data[pageSize] = page;
								pageSize++;
								pageAmount = rows;
								i=i-1;
							}
						}
						
						if (Object.keys(page).length > 0)
							values[pageSize] = page;
						
					}else{	
						values[1] = data;
					}
				}
			}
			
			$scope.clientMaxPage = 1;
			
			$scope.setLizenzViewPages = function(lizenzen, values,rows){
				if (values != undefined){

					if (lizenzen.length >= 11){
						var page = [];
						var pageAmount = rows;
						var pageSize=1;
						for (var i=0;i<lizenzen.length;i++){
							if (pageAmount > 0){
								page.push(lizenzen[i]);
								pageAmount--;
							}else{
								values[pageSize] = page;
								page = [];
								pageSize++;
								pageAmount = rows;
								i=i-1;
							}
						}

						if (Object.keys(page).length > 0)
							values[pageSize] = page;

					}else{
						values[1] = lizenzen;
					}
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
			
			$scope.resetPage = false;
			
			$scope.nextPage = function(view){
				if (view == 'hardware'){
					if (Object.keys($scope.hardwareServerPages).length > $scope.hardwarePage){
						$scope.hardwarePage++;
					}
				}else if(view == 'client'){
					if (Object.keys($scope.hardwareClientsPages).length > $scope.clientPage){
						$scope.clientPage++;
					}
				}else if(view == 'lizenz'){
					if (Object.keys($scope.lizenzPages).length > $scope.lizenzPage){
						$scope.lizenzPage++;
					}
				}else if(view == 'mobile'){
					if (Object.keys($scope.mobilePage).length > $scope.mobilePage){
						$scope.mobilePage++;
					}
				}else if(view == 'changelog'){
					if (Object.keys($scope.changelogPages).length > $scope.changelogPage){
						$scope.changelogPage++;
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
				}else if(view == 'mobile'){
					if (1 < Object.keys($scope.mobilePage).length && $scope.mobilePage != 1){
						$scope.mobilePage--;
					}
				}else if(view == 'changelog'){
					if (1 < Object.keys($scope.changelogPages).length && $scope.changelogPage != 1){
						$scope.changelogPage--;
					}
				}
			}
			
			$scope.categorieHardwareValue = "All";
			$scope.hardwareActivValue = "All";
			$scope.clientsActivValue = "All";
			$scope.hardwareLager = false;

			$scope.netzwerkSearchParameter = {
				searchValue : '',
				result : []
			};

			$scope.setClientsPage = function(){
				$scope.clientPage = 1;
			}	
			
			$scope.setHardwarePage = function(){
				$scope.hardwarePage = 1;
			}
			
			$scope.setLizenzPage = function(){
				$scope.lizenzPage = 1;
			}
			
			$scope.setMobilePage = function(){
				$scope.mobilePage = 1;
			}
			

			$scope.lizenzPageAmount = 0;
			
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
						for (var key in lizenzData[i]) {
							if (lizenzData[i][key] != null) {
								if (typeof lizenzData[i][key] == "number" || typeof lizenzData[i][key] == "object") {
									if (lizenzData[i][key] == lowerCaseSearchLizenz) {
										result.push(lizenzData[i]);
										break;
									}
								} else if (lizenzData[i][key].toLowerCase().indexOf(lowerCaseSearchLizenz) != -1) {
									result.push(lizenzData[i]);
									break;
								}
							}
						}
					}
					
					lizenzData = result;
					
				 }

				if ($scope.showAllLizenz){
					$scope.lizenzViewPages = false;
					$scope.lizenzMaxPage = Object.keys(lizenzData).length;
					$scope.lizenzPageAmount = Object.keys(lizenzData).length;
					return lizenzData;
				}


				$scope.lizenzPages = [];
				$scope.setLizenzViewPages(lizenzData,$scope.lizenzPages,$scope.lizenzRows);

				if ($scope.lizenzPages != undefined && $scope.lizenzPages != null){
					$scope.lizenzMaxPage = Object.keys($scope.lizenzPages).length;
					$scope.lizenzPageAmount = Object.keys($scope.lizenzPages[$scope.lizenzPage]).length;						
				} else {
					$scope.lizenzMaxPage = 0;
					$scope.lizenzPageAmount = 0;
				}
				
				return $scope.lizenzPages[$scope.lizenzPage];	
				 
			}
			
			this.getMobile= function(){
				 var lowerCaseSearch = $scope.searchMobile.toLowerCase();
				 var result = [];
				 if (lowerCaseSearch != ''){	
					var result = new Array();
					for (var i=0;i<$scope.mobileUserList.length;i++){
						for (var key in $scope.mobileUserList[i]) {
							if ($scope.mobileUserList[i][key] != null && $scope.mobileUserList[i][key].toLowerCase().indexOf(lowerCaseSearch) != -1){
								result.push($scope.mobileUserList[i]);
								break;
							}
						}
					}
				 }else{
					 result = $scope.mobileUserList;
				 }

				 return  result;
				 
				if ($scope.showAllLizenz){
					$scope.lizenzMaxPage = Object.keys(result).length;
					$scope.lizenzPageAmount = Object.keys(result).length;
					return result;
				}
				
				$scope.setMobileViewPages(result, $scope.mobilePages,$scope.mobileRows);
				
				if ($scope.result != undefined && $scope.result != null){
					$scope.mobileMaxPage = Object.keys($scope.result).length;
					$scope.mobilePageAmount = Object.keys($scope.mobilePages[$scope.mobilePage]).length;
				}else{
					$scope.mobileMaxPage = 0;
					$scope.mobilePageAmount =0;
				}
				
				if ($scope.mobilePages != undefined && $scope.mobilePages.length > 0)
				 return $scope.mobilePages[$scope.mobilePage];	
				else
				 return [];
				 
			}
			
			

			$scope.querySearch = querySearch;

			$scope.resetInformation = function () {
				$scope.user = {};
			}

			function querySearch(query) {
				var results = query ? $scope.people.filter(createFilterFor(query)) : $scope.people,
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
				return function filterFn(people) {
					return (people.uid.indexOf(lowercaseQuery) > -1);
				};
			}
		});