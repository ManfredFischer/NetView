app.service('data', function($http,initService) {
	this.initService = initService;
	this.clientsHardware = {
		lastUpdate : '',
		result : []
	};

    this.loadLocation = function ($scope) {
    	$http({
			method: 'GET',
			scope: $scope,
			this : this,
			url: 'location'
		}).then(function successCallback(response) {
			response.data.forEach(function (data) {
				response.config.scope.LocationList.push(data);
			});
			response.config.this.initService.finishTransaction("location",response);
		 }
		);
    };
    this.loadUser = function ($scope) {
		$http({
			method: 'GET',
			scope: $scope,
			this : this,
			url: 'ldap/user'
		}).then(
			function successCallback(response) {
				response.data.forEach(function (data) {
					if (data.lid != -1) {
						$scope.selectedItem = data.lid;
					} else {
						data.lid = 1;
					}

					response.config.scope.people.push(data);

				});
				response.config.this.initService.finishTransaction("users",response);
			});
    };

	this.loadChangelog = function ($scope) {
		$http({
			method: 'GET',
			scope: $scope,
			this : this,
			url: 'changelog'
		}).then(
			function successCallback(response) {
				response.data.forEach(function (data) {
					var dateInfo = new Date(data.date);
					data.date = dateInfo.getDay() +"."+dateInfo.getMonth()+"."+dateInfo.getFullYear()+" "+dateInfo.getHours()+":"+dateInfo.getSeconds();
					response.config.scope.changelogList.push(data);
				});
				response.config.this.initService.finishTransaction("changelog",response);
			});
	}
    this.loadLizenz = function($scope, $state){
		$http({
			method: 'GET',
			scope: $scope,
			this : this,
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
				response.config.this.initService.finishTransaction("lizenz",response);
			}
		 );
	}

	this.loadHardware = function($scope, categorie){
		if ($scope == undefined){
			$http({
				method: 'GET',
				scope : this,
				sync: false,
				params : {
					"categorie" : categorie
				},
				url: 'hardware'
			}).then(function successCallback(response) {
				response.data.forEach(function (data) {
					response.config.scope.clientsHardware.result.push(data);
				});
			});
		}else {
			if (this.clientsHardware.result.length != 0) {
				$scope.hardware = this.clientsHardware.result;
			} else {
				$http({
					method: 'GET',
					scope: $scope,
					params: {
						"categorie": categorie
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
		}
	};

	this.loadClientsHardware = function($scope){
		$http({
			method: 'GET',
			scope: $scope,
			this : this,
			params : {
				"categorie" : "clients"
			},
			url: 'hardware'
		}).then(function successCallback(response) {
			response.config.scope.hardware = [];
			response.data.forEach(function (data) {
				response.config.scope.hardware.push(data);
			});

			response.config.this.initService.finishTransaction("clientsHardware",response);
		});
	};

	this.loadNetzHardware = function($scope){
		$http({
			method: 'GET',
			scope: $scope,
			this : this,
			params : {
				"categorie" : "sonstiges"
			},
			url: 'hardware'
		}).then(function successCallback(response) {
			response.config.scope.hardware = [];
			response.data.forEach(function (data) {
				response.config.scope.hardware.push(data);
			});

			response.config.this.initService.finishTransaction("netzHardware",response);
		});
	};
	
});