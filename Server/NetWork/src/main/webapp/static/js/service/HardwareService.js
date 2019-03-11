app.service('hardwareService', function($http) {
	
	this.mainScope;
	
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
	 
	    
	 
	 	if (lowerCaseSearchHost != ''){					
		 filterHardware = this.filterByUser(filterHardware, lowerCaseSearchHost)
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
	
	this.filter = function(){
		
	}
	
	this.filterByUser = function(hardware, valueFilter){
		var result = new Array();
		for (var i=0;i<hardware.length;i++){
			if (hardware[i].hostname != null && filterHardware[i].hostname.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			} else if (hardware[i].owner != null && hardware[i].owner.toLowerCase().indexOf(valueFilter) != -1){
				result.push(filterHardware[i]);
			} else if (hardware[i].aktivUsername != null && hardware[i].aktivUsername.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			} else if (hardware[i].sn != null && hardware[i].sn.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			} else if (hardware[i].model != null && hardware[i].model.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			} else if (hardware[i].ram != null && hardware[i].ram.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			} else if (hardware[i].categorie != null && hardware[i].categorie.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			} else if (hardware[i].description != null && hardware[i].description.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			} else if (hardware[i].department != null && hardware[i].department.toLowerCase().indexOf(valueFilter) != -1){
				result.push(hardware[i]);
			}
			
			
			   if ("freiburg".indexOf(valueFilter) > -1) {
					  if (hardware[i].location != null && hardware[i].location == 1){
							result.push(hardware[i]);
					} 
			   }
			   
			   if ("frankfurt".indexOf(valueFilter) > -1) {
					  if (hardware[i].location != null && hardware[i].location == 2){
							result.push(hardware[i]);
					} 
			   }
			   
			   if ("wien".indexOf(valueFilter) > -1) {
					  if (hardware[i].location != null && hardware[i].location == 5){
							result.push(hardware[i]);
					} 
			   }
			   
			   if ("hamburg".indexOf(valueFilter) > -1) {
					  if (hardware[i].location != null && hardware[i].location == 7){
							result.push(hardware[i]);
					} 
			   }
			   
			   if ("berlin".indexOf(valueFilter) > -1) {
					  if (hardware[i].location != null && hardware[i].location == 3){
							result.push(hardware[i]);
					} 
			   }
			   
			   if ("leipzig".indexOf(valueFilter) > -1) {
					  if (hardware[i].location != null && hardware[i].location == 6){
							result.push(hardware[i]);
					} 
			   }
			   
			   if ("olenburg".indexOf(valueFilter) > -1) {
					  if (hardware[i].location != null && hardware[i].location == 4){
							result.push(hardware[i]);
					} 
			   }
			
			
			if (hardware[i].location != null && hardware[i].location == location){
				result.push(hardware[i]);
			} 
		}
		
	 return result;
	}
	
	
	this.showHardwareInformation = function (hid) {
		
		this.mainScope.showHardwareViewOwner = false;
		this.mainScope.showHardwareViewHardware = false;
		this.mainScope.showHardwareViewLizenz = false;
		this.mainScope.showHardwareViewHardwareList = false;
		this.mainScope.showHardwareViewUser = false;
		
		$http({
			method: 'GET',
			scope: $scope,
			sync: true,
			url: 'hardware/'+hid
		}).then(function successCallback(response) {
			this.mainScope.data.loadLizenz(this.mainScope,"0");
			$scope.hardwareInformation = response.data;
			
			$scope.showHardwareViewHardware = true;
			$scope.showHardwareViewLizenz = true;
			
			
			if ($scope.hardwareInformation.ownerInformation != undefined && $scope.hardwareInformation.ownerInformation.displayName != undefined){
				$scope.showHardwareViewOwner = true;
				$scope.hardwareOwner = $scope.hardwareInformation.ownerInformation;
				
			}
			
			if ($scope.hardwareInformation.inUseInformation != undefined && $scope.hardwareInformation.inUseInformation.displayName != undefined){
				$scope.showHardwareViewUser = true;
				$scope.hardwareUser = $scope.hardwareInformation.inUseInformation;
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

});