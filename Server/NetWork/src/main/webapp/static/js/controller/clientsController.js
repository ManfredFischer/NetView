app.controller('hardwareController', function ($scope, $http,$mdDialog, hardwareService, userService,changelogService,messageService) {

    $scope.clientsState = 'ALL';
    $scope.stateListClients = [
        {
            name : 'All',
            value : 'ALL'
        },{
            name : 'Verliehen',
            value : 'Verliehen'
        },{
            name : 'Aktiv',
            value : 'Aktiv'
        },{
            name : 'Prüfen',
            value : 'Pruefen'
        }, {
            name : 'Lager',
            value : 'Lager'
        },{
            name : 'Archiv',
            value : 'Archiv'
        }
    ];

    $scope.showAction = function(value, hw){
        if (value.name == "Löschen"){
            return hw.status.toLowerCase() == 'archiv';
        } else if (value.name == "Verleihen"){
            return !hw.verliehen;
        } else if (value.name == "Rückgabe"){
            return hw.verliehen && hw.status.toLowerCase() != 'archiv';
        } else if (value.name == "Manager"){
            return true;
        } else{
            return true;
        }
    };
    $scope.openShortActions = function(shortAction, hw){
        $scope.dialogAction.openHardware = false;
        if (shortAction.name == "Changelog"){
            $scope.hardwareDetails.hardware = hw;
            $scope.dialogChangelog();
        } else if (shortAction.name == "Lizenz"){
            $scope.hardwareDetails.hardware = hw;
            $scope.dialogLizenz();
        } else if (shortAction.name == "Löschen"){
            $scope.delHardware(hw);
        } else if (shortAction.name == "Verleihen"){
            $scope.rentHardwareView(hw)
        } else if (shortAction.name == "Rückgabe"){
            $scope.getBackHardware(hw.hid,hw.verliehenAn);
        } else if (shortAction.name == "Verantwortlicher"){
            $scope.hardwareDetails.hardware = hw;
            $scope.dialogResponsible();
        }
    };

    $scope.telefonNumberList = [{
        name : 'Owner',
        tel : 0
    },{
        name : 'Owner Mobile',
        tel : 0
    },{
        name : 'Aktiv User',
        tel : 0
    },{
        name : 'Aktiv User Mobile',
        tel : 0
    },{
        name : 'Verliehenden',
        tel : 0
    },{
        name : 'Verliehenden Mobile',
        tel : 0
    }];

    this.shortTelefonList = function (hw) {

        for (var index=0;index<$scope.telefonNumberList.length;index++) {
            if ($scope.telefonNumberList[index].name == 'Aktiv User') {
                if (hw.aktivUserPhone) {
                    $scope.telefonNumberList[index].tel = hw.aktivUserPhone;
                } else {
                    $scope.telefonNumberList[index].tel = 0;
                }
            } else if ($scope.telefonNumberList[index].name == 'Aktiv User Mobile') {
                if (hw.aktivUserMobile) {
                    $scope.telefonNumberList[index].tel = hw.aktivUserMobile;
                } else {
                    $scope.telefonNumberList[index].tel = 0;
                }
            } else if ($scope.telefonNumberList[index].name == 'Owner') {
                if (hw.ownerPhone) {
                    $scope.telefonNumberList[index].tel = hw.ownerPhone;
                } else {
                    $scope.telefonNumberList[index].tel = 0;
                }
            } else if ($scope.telefonNumberList[index].name == 'Owner Mobile') {
                if (hw.ownerMobile) {
                    $scope.telefonNumberList[index].tel = hw.ownerMobile;
                } else {
                    $scope.telefonNumberList[index].tel = 0;
                }
            } else if ($scope.telefonNumberList[index].name == 'Verliehenden') {
                if (hw.verliehenTel) {
                    $scope.telefonNumberList[index].tel = hw.verliehenTel;
                } else {
                    $scope.telefonNumberList[index].tel = 0;
                }
            } else if ($scope.telefonNumberList[index].name == 'Verliehenden Mobile') {
                if (hw.verliehenMobile) {
                    $scope.telefonNumberList[index].tel = hw.verliehenMobile;
                } else {
                    $scope.telefonNumberList[index].tel = 0;
                }
            }
        }

        return $scope.telefonNumberList;
    };

    $scope.shortActionList = [{
        name : 'Verleihen'
    },{
        name : 'Rückgabe'
    },{
        name : 'Changelog'
    },{
        name : 'Lizenz'
    },{
        name : 'Verantwortlicher'
    },{
        name : 'Löschen'
    }];

    $scope.hardwareRowsList = [
        {value :13},
        {value :50},
        {value :100},
        {value :200},
        {value :'All'}
    ];

    $scope.clientPage = 1;
    $scope.clientsRows = 15;
    $scope.clientMaxPage = 0;
    $scope.clientsAmount = 0;
    $scope.searchClientText = "";
    $scope.hardwareClientsPages = [];

    $scope.prevPage = function(){
        if (1 < $scope.clientPage && $scope.clientPage != 1) {
            $scope.clientPage--;
        }
    };

    $scope.nextPage = function(){
        if ($scope.clientMaxPage > $scope.clientPage) {
            $scope.clientPage++;
        }
    };

    this.getHostList = function(){
        $scope.hardwareClientsPages = [];
        var filterHardware = hardwareService.filterHardware($scope,$scope.hardwareList, $scope.clientsState);
        filterHardware = hardwareService.searchHardwareByTextInput(filterHardware,$scope.searchClientText.toLowerCase());

        if ($scope.clientsRows == 'All') {
            $scope.aktivClientsShow = Object.keys(filterHardware).length;
            return filterHardware;
        }

        hardwareService.setHardwareViewPages(filterHardware, $scope.hardwareClientsPages, $scope.clientsRows);
        $scope.clientMaxPage = Object.keys($scope.hardwareClientsPages).length;
        $scope.clientsAmount = Object.keys($scope.hardwareClientsPages[$scope.clientPage]).length;
        return $scope.hardwareClientsPages[$scope.clientPage];
    };

    $scope.showHardwareInformation = function (hid, editable) {
        hardwareService.showHardware(hid,editable,$scope);
    };

    $scope.delHardware = function (hardware, ev) {

        $scope.dialogMessageReset();

        $scope.configMessageDialog.titleMessageDialog = "Hardware löschen? ("+hardware.hostname+")";
        $scope.configMessageDialog.message = "Sind Sie sicher das Sie die Hardware Löschen möchten?"
        $scope.configMessageDialog.dialogYesNoMessage.show = true;
        $scope.configMessageDialog.dialogYesNoMessage.yesAction = $scope.deleteHardware
        $scope.configMessageDialog.dialogYesNoMessage.noAction = function(){
            $mdDialog.hide();
        }
        $scope.configMessageDialog.value = hardware;


        $mdDialog.show({
            scope: $scope.$new(),
            templateUrl: 'static/dialog/ask/MessageDialog.html',
            clickOutsideToClose: true
        });
    };

    $scope.deleteHardware = function() {
        $http({
            method: 'DELETE',
            params: {
                'hid' : $scope.configMessageDialog.value.hid,
            },
            scope : $scope,
            url: 'hardware'
        }).then(function successCallback(response) {
            const messageFunction = function(response){
                $scope.removeItemFromArrayDictonariy($scope.hardwareList,$scope.configMessageDialog.value.hid, 'hid');
                $mdDialog.hide()
            }
            messageService.hinweis("Hardware", "Hardware erfolgreich gelöscht!",messageFunction);
        },function failCallback(response) {
            messageService.hinweis("Hardware", "Hardware erfolgreich gelöscht!");
        });


    };

    $scope.rentHardwareView = function(hardware){
        $scope.dialogMessageReset();

        $scope.configMessageDialog.dialogUserMessage.setDate = true;
        $scope.configMessageDialog.dialogUserMessage.show = true;
        $scope.configMessageDialog.dialogUserMessage.action = $scope.rentHardware;
        $scope.configMessageDialog.value = {
            hid : hardware.hid
        };
        $scope.configMessageDialog.titleMessageDialog = "Mitarbeiter - "+hardware.hostname;

        $mdDialog.show({
            scope: $scope.$new(),
            templateUrl: 'static/dialog/ask/MessageDialog.html',
            clickOutsideToClose: true
        });
    };

    $scope.rentHardware = function(rentDate) {
        var userinfo = userService.findUserDetailsByDisplayname($scope,$scope.searchMessageUserValue);

        if (userinfo['uid'] != undefined && rentDate != "" && rentDate != undefined){
            $scope.configMessageDialog.value.uid = userinfo.uid;
            $scope.configMessageDialog.value.date = new Date(rentDate).getTime();
            $http({
                method: 'PUT',
                data : $scope.configMessageDialog.value,
                scope : $scope,
                url: 'ldap/user/rent'
            }).then(function successCallback(response) {
                $scope.changeValueFromArrayDictonariy($scope.hardwareList,$scope.configMessageDialog.value.hid, 'hid','verliehen', true);
                $scope.changeValueFromArrayDictonariy($scope.hardwareList,$scope.configMessageDialog.value.hid, 'hid','verliehenAn', { username : $scope.configMessageDialog.value.uid});
                $scope.changeValueFromArrayDictonariy($scope.hardwareList,$scope.configMessageDialog.value.hid, 'hid','verliehenBis', $scope.formatDate(new Date($scope.configMessageDialog.value.date)));
            });

            $mdDialog.hide();
        }
    };

    $scope.getBackHardware = function(hardwareRentHID, username){
        $scope.messageValue = {
            hid: hardwareRentHID,
            username : username
        };
        $http({
            method: 'PUT',
            data : $scope.messageValue,
            scope : $scope,
            url: 'ldap/user/rent/back'
        }).then(function successCallback(response) {
            $scope.changeValueFromArrayDictonariy($scope.hardwareList,$scope.messageValue.hid, 'hid','verliehen', false);
        });
    };

    $scope.changeValueFromArrayDictonariy = function(hardwareList, id, searchName, name, value){
        for( var i = 0; i < hardwareList.length; i++){
            if ( hardwareList[i][searchName] === id) {
                hardwareList[i][name] = value;
            }
        }
    }

    $scope.removeItemFromArrayDictonariy = function(values, remove, searchName){
        for( var i = 0; i < values.length; i++){
            if ( values[i][searchName] === remove) {
                values.splice(i, 1);
            }
        }
    }

    $scope.archivHardwareDialog = function(hw) {
        $http({
            method: 'PUT',
            params: {
                'hid' : $scope.configMessageDialog.dialogYesNoMessage.value.hardware.hid,
                'status' : $scope.configMessageDialog.dialogYesNoMessage.value.status
            },
            scope : $scope,
            url: 'hardware/status'
        }).then(function successCallback(response) {
            $scope.configMessageDialog.dialogYesNoMessage.value.hardware.status = response.config.params.status;
        });

        $mdDialog.hide();
    };

    $scope.setStatusHardware = function (hardware, status) {
        $scope.dialogMessageReset();

        $scope.configMessageDialog.titleMessageDialog = "Status ändern? ("+hardware.hostname+")";
        $scope.configMessageDialog.message = "Status: "+status.toString();
        $scope.configMessageDialog.dialogYesNoMessage.show = true;
        $scope.configMessageDialog.dialogYesNoMessage.yesAction = $scope.archivHardwareDialog
        $scope.configMessageDialog.dialogYesNoMessage.noAction = function(){
          $mdDialog.hide();
        }
        $scope.configMessageDialog.dialogYesNoMessage.value = {
            hardware : hardware,
            status : status
        };

        $mdDialog.show({
            scope: $scope.$new(),
            templateUrl: 'static/dialog/ask/MessageDialog.html',
            clickOutsideToClose: true
        })
    };

    $scope.changeResponsible = function(){
        $scope.messageValue = {
            uid : userService.findUserDetailsByDisplayname($scope, $scope.searchMessageUserValue).uid,
            hid : $scope.hardwareDetails.hardware.hid
        };

        $http({
            method: 'PUT',
            data : $scope.messageValue,
            scope : $scope,
            url: 'hardware/owner'
        }).then(function successCallback(response) {

            const messageFunction = function(response){
                if ($scope.dialogAction.openHardware) {
                    hardwareService.showHardware($scope.hardwareDetails.hardware.hid, false, $scope);
                }else{
                    $mdDialog.hide();
                }
            }

            messageService.hinweis("Verantwortlicher", "Verantwortlicher wurde erfolgreich geändert.",messageFunction, response);

        });
    };

    $scope.dialogResponsible = function(){
        $scope.dialogMessageReset();
        $scope.configMessageDialog.dialogUserMessage.action = $scope.changeResponsible;
        $scope.configMessageDialog.dialogUserMessage.show = true;
        $scope.configMessageDialog.titleMessageDialog = "Verwantworlicher - "+$scope.hardwareDetails.hardware.hostname;

        $mdDialog.show({
            scope: $scope.$new(),
            templateUrl: 'static/dialog/ask/MessageDialog.html',
            clickOutsideToClose: true
        });
    };

    $scope.dialogChangelog = function(item){
        changelogService.openChangelog(item,$scope,hardwareService);
    };

    $scope.openChangelog = function(item){
        $scope.dialogChangelog(item)
    };

    $scope.dialogLizenz = function(){
        $scope.dialogMessageReset();
        $scope.configMessageDialog.dialogLizenzMessage.show = true;
        $scope.configMessageDialog.titleMessageDialog = "Lizenz - "+$scope.hardwareDetails.hardware.hostname;

        $mdDialog.show({
            scope: $scope.$new(),
            templateUrl: 'static/dialog/ask/MessageDialog.html',
            clickOutsideToClose: true
        });

        $scope.activWindow = $mdDialog;
    };

    $scope.openShortTelefonActions = function (telefon, hw) {
        switch (telefon.name.toString().toUpperCase()) {
            case "OWNER" :
                $scope.openTelefon(hw.ownerPhone);
                break;
            case "AKTIV USER" :
                $scope.openTelefon(hw.aktivUserPhone);
                break;
        }
    }


});