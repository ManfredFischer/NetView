app.directive('addSoftware', function() {
    return {
        templateUrl : 'static/dialog/software.html'
    };
}).directive('addLizenzen', function() {
    return {
        templateUrl : 'static/dialog/lizenzen.html'
    };
}).directive('addChangelog', function() {
    return {
        templateUrl : 'static/dialog/changelog.html'
    };
}).directive('showChangelog', function() {
    return {
        templateUrl : 'static/dialog/changelogView.html'
    };
}).directive('showHardware', function() {
    return {
        templateUrl : 'static/dialog/hardwareView.html'
    };
}).directive('addHandyuser', function() {
    return {
        templateUrl : 'static/dialog/addHandyUser.html'
    };
}).directive('myUpload', function() {
    return {
        templateUrl : 'static/dialog/Import.html'
    };
});