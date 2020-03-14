app.controller('clientsController', function ($scope, hardwareService) {
    $scope.hardware = [];
    $scope.clientsLager = false;
    hardwareService.loadHardware($scope,'clients');
    $scope.hardwareClientsPages = [];

    $scope.stateListClients = hardwareService.stateList;
    $scope.clientsState = {
        name : 'All',
        value : 0
    };

    this.getClientsHardware = function(){
        var lowerCaseSearchHost = $scope.searchHostClient.toLowerCase();
        var filterHardware = hardwareService.filterHardware($scope,$scope.hardware,$scope.clientsLager, $scope.clientsActivValue)

        filterHardware = hardwareService.searchHardwareByTextInput(filterHardware,lowerCaseSearchHost);

        if ($scope.clientsRows == 'All') {
            $scope.aktivClientsShow = Object.keys(filterHardware).length;
            return filterHardware;
        }

        $scope.hardwareClientsPages = [];
        hardwareService.setHardwareViewPages(filterHardware, $scope.hardwareClientsPages, $scope.clientsRows);
        $scope.clientMaxPage = Object.keys($scope.hardwareClientsPages).length;
        $scope.aktivClientsShow = Object.keys($scope.hardwareClientsPages[$scope.clientPage]).length;
        return $scope.hardwareClientsPages[$scope.clientPage];
    }
});