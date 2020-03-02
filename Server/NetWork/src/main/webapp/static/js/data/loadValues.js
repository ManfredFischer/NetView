app.service('data', function($http) {
    this.loadLocation = function ($scope) {
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
    }
    this.loadUser = function ($scope) {
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
			});
    }
    
    this.loadContract = function ($scope) {
		$http({
			method: 'GET',
			scope: $scope,
			sync: false,
			url: 'contract'
		}).then(
			function successCallback(response) {
				response.data.forEach(function (data) {
				  response.config.scope.contractList.push(data);
				});
			});
    }
    
    this.loadHandyModel = function ($scope) {
		$http({
			method: 'GET',
			scope: $scope,
			sync: false,
			url: 'handyModel'
		}).then(
			function successCallback(response) {
				response.data.forEach(function (data) {
					response.config.scope.handyModelList.push(data);
				});
			});
    }

	this.loadMobileUser = function ($scope) {
		$http({
			method: 'GET',
			scope: $scope,
			sync: false,
			url: 'mobileUser'
		}).then(
			function successCallback(response) {
				response.data.forEach(function (data) {
					response.config.scope.mobileUserList.push(data);
				});
			});
	};

	this.loadChangelog = function ($scope) {
		$http({
			method: 'GET',
			scope: $scope,
			sync: false,
			url: 'changelog'
		}).then(
			function successCallback(response) {
				response.data.forEach(function (data) {
					var dateInfo = new Date(data.date);
					data.date = dateInfo.getDay() +"."+dateInfo.getMonth()+"."+dateInfo.getFullYear()+" "+dateInfo.getHours()+":"+dateInfo.getSeconds();
					response.config.scope.changelogList.push(data);
				});
			});
	}
    
    this.loadLizenz = function($scope, $state){
		$http({
			method: 'GET',
			scope: $scope,
			params : {
				"state" : $state
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
					} else if (data.state == 3){
						data.stateInfo = "reserviert";
					}

					var reserved = data.reserved == null ? 0 : data.reserved.split(",").length;
					data.allowamount = data.allowamount == undefined ? 1 : data.allowamount;
					var verfuegbar = data.allowamount - (reserved + data.hardware.length);
					data.lizenzState = reserved+"/"+data.hardware.length+"/"+verfuegbar;
					data.vergeben = reserved + data.hardware.length;
					data.verfuegbar = verfuegbar;
					response.config.scope.lizenzen.push(data);
				});
				
				$scope.setLizenzViewPages(response.config.scope.lizenzen);
			}
		 );
	}
    
    this.loadDepartment = function($scope){
    	/*$http({
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
			}
		);*/
	}
    
    this.loadHardware = function($scope, categorie){
		$http({
			method: 'GET',
			scope: $scope,
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
	};

	this.loadClients = function($hardware, categorie){
		$http({
			method: 'GET',
			hardware: $hardware,
			params : {
				"categorie" : categorie
			},
			url: 'hardware'
		}).then(function successCallback(response) {
			response.config.hardware = [];
			response.data.forEach(function (data) {
				response.config.hardware.push(data);
			});
		});
	};
    
    this.loadMobile = function($scope){
		$http({
			method: 'GET',
			scope: $scope,
			url: 'mobile'
		}).then(function successCallback(response) {
				response.data.forEach(function (data) {
					response.config.scope.mobile.push(data);
				});
		});
	}
    
	
});