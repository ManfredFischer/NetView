app.directive('messageLizenz', function() {
    return {
        restrict: 'E',
        templateUrl: 'static/dialog/ask/lizenz.html'
    };
}).directive('messageUser', function() {
    return {
        restrict: 'E',
        templateUrl: 'static/dialog/ask/user.html'
    };
}).directive('messageChangelog', function() {
    return {
        restrict: 'E',
        templateUrl : 'static/dialog/changelog.html'
    };
});