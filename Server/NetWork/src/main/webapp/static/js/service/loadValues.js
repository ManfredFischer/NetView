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
					response.config.scope.lizenzen.push(data);
				});
				
				$scope.setLizenzViewPages(response.config.scope.lizenzen);
			}
		 );
	}
    
    this.loadDepartment = function($scope){
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
			}
		);
	}
    
    this.loadHardware = function($scope, categorie){
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
    
	
});