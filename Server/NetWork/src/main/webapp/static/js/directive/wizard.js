app.directive('wizardAdduserinformation', function() {
    return {
        templateUrl : 'static/html/user/wizard/DefaultInformationUser.html'
    };
}).directive('wizardAdduserhardwareinformation', function() {
    return {
        templateUrl : 'static/html/user/wizard/DefaultHardwareInformationUser.html'
    };
}).directive('wizardAdduseroverview', function() {
    return {
        templateUrl : 'static/html/user/wizard/DefaultOverviewUser.html'
    };
}).directive('wizardAdduserpermission', function() {
    return {
        templateUrl : 'static/html/user/wizard/DefaultPermissionUser.html'
    };
});