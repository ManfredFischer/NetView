app.service('hardwareService', function($http,data,initService) {
    this.initService = initService;

    this.loadHardware = function(scope, categorie){
        $http({
            method: 'GET',
            scope: scope,
            initService : initService,
            params : {
                "categorie" : categorie
            },
            url: 'hardware'
        }).then(function successCallback(response) {
            response.config.scope.hardware = [];
            response.data.forEach(function (data) {
                response.config.scope.hardware.push(data);
            });
            if (response.config.params.categorie === "sonstiges"){
                response.config.initService.finishTransaction("netzHardware",response);
            } else{
                response.config.initService.finishTransaction("clientsHardware",response);
            }

        });
    };
    this.filterHardware = function (scope,hardwareList, lager, aktivStatus) {
        var location = JSON.parse(scope.selectedLocation).lid;
        var result = []
        for (var i = 0; i < hardwareList.length; i++) {
            var insert = false;

            if (lager) {
                if (hardwareList[i].status == 0 || hardwareList[i].status == undefined) {
                    continue;
                }
            } else {
                if (hardwareList[i].status != undefined) {
                    if (hardwareList[i].status == 1) {
                        continue;
                    }
                }
            }

            if (location != "Standort..." && location != "" && location != undefined) {
                if (hardwareList[i].location.toString() == location) {
                    insert = true;
                }
            } else {
                insert = true;
            }

            if (aktivStatus != 'All' && insert) {
                if ((hardwareList[i].icon.toLowerCase().indexOf('green') != -1) && ($scope.clientsActivValue == 'Aktiv')) {
                    insert = true;
                } else if ((hardwareList[i].icon.toLowerCase().indexOf('red') != -1) && ($scope.clientsActivValue == 'Inaktiv')) {
                    insert = true;
                } else if ((hardwareList[i].icon.toLowerCase().indexOf('blanko') != -1) && ($scope.clientsActivValue == 'Unbekannt')) {
                    insert = true;
                } else {
                    insert = false;
                }
            }

            if (insert) {
                result.push(hardwareList[i]);
            }

        }

        return result;
    }
    this.searchHardwareByTextInput = function(hardwareList, searchValue){
        if (searchValue != '') {
            var result = [];
            for (var i = 0; i < hardwareList.length; i++) {

                for (var key in hardwareList[i]) {
                    if (hardwareList[i][key] != null && hardwareList[i][key].toString().toLowerCase().indexOf(searchValue) != -1) {
                        result.push(hardwareList[i]);
                        break;
                    }
                }

                if (hardwareList[i].verliehen) {
                    if ("verliehen".indexOf(searchValue) != -1) {
                        result.push(hardwareList[i]);
                    }
                }

                if ("freiburg".indexOf(searchValue) > -1) {
                    if (hardwareList[i].location != null && hardwareList[i].location == 1) {
                        result.push(hardwareList[i]);
                    }
                }

                if ("frankfurt".indexOf(searchValue) > -1) {
                    if (hardwareList[i].location != null && hardwareList[i].location == 2) {
                        result.push(hardwareList[i]);
                    }
                }

                if ("wien".indexOf(searchValue) > -1) {
                    if (hardwareList[i].location != null && hardwareList[i].location == 5) {
                        result.push(hardwareList[i]);
                    }
                }

                if ("hamburg".indexOf(searchValue) > -1) {
                    if (hardwareList[i].location != null && hardwareList[i].location == 7) {
                        result.push(hardwareList[i]);
                    }
                }

                if ("berlin".indexOf(searchValue) > -1) {
                    if (hardwareList[i].location != null && hardwareList[i].location == 3) {
                        result.push(hardwareList[i]);
                    }
                }

                if ("leipzig".indexOf(searchValue) > -1) {
                    if (hardwareList[i].location != null && hardwareList[i].location == 6) {
                        result.push(hardwareList[i]);
                    }
                }

                if ("oldenburg".indexOf(searchValue) > -1) {
                    if (hardwareList[i].location != null && hardwareList[i].location == 4) {
                        result.push(hardwareList[i]);
                    }
                }
            }


            return result;
        }else{
            return hardwareList;
        }
    }
    this.setHardwareViewPages = function(hardware, values,rows){
        if (values != undefined){

            if (hardware.length >= 11){
                var page = [];
                var pageAmount = rows;
                var pageSize=1;
                for (var i=0;i<hardware.length;i++){
                    if (pageAmount > 0){
                        page.push(hardware[i]);
                        pageAmount--;
                    }else{
                        values[pageSize] = page;
                        page = [];
                        pageSize++;
                        pageAmount = rows;
                        i=i-1;
                    }
                }

                if (Object.keys(page).length > 0)
                    values[pageSize] = page;

            }else{
                values[1] = hardware;
            }
        }
    }


    this.prevPage = function(page){
        if (1 < Object.keys(page).length && page != 1){
            page--;
            return page;
        }else {
            return page;
        }
    }
    this.nextPage = function(page){
        if (Object.keys(page).length > page){
            page++;
            return page;
        }else {
            return page;
        }
    }
});