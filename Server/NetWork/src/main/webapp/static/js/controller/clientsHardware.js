// @ts-ignore
app.controller('mainClients', function ($scope, $mdDialog,$timeout, $http,Upload, data, userService, hardwareService) {
    debugger;

    $scope.hardware = [];
    $scope.hardwareClientsPages;
    $scope.clientsRows;
    $scope.clientPage;
    $scope.aktivClientsShow;
    $scope.aktivClientsShow;
    $scope.hardwareClientsPages = [];

    data.loadClients($scope.hardware,'clients');

    this.getClientsHardware = function(){
            debugger;
            var location = JSON.parse($scope.selectedLocation).lid;
            var lowerCaseSearchHost = $scope.searchHostClient.toLowerCase();;
            var filterHardware = [];

            var searchValueAktuell = $scope.selectedLocation+';'+lowerCaseSearchHost+';';
            if ($scope.netzwerkSearchParameter.result != undefined){
                if ($scope.netzwerkSearchParameter.searchValue == searchValueAktuell){
                    return  $scope.netzwerkSearchParameter.result;
                }
            }

            for (var i = 0; i < $scope.hardware.length; i++) {
                if ($scope.hardwareLager) {
                    if ($scope.hardware[i].status == 0 || $scope.hardware[i].status == undefined) {
                        continue;
                    }
                } else {
                    if ($scope.hardware[i].status != undefined) {
                        if ($scope.hardware[i].status == 1) {
                            continue;
                        }
                    }
                }

                var insert = false;
                if ($scope.hardware[i].categorie != undefined) {
                    if (location != "Standort..." && location != "" && location != undefined) {
                        if ($scope.hardware[i].location.toString() == location) {
                            insert = true;
                        }
                    } else {
                        insert = true;
                    }

                    if ($scope.clientsActivValue != 'All' && insert) {
                        if (($scope.hardware[i].icon.toLowerCase().indexOf('green') != -1) && ($scope.clientsActivValue == 'Aktiv')) {
                            insert = true;
                        } else if (($scope.hardware[i].icon.toLowerCase().indexOf('red') != -1) && ($scope.clientsActivValue == 'Inaktiv')) {
                            insert = true;
                        } else if (($scope.hardware[i].icon.toLowerCase().indexOf('blanko') != -1) && ($scope.clientsActivValue == 'Unbekannt')) {
                            insert = true;
                        } else {
                            insert = false;
                        }
                    }

                    if (insert) {
                        filterHardware.push($scope.hardware[i]);
                    }
                }
            }


            if (lowerCaseSearchHost != '') {
                var result = new Array();
                for (var i = 0; i < filterHardware.length; i++) {

                    for (var key in filterHardware[i]) {
                        if (filterHardware[i][key] != null && filterHardware[i][key].toString().toLowerCase().indexOf(lowerCaseSearchHost) != -1) {
                            result.push(filterHardware[i]);
                            break;
                        }
                    }

                    if (filterHardware[i].verliehen) {
                        if ("verliehen".indexOf(lowerCaseSearchHost) != -1) {
                            result.push(filterHardware[i]);
                        }
                    }

                    if ("freiburg".indexOf(lowerCaseSearchHost) > -1) {
                        if (filterHardware[i].location != null && filterHardware[i].location == 1) {
                            result.push(filterHardware[i]);
                        }
                    }

                    if ("frankfurt".indexOf(lowerCaseSearchHost) > -1) {
                        if (filterHardware[i].location != null && filterHardware[i].location == 2) {
                            result.push(filterHardware[i]);
                        }
                    }

                    if ("wien".indexOf(lowerCaseSearchHost) > -1) {
                        if (filterHardware[i].location != null && filterHardware[i].location == 5) {
                            result.push(filterHardware[i]);
                        }
                    }

                    if ("hamburg".indexOf(lowerCaseSearchHost) > -1) {
                        if (filterHardware[i].location != null && filterHardware[i].location == 7) {
                            result.push(filterHardware[i]);
                        }
                    }

                    if ("berlin".indexOf(lowerCaseSearchHost) > -1) {
                        if (filterHardware[i].location != null && filterHardware[i].location == 3) {
                            result.push(filterHardware[i]);
                        }
                    }

                    if ("leipzig".indexOf(lowerCaseSearchHost) > -1) {
                        if (filterHardware[i].location != null && filterHardware[i].location == 6) {
                            result.push(filterHardware[i]);
                        }
                    }

                    if ("oldenburg".indexOf(lowerCaseSearchHost) > -1) {
                        if (filterHardware[i].location != null && filterHardware[i].location == 4) {
                            result.push(filterHardware[i]);
                        }
                    }
                }


                filterHardware = result;
            }


            if ($scope.clientsRows == 'All') {
                $scope.aktivClientsShow = Object.keys(filterHardware).length;
                return filterHardware;
            }
            $scope.hardwareClientsPages = [];
            $scope.setHardwareViewPages(filterHardware, $scope.hardwareClientsPages, $scope.clientsRows);
            $scope.clientMaxPage = Object.keys($scope.hardwareClientsPages).length;
            $scope.aktivClientsShow = Object.keys($scope.hardwareClientsPages[$scope.clientPage]).length;
            return $scope.hardwareClientsPages[$scope.clientPage];

    }
});