app.directive('settingsData', function() {
    return {
        templateUrl : 'static/html/settings/settingsData.html'
    };
}).directive('settingsConverter', function() {
    return {
        templateUrl : 'static/html/settings/settingsConverter.html'
    };
}).directive('settingsUsers', function() {
    return {
        templateUrl : 'static/html/settings/user/userView.html'
    };
}).directive('mySettingsdatacontractinput', function() {
    return {
        templateUrl : 'static/html/settings/data/contractInput.html'
    };
}).directive('mySettingsdatahandyinput', function() {
    return {
        templateUrl : 'static/html/settings/data/handyInput.html'
    };
});