// @ts-ignore
angular.module('MainPage', ['ngMaterial'])
	.controller(
		'ListCtrl',
		function ($scope, $mdDialog, $http, $timeout, $q) {
			$scope.people = [];
			$scope.hardware = [];
			$scope.selectedUser = "";
			$scope.searchUpdateName = '';
			$scope.storeUserInformation = {};
			$scope.selectedHardware;

			$scope.hardwareInformationOwner = {};
			$scope.hardwareInformation = {};
			$scope.userInformation = {};
			$scope.userInformationAktiv = {};

			$scope.showHardwareOwnerDetails = false;
			$scope.showHardwareDetails = false;
			$scope.showUserDetails = false;
			$scope.hardwareActiv = false;
			$scope.showEnabledUserDetails = false;
			$scope.titleUserAktiv = "Besitzer";
			$scope.titleUserOwner = "aktiver User";
			$scope.titleHardwareOwner = "Hardware";
			$scope.titleHardware =  "aktive Hardware";

			$scope.config = {
				create: false,
				userdetails: true
			};
			$scope.LocationList = [];
			var self = this;


			$scope.findUser = function (user) {
				return $scope.findUserByName(user);
			}

			$scope.findUserByName = function (user) {
				return user.displayName.toLowerCase() == $scope.selectedUser.toLowerCase()
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
				sync: false,
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

					self.stateUser = loadAllUser($scope);

				},
				// @ts-ignore
				function errorCallback(response) {
					$mdDialog.show($mdDialog.alert()
						.title('Fehler').textContent(
							'Fehler beim Laden der Daten')
						.ariaLabel('Error').ok('OK')
						.targetEvent(event));
				});

			// @ts-ignore
			self.selectUpdateUser = function (name, ev) {
				$scope.config.userdetails = false;
				$scope.selectedUser = $scope.ctrl.searchUserText
				$scope.userInformation = $scope.people.find($scope.findUser);
			};

			$scope.changeUserUpdateInsert = function () {
				debugger;
				if ($scope.config.create) {
					$scope.storeUserInformation = $scope.userInformation;
					$scope.clearUserInformaiton();
				} else {
					$scope.config.userdetails = false;
					$scope.userInformation = $scope.storeUserInformation;
				}
			}

			$scope.showHardwareInformation = function (hardware) {
				$scope.titleHardware = "Hardware";
				$scope.hardwareInformation = hardware;
				$scope.showHardwareOwnerDetails = false;
				$scope.showHardwareDetails = true;
				$scope.hardwareActiv = false;

				if ($scope.hardwareInformation.aktivUsername != "" && $scope.hardwareInformation.aktivUsername != undefined) {
					var aktivUsername = $scope.hardwareInformation.aktivUsername.split(".");
					if (aktivUsername.length > 1) {
						$scope.selectedUser = aktivUsername[0] + " " + aktivUsername[1];
					} else {
						$scope.selectedUser = aktivUsername[0];
					}
					$scope.userInformationAktiv = $scope.people.find($scope.findUser);
					$scope.hardwareActiv = true;
				}

				if ($scope.hardwareInformation.owner != "" && $scope.hardwareInformation.owner != undefined) {
					var user = $scope.hardwareInformation.owner.split(".");
					if (user.length > 1) {
						$scope.selectedUser = user[0] + " " + user[1];
					} else {
						$scope.selectedUser = user[0];
					}
					$scope.userInformation = $scope.people.find($scope.findUser);
				} 

				$scope.showUserDetails = false;
				$scope.showEnabledUserDetails = false;

				if (($scope.userInformation != undefined && $scope.userInformation.displayName != undefined) && ($scope.userInformationAktiv != undefined && $scope.userInformationAktiv.displayName != undefined)){
			     if ($scope.userInformation.displayName == $scope.userInformationAktiv.displayName){
					$scope.titleUserAktiv = "User";
				 } else{
					$scope.showUserDetails = true;
					$scope.titleUserOwner = "Besitzer";
					$scope.titleUserAktiv = "Aktiver User";
				 }

					$scope.showEnabledUserDetails = true;
				} else if ($scope.userInformation != undefined  && $scope.userInformation.displayName != undefined){
			     	$scope.showUserDetails = true;
					$scope.titleUserOwner = "Besitzer";
				} else if ($scope.userInformationAktiv != undefined  && $scope.userInformationAktiv.displayName != undefined){
			     	$scope.showEnabledUserDetails = true;
					$scope.titleUserAktiv = "Aktiver User";
				}


				$mdDialog.show({
						scope: $scope.$new(),
						templateUrl: 'static/dialog/hardware.html',
						clickOutsideToClose: true,
						fullscreen: $scope.customFullscreen
					})
					.then(function () {

					}, function () {

					});
			};

			$scope.showUserInformation = function () {
				

				$scope.titleUserOwner = "User";
				$scope.showUserDetails = true;

				$scope.hardwareInformationOwner =  $scope.hardware.find($scope.findHardwareByOwner);
				$scope.hardwareInformation =  $scope.hardware.find($scope.findHardwareByAktivUsername);
				
				$scope.showHardwareOwnerDetails = false;
				$scope.showHardwareDetails = false;
				$scope.hardwareActiv = false;
				
				if (($scope.hardwareInformationOwner != undefined && $scope.hardwareInformationOwner.hostname != undefined) && ($scope.hardwareInformation != undefined && $scope.hardwareInformation.hostname != undefined)){
			     if ($scope.hardwareInformation.hostname == $scope.hardwareInformationOwner.hostname){
					$scope.titleHardwareOwner = "Hardware";
				 } else{
					$scope.titleHardwareOwner = "aktive Hardware";
					$scope.showHardwareDetails = true;
				 }
				 $scope.hardwareActiv = true;	
				 $scope.showHardwareOwnerDetails = true;
				} else if ($scope.hardwareInformationOwner != undefined  && $scope.hardwareInformationOwner.hostname != undefined){
					 $scope.titleHardwareOwner = "Hardware";
					 $scope.showHardwareOwnerDetails = true;
				} else if ($scope.hardwareInformation != undefined  && $scope.hardwareInformation.hostname != undefined){
					$scope.hardwareActiv = true;
					$scope.titleHardware = "aktive Hardware";
			     	$scope.showHardwareDetails = true;
				}

				$mdDialog.show({
						scope: $scope.$new(),
						templateUrl: 'static/dialog/hardware.html',
						clickOutsideToClose: true,
						fullscreen: $scope.customFullscreen
					})
					.then(function () {

					}, function () {

					});
			};

			$http({
				method: 'GET',
				scope: $scope,
				sync: false,
				url: 'hardware/all'
			}).then(function successCallback(response) {
					response.data.forEach(function (data) {
						response.config.scope.hardware.push(data);
						self.stateHardware = loadAllHardware($scope);
					});
				},
				// @ts-ignore
				function errorCallback(response) {
					$mdDialog.show($mdDialog.alert()
						.title('Fehler').textContent(
							'Fehler beim Laden der Daten')
						.ariaLabel('Error').ok('OK')
						.targetEvent(event));
				});

			self.updateUserInformation = function () {
				var httpRequest = $http.put("ldap/user",
					$scope.user, {
						'Content-Type': 'application/json'
					}).then($scope.httpRequestSuccess,
					$scope.httpRequestError);
				$scope.sendInformation(httpRequest);
			}

			self.createUserInformation = function () {
				var httpRequest = $http.post("ldap/user", $scope.user, {
					'Content-Type': 'application/json'
				}).then($scope.httpRequestSuccess,
					$scope.httpRequestError);
				$scope.sendInformation(httpRequest);
			}

			self.createUserInformation = function () {
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




			self.querySearch = querySearch;

			$scope.resetInformation = function () {
				$scope.user = {};
			}

			function querySearch(query) {
				var results = query ? self.stateUser.filter(createFilterFor(query)) : self.stateUser,
					deferred;
				if (self.simulateQuery) {
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
			function queryHardwareSearch(query) {
				var results = query ? self.stateHardware.filter(createHardwareFilterFor(query)) : self.stateHardware,
					deferred;
				if (self.simulateQuery) {
					deferred = $q.defer();
					$timeout(function () {
						deferred.resolve(results);
					}, Math.random() * 1000, false);
					return deferred.promise;
				} else {
					return results;
				}
			}

			function createHardwareFilterFor(query) {
				// @ts-ignore
				var lowercaseQuery = angular.lowercase(query);
				return function filterFn(stateHardware) {
					return (stateHardware.value.indexOf(lowercaseQuery) > -1);
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

			// @ts-ignore
			function loadAllHardware(scope) {
				return $scope.hardware.map(function (hardware) {
					return {
						value: hardware.hostname.toLowerCase(),
						display: hardware.hostname
					};
				});
			}

		});