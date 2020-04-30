app.directive('hardwareComponent', function() {
    return {
        restrict: 'E',
        scope: {
            hardwareInformation: '=data'
        },
        templateUrl: 'static/dialog/component/hardware.html'
    };
}).directive('userComponent', function() {
    return {
        restrict: 'E',
        scope: {
            userInformation: '=data'
        },
        templateUrl: 'static/dialog/component/user.html'
    };
}).directive('clientshardwareComponent', function() {
    return {
        templateUrl: 'static/html/views/component/clientsHardware.html'
    };
}).directive('sonstigehardwareComponent', function() {
    return {
        templateUrl: 'static/html/views/component/sonstigeHardware.html'
    };
});