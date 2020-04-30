// @ts-ignore
app.controller('ListCtrl', function ($scope,$mdDialog, $http,data, lizenzService,viewService,hardwareService,changelogService) {
	// Load Config
	$scope.loginUser = "Manfred Fischer";
	$scope.ldapUser = [];
	$scope.lizenzList = [];
	$scope.abstractDialogData;
	$scope.freeLizenz = [];
	$scope.locationList = [];
	$scope.activWindow = $mdDialog;
	$scope.selectedLocation = "{\"displayname\":\"Standort...\"}";

	$scope.selectedHW = undefined;
	$scope.selectedlizenz = undefined;
	$scope.selectedChangelog = undefined;
	$scope.selectedSoftware = undefined;

	$scope.hardwareDetails = {
		view : false,
		tabTitle : '',
		title : '',
		hardware : undefined,
		lizenz : undefined,
		changelog : undefined,
		selectedHW : undefined,
		selectedlizenz : undefined,
		selectedChangelog : undefined,
		selectedSoftware : undefined
	};

	$scope.hardwareActivDetails = {
		view : false,
		tabTitle : '',
		title : '',
		hardware : undefined,
		lizenz : undefined,
		changelog : undefined,
		selectedHW : undefined,
		selectedlizenz : undefined,
		selectedChangelog : undefined,
		selectedSoftware : undefined
	};

	$scope.hardwareRentDetails = {
		view : false,
		tabTitle : '',
		title : '',
		hardware : undefined,
		lizenz : undefined,
		changelog : undefined,
		selectedHW : undefined,
		selectedlizenz : undefined,
		selectedChangelog : undefined,
		selectedSoftware : undefined
	};

	$scope.hardwareList = [];
	$scope.ownerDetails = {
		view : false,
		tabTitle : '',
		user : undefined
	};
	$scope.activUserDetails = {
		view : false,
		tabTitle : '',
		user : undefined
	};
	$scope.userDetails = {
		view : false,
		tabTitle : '',
		user : undefined
	};

	$scope.changelogList = [];

	$scope.setView = function (){

		$scope.hardwareDetails = {
			title : 'Details',
			hardware : undefined
		};

		$scope.dialogAction = {
			add : {
				show : false,
				action : undefined,
				value : undefined
			},
			update : {
				show : false,
				action : undefined,
				value : undefined
			},
			changelog : {
				show : false,
				action : undefined,
				value : undefined
			},
			lizenz : {
				show : false,
				action : undefined,
				value : undefined
			},
			responsible : {
				show : false,
				action : undefined,
				value : undefined
			},
			rent : {
				show : false,
				action : undefined,
				value : undefined
			},
			delete : {
				show : false,
				action : undefined,
				value : undefined
			},
			teamviewer : {
				show : false,
				action : $scope.openTeamViewer,
				value : "L1090"
			},
			telefon : {
				show : false,
				value : []
			}
		};

		$scope.showConfig = {
			user : {
			  	details : false,
				austritt : false,
				teamviewer : false,
				telefon : false
			},
			menue : {
				datei : {
					show : true,
					setting : false,
					upload : false,
					logout : true
				},
				system : {
					show : true,
					client : true,
					netz : true,
					changelog : true,
					lizenz : true,
					addHardware : false,
					addLizenz : false,
					addChangelog : false
				},
				user : {
					show : true,
					user : true
				},
				software : {
					show : false,
					software : false,
					addSoftware : false,
					delSoftware : false
				}
			},
			view : {
				logo : true,
				changelog : false,
				lizenz : false,
				net : false,
				client : false,
				user : true,
				software : false,
				setting : false,
				userwizard : false
			},
			details : {
				hardware : false,
				changelog : false,
				lizenz : false
			}
		}
	};

	$scope.setView();

	$scope.dialogMessageReset = function(){
		$scope.configMessageDialog = {
			titleMessageDialog : '',
			message : '',
			value : undefined,
			dialogYesNoMessage : {
				show : false,
				deleteAction : undefined,
				archivAction : undefined
			},
			dialogChangelog : false,
			dialogLizenzMessage : {
				show : false
			},
			dialogUserMessage : {
				show : false,
				setDate : false,
				action : undefined
			}
		}
	};
	$scope.dialogMessageReset();

	// read default value

	$http.get('static/translation/translation.json',{header : {'Content-Type' : 'application/json; charset=utf-8'}}).then(function(res){
		var language = navigator.language.split("-")[0]
		$scope.translation = res.data[language];
		$scope.title = $scope.translation.shortcurt.clients
	});

	data.loadLocation($scope);
	data.loadUser($scope);

	// Abstract Dialog
	$scope.showAbstractInformation = function(title, subtitle, img, data){
		$scope.abstractDialogData = data;
		$scope.titleImg = "static/img/"+img;
		$scope.titleDialog = title;
		$scope.subTitleDialog = subtitle == undefined ? "" : subtitle;

		$mdDialog.show({
			scope: $scope.$new(),
			templateUrl: 'static/dialog/AbstractDialog.html',
			clickOutsideToClose: true
		});

	};



	// Menue

	$scope.resetHardwareView = function(){

		for (var key of Object.keys($scope.activUserDetails)) {
			$scope.activUserDetails[key] = undefined;
		}

		for (var key of Object.keys($scope.ownerDetails)) {
			$scope.ownerDetails[key] = undefined;
		}

		for (var key of Object.keys($scope.hardwareDetails)) {
			$scope.hardwareDetails[key] = undefined;
		}

		for (var key of Object.keys($scope.hardwareActivDetails)) {
			$scope.hardwareActivDetails[key] = undefined;
		}

		for (var key of Object.keys($scope.hardwareRentDetails)) {
			$scope.hardwareRentDetails[key] = undefined;
		}

		for (var key of Object.keys($scope.userDetails)) {
			$scope.userDetails[key] = undefined;
		}
		for (var key of Object.keys($scope.hardwareActivDetails)) {
			$scope.hardwareActivDetails[key] = undefined;
		}

		for (var key of Object.keys($scope.hardwareDetails)) {
			$scope.hardwareDetails[key] = undefined;
		}
	}

	$scope.resetView = function (){
		$scope.resetHardwareView();

		for (var key of Object.keys($scope.showConfig.user)) {
			$scope.showConfig.user[key] = false;
		}

		for (var key of Object.keys($scope.showConfig.view)) {
			$scope.showConfig.view[key] = false;
		}
		for (var key of Object.keys($scope.dialogAction)) {
			$scope.dialogAction[key] = {
				show : false,
				action : undefined,
				value : undefined
			};
		}
	};

	$scope.resetDialog = function(){
		for (var key of Object.keys($scope.showConfig.details)) {
			$scope.showConfig.details[key] = false;
		}
	}

	$scope.selectMenu = function (selectMenue) {

		this.resetView();

		switch (selectMenue) {
			case "showClients" :
				hardwareService.loadHardware($scope,'clients');
				$scope.showConfig.view.clientsHardware = true;
				break;
			case "showNetz" :
				hardwareService.loadHardware($scope,'sonstiges');
				$scope.showConfig.view.sonstigeHardware = true;
				break;
			case "showChangelog" :
				changelogService.loadChangelog($scope);
				$scope.showConfig.view.logo = true;
				$scope.showConfig.view.changelog = true;
				break;
			case "showLizenz" :
				lizenzService.loadLizenz($scope)
				$scope.showConfig.view.logo = true;
				$scope.showConfig.view.lizenz = true;
				break;
			case "showUser":
				debugger
				$scope.showConfig.view.logo = true;
				$scope.showConfig.view.user = true;
				break;
		}
	};

	this.logout = function(){
		$http({
			method: 'POST',
			url: 'logout'
		}).then(
			function successCallback(response) {
				window.location = "NetView/login"
			});
	}

	// default Funktion

	$scope.openTeamViewer = function(teamviewerid){
		var id = $scope.dialogAction.teamviewer.value;

		if (teamviewerid != undefined){
			id = teamviewerid;
		}

		window.open("teamviewer10://control?device="+id+"&authorization=password","_self");

	};

	$scope.openTelefon = function(telefon){
		if (telefon != undefined && telefon != '')
			window.open('tel:'+telefon,"_self");
	};

	$scope.formatDate = function(date) {
		var d = date.getDate();
		var m = date.getMonth() + 1; //Month from 0 to 11
		var y = date.getFullYear();
		return (d <= 9 ? '0' + d : d) + '.' + (m<=9 ? '0' + m : m) + '.' + y;
	};

	// Lizenz search

	$scope.dynamicLizenzList = viewService.getNewDynamicItems($scope.freeLizenz);

	$scope.searchMessageLizenzText = "";
	$scope.selectedLizenzList = [];
	$scope.selectedSearchLizenzList = [];

	$scope.insertSelectedLizenz = function(lizenz, selected){
		if (lizenz.id == undefined){
			for (var i=0;i<$scope.freeLizenz.length;i++){
				if ($scope.freeLizenz[i].name == lizenz) {
					if (checkLizenz($scope.selectedSearchLizenzList,$scope.freeLizenz[i])){
						$scope.selectedSearchLizenzList.push($scope.freeLizenz[i]);
						$scope.freeLizenz = removeLizenzFromList($scope.freeLizenz,$scope.freeLizenz[i]);
						break;
					}
				}
			}
		} else {
			if (selected){
				if (checkLizenz($scope.selectedLizenzList,lizenz)){
					$scope.selectedLizenzList.push(lizenz);
				}
			} else {
				$scope.selectedLizenzList = removeLizenzFromList($scope.selectedLizenzList,lizenz);
				$scope.freeLizenz.push(lizenz);
			}
		}
	};

	$scope.lizenzSelected = function (item) {
		return !$scope.selectedLizenzList.indexOf(item);
	};

	function removeLizenzFromList(lizenzList,lizenz) {
		var newList = [];
		for (var i=0;i<lizenzList.length;i++){
			if (lizenzList[i].id != lizenz.id) {
				newList.push(lizenzList[i])
			}
		}

		return newList;
	}

	function checkLizenz(lizenzList, lizenz){
		for (var a=0;a<lizenzList.length;a++){
			if (lizenz.id == lizenzList[a].id){
				return false;
			}
		}
		return true;
	}

	$scope.selectMessagLizenz = function(item){
		$scope.searchMessageLizenzText = item;
	};


	$scope.querySearchLizenz = function querySearch(query) {
		var result = [];
		var resultWithOutDuplicate = [];
		var lowercaseQuery = angular.lowercase(query);

		for (var i=0;i<$scope.freeLizenz.length;i++){
			var lowercaseSource = angular.lowercase($scope.freeLizenz[i].name);
			if ((lowercaseSource.indexOf(lowercaseQuery) > -1)){
				result.push($scope.freeLizenz[i])
			}
		}

		for (var i=0;i<result.length;i++){
			var insert = true;
			for (var a=0;a<resultWithOutDuplicate.length;a++){
				if (result[i].name == resultWithOutDuplicate[a].name){
					insert = false;
				}
			}
			if (insert){
				resultWithOutDuplicate.push(result[i]);
			}
		}


		return resultWithOutDuplicate;
	};

	$scope.openHardware = false;

	$scope.addHardwareLizenz = function(openHardware){
		var lizenzen = [];
		$scope.openHardware = openHardware;
		for (var a=0;a<$scope.selectedSearchLizenzList.length;a++) {
			lizenzen.push($scope.selectedSearchLizenzList[a].id)
		}

		for (var a=0;a<$scope.selectedLizenzList.length;a++) {
			if (!(lizenzen.indexOf($scope.selectedLizenzList[a]) >-1))
				lizenzen.push($scope.selectedLizenzList[a].id)
		}
		if (lizenzen.length > 0) {
			$http({
				method: 'POST',
				params: {
					'lid': 1,
					'hid': $scope.hardwareDetails.hardware.hid,
				},
				url: 'hardware/lizenz'
			}).then(function successCallback(response) {
				$scope.selectedSearchLizenzList = [];
				$scope.selectedLizenzList = [];

				$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document.querySelector('#popupContainer')))
					.clickOutsideToClose(true)
					.title('Lizenz')
					.textContent('Lizenz wurde erfolgreich hinzugefügt').ok('OK'));
				if ($scope.dialogAction.openHardware) {
					hardwareService.showHardware(response.config.params.hid, false, $scope);
					$scope.dialogAction.openHardware = false;
				}
			},function failCallback(response) {
				$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document.querySelector('#popupContainer')))
					.clickOutsideToClose(true)
					.title('Fehler')
					.textContent('Leider ist ein Fehler unterlaufen').ok('OK'));
			});
		}else{
			$mdDialog.show($mdDialog.alert()
				.parent(angular.element(document.querySelector('#popupContainer')))
				.clickOutsideToClose(true)
				.title('Fehlende Lizenz')
				.textContent('Es wurde keine Lizenz ausgewählt').ok('OK'));
		}
	};


	// User search

	$scope.searchMessageUserText = "";

	$scope.selectMessagUser = function(item){
		$scope.searchMessageUserValue = item;
	}

	$scope.querySearch = function querySearch(query) {
		return query ? $scope.ldapUser.filter(createFilterFor(query)) : $scope.ldapUser;
	};

	function createFilterFor(query) {
		var lowercaseQuery = angular.lowercase(query);
		return function filterFn(people) {
			return (people.uid.indexOf(lowercaseQuery) > -1);
		};
	}

});