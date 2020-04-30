app.service('lizenzService', function($http) {

    this.addLizenz = function(){
        $http({
            method: 'POST',
            scope: $scope,
            url: 'lizenz',
            data : $scope.lizenz,
        }).then(function successCallback(response) {
            $scope.lizenz = {
                name : '',
                key : '',
                categorie : ''
            }
        });
    };

    this.updateLizenz = function(){
        $http({
            method: 'PUT',
            scope: $scope,
            url: 'lizenz',
            data : $scope.lizenz,
        }).then(function successCallback(response) {

        });
    };

    this.addLizenzToHardware = function(lizenz, hardware, hardwareLizenzen, lizenzenFrei){
        var lizenzData = JSON.parse(lizenz);
        if (lizenzData.lid != undefined){
            $http({
                method: 'POST',
                params: {
                    'lid' : lizenzData.lid,
                    'hid' : hardware.hid,
                },
                url: 'hardware/lizenz'
            }).then(function successCallback(response) {

                for( var i = 0; i < lizenzenFrei.length; i++){
                    if ( lizenzenFrei[i]["lid"] === lizenzData.lid) {
                        lizenzenFrei.splice(i, 1);
                    }
                }

                hardwareLizenzen.push(lizenzData);
                hardware.push(lizenzData);
                lizenz = "";
            });
        }
    };

    this.loadLizenz = function($scope, $state){
        $scope.lizenzList = [];
        if ($state == null){
            $state = "-1";
        }

        $http({
            method: 'GET',
            scope: $scope,
            params : {
                "state" : $state
            },
            url: 'lizenz'
        }).then(function successCallback(response) {
                response.config.scope.lizenzen = [];
                response.data.forEach(function (data) {

                    var reserved = data.reserved == null ? 0 : data.reserved.split(",").length;

                    data.vergeben = reserved + data.hardware.length;
                    data.verfuegbar = data.allowamount == undefined || data.allowamount == 0 ? 1 : data.allowamount;
                    data.free = data.verfuegbar - data.vergeben;

                    if (data.free > 0){
                        data.stateInfo = "frei";
                    } else if (data.free < 0 && reserved > 0){
                        data.stateInfo = "reserviert";
                    } else if (data.free < 0){
                        data.stateInfo = "mehrfach";
                    } else if (data.free == 0 && reserved > 0){
                        data.stateInfo = "reserviert";
                    } else if (data.free == 0){
                        data.stateInfo = "vergeben";
                    }

                    response.config.scope.lizenzList.push(data);
                });

            }
        );
    };

    this.filterLizenz = function (scope, lizenzList, searchValue){
        if (searchValue != '' || scope.lizenzStateFilter != '') {
            var result = [];
            for (var i = 0; i < lizenzList.length; i++) {

                var insert = false;

                for (var key in lizenzList[i]) {
                    if (lizenzList[i][key] != null && lizenzList[i][key].toString().toLowerCase().indexOf(searchValue) != -1) {
                        insert = true;
                        break;
                    }
                }

                if (scope.lizenzStateFilter != '' && scope.lizenzStateFilter != 'All'){
                    if (scope.lizenzStateFilter.toLowerCase() != lizenzList[i].stateInfo.toLowerCase()){
                        insert = false;
                    }
                }

                if (insert){
                    result.push(lizenzList[i]);
                }
            }

            return result;
        }else{
            return lizenzList;
        }

    }


    this.setLizenzViewPages = function(lizenzen, values,rows){
        if (values != undefined){

            if (lizenzen.length >= 11){
                var page = [];
                var pageAmount = rows;
                var pageSize=1;
                for (var i=0;i<lizenzen.length;i++){
                    if (pageAmount > 0){
                        page.push(lizenzen[i]);
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
                values[1] = lizenzen;
            }
        }
    }




});