app.service('changelogService', function($http, $mdDialog, messageService) {

    this.loadChangelog = function ($scope) {
        $http({
            method: 'GET',
            scope: $scope,
            url: 'changelog'
        }).then(
            function successCallback(response) {
                response.config.scope.changelogList =  [];
                response.data.forEach(function (data) {
                    var dateInfo = new Date(data.date);
                    data.date = dateInfo.getDay() +"."+dateInfo.getMonth()+"."+dateInfo.getFullYear()+" "+dateInfo.getHours()+":"+dateInfo.getSeconds();
                    response.config.scope.changelogList.push(data);
                });
            });
    }

    this.filterChangelog = function (changelogList, searchValue){
        if (searchValue != '' && changelogList != undefined) {
            var result = [];
            for (var i = 0; i < changelogList.length; i++) {
                for (var key in changelogList[i]) {
                    if (changelogList[i][key] != null && changelogList[i][key].toString().toLowerCase().indexOf(searchValue) != -1) {
                        result.push(changelogList[i]);
                        break;
                    }
                }
            }

            return result;
        }else{
            return changelogList;
        }

    }

    this.setChangelogViewPages = function(changelog, values,rows){
        if (values != undefined){
            if (changelog.length >= 11){
                var page = [];
                var pageAmount = rows;
                var pageSize=1;
                for (var i=0;i<changelog.length;i++){
                    if (pageAmount > 0){
                        page.push(changelog[i]);
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
                values[1] = changelog;
            }
        }
    }

    this.addChangelog = function(changelogData){
        changelogData.data.date = new Date(changelogData.data.date).getTime();
        $http({
            method: 'POST',
            url: 'changelog',
            changelogData : changelogData,
            data : changelogData.data,
        }).then(function successCallback(response) {
            const messageFunction = function(response){
                if (response.config.changelogData.scope.dialogAction.openHardware) {
                    response.config.changelogData.hardwareService.showHardware(response.config.changelogData.data.hid, false, response.config.changelogData.scope);
                    response.config.changelogData.scope.dialogAction.openHardware = false;
                }
            }

            messageService.hinweis("Changelog", "Changelog wurde erfolgreich hinzugefügt.",messageFunction, response);
        });
    }

    this.openChangelog = function(data,scope,hardwareService){
        scope.dialogMessageReset();

        if (data){
            scope.changelogInformation = {
                data : data
            };
        }else {
            scope.openHardware = true;
            scope.changelogInformation = {
                data: {
                    title: "",
                    date: new Date(),
                    hid: scope.hardwareDetails.hardware.hid,
                    username: 'Manfred Fischer',
                    changelog: '',
                    impact: '',
                    system: scope.hardwareDetails.hardware.hostname
                },
                addChangelog: this.addChangelog,
                hardwareService : hardwareService,
                scope : scope,
                mdDialog : $mdDialog
            };
        }

        scope.configMessageDialog.dialogChangelog = true;
        scope.configMessageDialog.titleMessageDialog = "Changelog - "+scope.changelogInformation.data.system;

        $mdDialog.show({
            scope: scope.$new(),
            templateUrl: 'static/dialog/ask/MessageDialog.html',
            clickOutsideToClose: true
        });
    }

});