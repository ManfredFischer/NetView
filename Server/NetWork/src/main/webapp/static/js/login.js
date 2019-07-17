// @ts-ignore
angular.module('BlankApp', ['ngMaterial','ngFileUpload'])
    .controller('DemoBasicCtrl', function($scope, $mdDialog, $http) {
      $scope.showVeranstalter = false;
      $scope.showChangePassword = true;  
      $scope.url = "";
      $scope.name = "";
      $scope.value = "";
      $scope.titleButton = "Veranstalter";
      
      this.changeView = function(){
    	  if ($scope.showVeranstalter){
    		  $scope.titleButton = "Veranstalter";
    		  $scope.showVeranstalter = false;
    	      $scope.showChangePassword = true; 
    	  }else{
    		  $scope.titleButton = "Change Password";
    		  $scope.showVeranstalter = true;
    	      $scope.showChangePassword = false; 
    	  }
      }
      
      
      $scope.send = function(url, name, value, files){
    		if (files != undefined){
    			
    			var names = files[0];
    			
    			for (var i = 1; i <= files.length; i++) {
    				if (files[i] != undefined){
    				  for (var a = 1; a <= files[i].length; a++) {
    					if (names[a] != undefined){
    					 if (names[a].toLowerCase().indexOf("ignore") == -1){
    					  var result = {};
    					  result[names[0]] = files[i][0];
    					  result[names[a]] = files[i][a];
        				  console.log(result);
        				  $http({
        					    method: 'POST',
        					    url: url,
        					    data: result,
        					    headers: {'Content-Type': 'text/plain; charset=utf-8',"statusText":"","xhrStatus":"error"}
        					})
        				  
    					 }
    					}
    				  }
    				}
    				
    				
    	  		}
    			
    	  			
			 
    	  	}
		}
      
    }).directive('changePassword', function() {
    	return {
    		templateUrl : 'static/html/changePassword.html'
    	};
    }).directive('showVeranstalter', function() {
    	return {
    		templateUrl : 'static/html/veranstalter.html'
    	};
    }).directive('fileReader', function() {
    	  return {
    		    scope: {
    		      fileReader:"="
    		    },
    		    link: function(scope, element) {
    		      $(element).on('change', function(changeEvent) {
    		    	  scope.fileReader = [];
    		    	  var files = changeEvent.target.files;
    		    	  var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.csv|.txt)$/;
    		            if (regex.test(files[0].name.toLowerCase())) {
    		                if (typeof (FileReader) != "undefined") {
    		                    var reader = new FileReader();
    		                    reader.onload = function (e) {
    		                        var customers = new Array();
    		                        var rows = e.target.result.split("\r\n");
    		                        for (var i = 0; i < rows.length; i++) {
    		                            var cells = rows[i].split(",");
    		                            if (cells.length > 1) {
    		                                scope.fileReader.push(cells);
    		                            }
    		                        }
    		 
    		                    }
    		                    reader.readAsText(files[0]);
    		                    scope.sendInformation = true;
    		                } else {
    		                    $window.alert("This browser does not support HTML5.");
    		                }
    		            } else {
    		                $window.alert("Please upload a valid CSV file.");
    		            }  
    		      });
    		    }
    		  };
    });