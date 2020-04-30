app.controller('netController', function ($scope) {

    $scope.hardwareNetzPages = [];
    this.getNetzHardware = function(){

        return $scope.hardwareNetzPages[$scope.clientPage];
    }
});