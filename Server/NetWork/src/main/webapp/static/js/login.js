angular.module('BlankApp', ['ngMaterial'])
    .controller('DemoBasicCtrl', function($scope, $mdDialog,$http) {
      $scope.change = function(name, ev) {
            $mdDialog.show($mdDialog.alert()
                .title(name)
                .textContent('You triggered the "' + name + '" action')
                .ok('Great')
                .targetEvent(ev)
            );
        };
    });