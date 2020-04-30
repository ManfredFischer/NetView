app.controller('lizenzController', function ($scope,lizenzService,hardwareService) {
    $scope.useList = [
        {
            name: 'All'
        },{
            name: 'frei'
        },{
            name: 'verwendet'
        },{
            name: 'reserviert'
        },{
            name: 'mehrfach'
        }
    ]
    $scope.searchLizenz = "";
    $scope.lizenzPage = 1;
    $scope.lizenzMaxPage = 0;
    $scope.lizenzRows = 14;
    $scope.lizenzStateFilter = "";

    $scope.openHardware = function(hw){
        $scope.showConfig.details.lizenz = false;
        hardwareService.showHardware(hw.hid,false,$scope);
    }

    $scope.showLizenzInformation = function(lizenz){
        $scope.resetDialog();

        var lizenzInfo = {
            lizenz : lizenz,
            lizenzHostList : lizenz.hardware,
            showLizenzHardwareView : true,
            openHardware : $scope.openHardware
        };
        $scope.showConfig.details.lizenz = true;

        $scope.showAbstractInformation("Lizenz","","Lizenz-white.png",lizenzInfo);

    };


    $scope.delLizenz = function (lizenz, ev) {
        var confirm = $mdDialog.confirm()
            .title('Lizenz entfernen ('+lizenz.name+')')
            .textContent('Sind Sie sicher?')
            .targetEvent(ev)
            .ok('Ja')
            .cancel('Nein');

        $mdDialog.show(confirm).then(function() {
            $http({
                method: 'DELETE',
                params: {
                    'lid' : lizenz.lid
                },
                url: 'lizenz'
            }).then(function successCallback(response) {
                $scope.removeItemFromArrayDictonariy($scope.lizenzen,lizenz.lid, 'lid');
                $scope.hardware.forEach(data => {
                    $scope.removeItemFromArrayDictonariy(data.lizenz,lizenz.lid, 'lid');
                });
            });
        });
    }

    $scope.updateLizenzState = function(selectLizenzAsUsed, state){

        if (!state){
            selectLizenzAsUsed.state = 1;
        }else{
            selectLizenzAsUsed.state = 0;
        }

        $http({
            method: 'PUT',
            url: 'lizenz/state',
            data : selectLizenzAsUsed,
            scope: $scope,
        }).then(function successCallback(response) {
            selectLizenzAsUsed.state = response.config.data.state;
            if (response.config.data.state == 0){
                selectLizenzAsUsed.stateInfo = "frei";
            } else if (response.config.data.state == 1){
                selectLizenzAsUsed.stateInfo = "verwendet"
            } else if (response.config.data.state == 2){
                selectLizenzAsUsed.stateInfo = "mehrfach";
            } else if (response.config.data.state == 3){
                selectLizenzAsUsed.stateInfo = "reserviert";
            }
        });
    }

    this.getLizenz = function(){
        var filterLizenz = lizenzService.filterLizenz($scope,$scope.lizenzList,$scope.searchLizenz.toLowerCase());
        $scope.lizenzPages = [];
        lizenzService.setLizenzViewPages(filterLizenz, $scope.lizenzPages,$scope.lizenzRows);
        $scope.lizenzMaxPage = Object.keys($scope.lizenzPages).length;
        $scope.aktivLizenzShow = Object.keys($scope.lizenzPages[$scope.lizenzPage] != undefined ? $scope.lizenzPages[$scope.lizenzPage] : 1).length;
        return $scope.lizenzPages[$scope.lizenzPage];
    }

});