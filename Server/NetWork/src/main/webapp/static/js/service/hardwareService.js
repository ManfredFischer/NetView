app.service('hardwareService', function($http, $mdDialog,viewService,userService) {

    this.loadHardware = function(scope, categorie){
        scope.hardwareList = [];
        $http({
            method: 'GET',
            scope: scope,
            params : {
                "categorie" : categorie
            },
            url: 'hardware'
        }).then(function successCallback(response) {
            response.config.scope.hardwareList = [];
            response.data.forEach(function (data) {
                response.config.scope.hardwareList.push(data);
            });
        });
    };

    this.filterHardware = function (scope,hardwareList, status) {
        if (hardwareList != undefined) {
            var location = JSON.parse(scope.selectedLocation).lid;
            var result = []
            for (var i = 0; i < hardwareList.length; i++) {
                if (status.toLowerCase() != 'all') {
                    if (status.toLowerCase() == 'verliehen'){
                        if (!hardwareList[i].verliehen){
                            continue
                        }
                    } else if (hardwareList[i].status == undefined || hardwareList[i].status.toLowerCase() != status.toLowerCase()) {
                        continue;
                    }
                }else {
                    if (hardwareList[i].status == undefined || hardwareList[i].status.toLowerCase() == "archiv" || hardwareList[i].status.toLowerCase() == "lager") {
                        continue;
                    }
                }

                if (location != "Standort..." && location != "" && location != undefined) {
                    if (hardwareList[i].location.toString() != location) {
                        continue;
                    }
                }

                result.push(hardwareList[i]);
            }
        }
        return result;
    }

    this.searchHardwareByTextInput = function(hardwareList, searchValue){
        if (searchValue != '' && hardwareList != undefined) {
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
    };

    this.setHardwareViewPages = function(hardware, values,rows){
        if (values != undefined){

            if (hardware.length >= rows){
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


    this.delHardwareLizenz = function (lizenz, hardwareInformation,ev,scope) {
        var confirm = $mdDialog.confirm()
            .title('Hardware lizenz entfernen ('+lizenz.name+')')
            .textContent('Sind Sie sicher?')
            .targetEvent(ev)
            .ok('Ja')
            .cancel('Nein');

        $mdDialog.show(confirm).then(function() {
            $http({
                method: 'DELETE',
                params: {
                    'lid' : lizenz.lid,
                    'hid' : hardwareInformation.hardware.hid
                },
                hardwareInformation : hardwareInformation,
                url: 'hardware/lizenz'
            }).then(function successCallback(response) {
                response.config.hardwareInformation.hardwareService.showHardware(response.config.params.hid,false,response.config.hardwareInformation.scope);
            });
        });
    };


    this.showHardware = function (hid, editable, scope) {

        scope.resetHardwareView();

        $http({
            method: 'GET',
            this : this,
            sync: true,
            url: 'hardware/'+hid
        }).then(function successCallback(response) {
            scope.activUserDetails.view = false;
            scope.activUserDetails.user = {};

            scope.ownerDetails.view = false;
            scope.ownerDetails.user = {};

            scope.hardwareDetails.view = true;
            scope.hardwareDetails.tabTitle = 'Hardware';
            scope.hardwareDetails.title = 'Details';
            scope.hardwareDetails.hardware = response.data;
            scope.hardwareDetails.scope = scope;
            scope.hardwareDetails.hardwareService = response.config.this;


            scope.hardwareDetails.lizenz = viewService.getNewDynamicItems(scope.hardwareDetails.hardware.lizenz);

            scope.hardwareDetails.hardware.changelogList.forEach(data => {
                data.date = new Date(data.date).toDateString();
            });

            scope.hardwareDetails.changelog = viewService.getNewDynamicItems(scope.hardwareDetails.hardware.changelogList);
            scope.hardwareDetails.software = viewService.getNewDynamicItems(scope.hardwareDetails.hardware.software);
            //scope.hardwareDetails.document.push(viewService.getNewDynamicItems(scope.hardwareDetails.hardware[index].document));



            scope.hardwareDetails.help = scope.help;

            scope.hardwareDetails.hardwareService.resetUserDetails(scope);
            var same = false;
            if (scope.hardwareDetails.hardware != undefined && scope.hardwareDetails.hardware.ownerInformation != undefined && scope.hardwareDetails.hardware.ownerInformation.displayName != undefined){
                if (scope.hardwareDetails.hardware.inUseInformation == undefined ||
                    scope.hardwareDetails.hardware.inUseInformation.displayName != scope.hardwareDetails.hardware.ownerInformation.displayName)
                    scope.hardwareDetails.hardwareService.setUserDetails(scope.hardwareDetails.hardware.ownerInformation,scope.ownerDetails, scope.dialogAction,'Owner' );
                else
                  same = true
            }

            if (scope.hardwareDetails.hardware != undefined && scope.hardwareDetails.hardware.inUseInformation != undefined &&
                scope.hardwareDetails.hardware.inUseInformation.displayName != undefined){
                scope.hardwareDetails.hardwareService.setUserDetails(scope.hardwareDetails.hardware.inUseInformation,scope.activUserDetails, scope.dialogAction,same ? 'User' : 'Aktiv User' );
            }

            if (scope.dialogAction.telefon != undefined && scope.dialogAction.telefon.value.length == 1){
                scope.dialogAction.telefon.show = false;
                scope.dialogAction.telefon.number = scope.dialogAction.telefon.value.value;
            }

            scope.showConfig.details.hardware = true;
            scope.dialogAction.teamviewer.show = true;
            scope.dialogAction.teamviewer.action = scope.openTeamViewer;
            scope.dialogAction.teamviewer.value = response.data.hostname;

            scope.dialogAction.responsible.show = true;
            scope.dialogAction.responsible.action = scope.dialogResponsible;

            scope.dialogAction.lizenz.show = true;
            scope.dialogAction.lizenz.action = scope.dialogLizenz;
            scope.dialogAction.lizenz.value = scope.hardwareDetails;

            scope.dialogAction.changelog.show = true;
            scope.dialogAction.changelog.action = scope.dialogChangelog;
            scope.dialogAction.changelog.value = scope.hardwareDetails;

            scope.dialogAction.openHardware = true;

            scope.showAbstractInformation(scope.hardwareDetails.hardware.hostname,scope.hardwareDetails.hardware.lastLogin,"hardware-weis.png");
        });
    };



    this.resetUserDetails = function (scope) {
        scope.dialogAction.telefon = {
                show : false,
                value : []
            }
    }

    this.setUserDetails = function (ownerInformation,userDetails, dialogAction, title) {
        userDetails.view = true;
        userDetails.tabTitle = title;
        userDetails.saveUserDetails = userService.saveUserDetails;
        userDetails.user = ownerInformation;
        userDetails.user.details.username = ownerInformation.uid;


        if (userDetails.user.telephoneNumber != '') {
            dialogAction.telefon.show = true;
            dialogAction.telefon.value.push({
                name: title,
                value: userDetails.user.telephoneNumber
            });
        }
        if (userDetails.user.mobile != '') {
            dialogAction.telefon.show = true;
            dialogAction.telefon.value.push({
                name: title + ' Mobile',
                value: userDetails.user.mobile
            });
         }
    }
});


/*
this.addHardware = function(){
    $http({
        method: 'POST',
        data: $scope.hardwareInsert,
        url: 'hardware'
    }).then(function successCallback(response) {
        $scope.hardware.push(response.data)
        $scope.resetHardwareInsert();
    });
}
this.updateHardware = function(){
    $http({
        method: 'POST',
        data: $scope.hardwareInformation,
        url: 'hardware'
    }).then(function successCallback(response) {
        $scope.hardware.push(response.data)
        $scope.resetHardwareInsert();
    });
};
*/