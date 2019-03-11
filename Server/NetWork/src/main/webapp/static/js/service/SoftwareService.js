app.service('softwareService', function($http) {
	
	this.scopeMain;
	this.mdDialog;
	this.software;
	this.searchSoftwareValue;
	this.softwareList;
	this.softwareResult = [];
	
	this.setSoftwareValue = function(){
		this.software = {
				displayname : '',
				installcontent : '',
				description : '',
				defaultsoftware : true,
				distribute : true,
				removebydeleting : true,
				msi : false
		}
	}
	
	this.setSoftwareValue();
	
	this.addSoftware = function(){
		this.scopeMain.titleDialog = "Software";
		this.scopeMain.titleImg = "/NetView/static/img/AddHardware.png";
		this.scopeMain.actionDialog = this.postSoftware;
		this.scopeMain.dialogSoftware = true;
		this.scopeMain.dialogHardware = false;
		this.mdDialog.show({
			scope: this.scopeMain.$new(),
			templateUrl: 'static/dialog/AbstractDialog.html',
			clickOutsideToClose: true,
			fullscreen: this.scopeMain.customFullscreen
		});
	}
	
	this.postSoftware = function(){
		debugger;
		$http({
			method: 'POST',
			scope: this.scopeMain,
			url: 'software/distribute',
			data : this.softwareService.software,
		}).then(function successCallback(response) {
			this.setSoftwareValue();
		});
	}
	
	this.putSoftware = function(){
		$http({
			method: 'PUT',
			scope: this.scopeMain,
			url: 'software/distribute',
			data : this.software,
		}).then(function successCallback(response) {
			this.setSoftwareValue();
		});
	}
	
	this.getSoftware = function(){
		
		if (this.searchSoftwareValue == undefined || this.searchSoftwareValue == ''){
			return this.softwareResult;	
		}else{
			var result = [];
			for (var i=0;i<this.softwareResult.length;i++){
				if (this.softwareResult[i].displayname.toLowerCase().indexOf(this.searchSoftwareValue.toLowerCase()) != -1){
					result.push(this.softwareResult[i])
				}
			}
			
			return result;
		}
		
	}
	
	
	
});