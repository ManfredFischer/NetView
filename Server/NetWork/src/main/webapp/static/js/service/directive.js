app.directive('myLizenz', function() {
	return {
		templateUrl : 'static/html/lizenz.html'
	};
}).directive('myNetz', function() {
	return {
		templateUrl : 'static/html/netz.html'
	};
}).directive('myHardware', function() {
	return {
		templateUrl : 'static/html/hardware.html'
	};
}).directive('myUserdetails', function() {
	return {
		templateUrl : 'static/html/UserDetails.html'
	};
}).directive('myMenue', function() {
	return {
		templateUrl : 'static/html/menue.html'
	};
}).directive('myShurtcurt', function() {
	return {
		templateUrl : 'static/html/shurtcurt.html'
	};
}).directive('myMobile', function() {
	return {
		templateUrl : 'static/html/mobile.html'
	};
}).directive('mySoftware', function() {
	return {
		templateUrl : 'static/html/software.html'
	};
}).directive('addHardware', function() {
	return {
		templateUrl : 'static/dialog/hardware.html'
	};
}).directive('addSoftware', function() {
	return {
		templateUrl : 'static/dialog/software.html'
	};
}).directive('addLizenzen', function() {
	return {
		templateUrl : 'static/dialog/lizenzen.html'
	};
}).directive('showHardware', function() {
	return {
		templateUrl : 'static/dialog/hardwareView.html'
	};
});