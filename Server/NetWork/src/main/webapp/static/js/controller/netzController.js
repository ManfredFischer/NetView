app.controller('netzController', function ($scope, hardwareService) {
    $scope.hardware = [];
    hardwareService.loadHardware($scope,'sonstiges');
    $scope.hardwareNetzPages = [];

    this.getNetzHardware = function(){
        var lowerCaseSearchHost = $scope.searchHostServer.toLowerCase();
        var filterHardware = hardwareService.filterHardware($scope,$scope.hardware,false, $scope.clientsActivValue)

        filterHardware = hardwareService.searchHardwareByTextInput(filterHardware,lowerCaseSearchHost);

        if ($scope.clientsRows == 'All') {
            $scope.aktivClientsShow = Object.keys(filterHardware).length;
            return filterHardware;
        }

        $scope.hardwareNetzPages = [];
        hardwareService.setHardwareViewPages(filterHardware, $scope.hardwareNetzPages, $scope.clientsRows);
        $scope.clientMaxPage = Object.keys($scope.hardwareNetzPages).length;
        $scope.aktivClientsShow = Object.keys($scope.hardwareNetzPages[$scope.clientPage]).length;
        return $scope.hardwareNetzPages[$scope.clientPage];
    }
});