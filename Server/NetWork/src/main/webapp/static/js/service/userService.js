app.service('userService', function($http,$timeout,viewService) {
    this.userList = [];
    this.searchUser = "";

    this.showUserInformation = function (uid, $scope, hardwareService) {

        $scope.resetHardwareView();

        var user = "";
        var username = uid.split(".");
        for (var index=0;index<username.length;index++){
            if (index == (username.length-1)){
                user = user + username[index]
            }else{
                user = user + username[index]+"_"
            }
        }
        $http({
            method: 'GET',
            scope: $scope,
            sync: true,
            url: 'ldap/user/'+user
        }).then(function successCallback(response) {

            $scope.showConfig.details.hardware = true;
            $scope.userDetails.view = true;
            $scope.userDetails.user = response.data.userData;

            var aktivHardware = response.data.aktivHardware;
            var ownerHardware = response.data.ownerHardware;

            var same = false;
            if (ownerHardware.length === aktivHardware.length){
                same = true;
                for (var index=0;index<ownerHardware.length;index++){
                    for (var index=0;index<aktivHardware.length;index++){
                        if (aktivHardware[index].hid !== ownerHardware[index].hid){
                           same = false;
                           break;
                        }
                    }
                    if (!same){
                        break;
                    }
                }
            }

            if (aktivHardware.length > 0 || ownerHardware.length > 0) {

                if (ownerHardware.length > 0) {
                    $scope.hardwareDetails.view = true;
                    $scope.hardwareDetails.title = 'Details';
                    $scope.hardwareDetails.scope = $scope;
                    $scope.hardwareDetails.hardwareService = hardwareService;
                }

                if (ownerHardware.length > 1) {
                    $scope.hardwareDetails.more = true;
                    $scope.hardwareDetails.hardware = viewService.getNewDynamicItems(ownerHardware);
                }else if (ownerHardware.length == 1){
                    $scope.hardwareDetails.hardware = ownerHardware[0]
                }

                if (same) {
                    $scope.hardwareDetails.tabTitle = "Hardware (Aktiv/Owner)";
                } else {

                    if (ownerHardware.length > 0) {
                        $scope.hardwareDetails.tabTitle = "Aktive Hardware";
                    }

                    if (aktivHardware.length > 0) {
                        $scope.hardwareActivDetails.view = true;
                        $scope.hardwareActivDetails.tabTitle = "Owner Hardware";
                        $scope.hardwareActivDetails.title = 'Details';
                        $scope.hardwareActivDetails.scope = $scope;
                        $scope.hardwareActivDetails.hardwareService = hardwareService;

                        if (aktivHardware.length > 1) {
                            $scope.hardwareActivDetails.more = true;
                            $scope.hardwareActivDetails.hardware = viewService.getNewDynamicItems(aktivHardware);
                        }else if (aktivHardware.length == 1){
                            $scope.hardwareActivDetails.hardware = aktivHardware[0]
                        }

                    }
                }
            }
            $scope.showAbstractInformation($scope.userDetails.user.displayName,$scope.userDetails.user.title,"user-white.png");

        });
    };

    this.findUserDetailsByDisplayname = function(scope,username){
        this.searchUser = username;
        return scope.ldapUser.find((data) => {
            return data.displayName.toLowerCase() == this.searchUser.toLowerCase();
        });
    };

    this.getUsers = function(){
        return this.userList;
    };

    this.saveUserDetails = function(hardwareUserDetails){
        $http.post("ldap/user", hardwareUserDetails, {
            'Content-Type': 'application/json'
        });
    };

    this.addUser = function(userData){
        $http({
            method: 'POST',
            scope: this.scopeMain,
            url: 'systemuser',
            data : userData,
        }).then(function successCallback(response) {
            debugger;
            this.userList.push(response.data);
        });
    };

    this.addWizardUser = function(){
        $http({
            method: 'POST',
            scope: $scope,
            url: 'ldap/user/ad',
            data : $scope.addUserWizardData,
        }).then(function successCallback(response) {
            $scope.lizenz = {
                name : '',
                key : '',
                categorie : ''
            }
        });
    };




    /*
    this.startWizardAddUser = function (){
        resetView();
        $scope.showMainConfig.showAddUserWizard = true;
        $scope.showMainConfig.showAddUserWizardInformation = true;
    };



    tis.nextWizardAddUser = function (){
        if ($scope.showMainConfig.showAddUserWizardInformation){
            $scope.resetView();
            $scope.showMainConfig.showAddUserWizard = true;
            $scope.showMainConfig.showAddUserWizardPermission = true;
            $scope.showUserAddWizardConfig.showAddUserWizardBack = true;
        } else if ($scope.showMainConfig.showAddUserWizardPermission){
            $scope.resetView();
            $scope.showMainConfig.showAddUserWizard = true;
            $scope.showMainConfig.showAddUserWizardOverview = true;
            $scope.showUserAddWizardConfig.showAddUserWizardBack = true;
            $scope.showUserAddWizardConfig.showAddUserWizardAddUser = true;
            $scope.showUserAddWizardConfig.showAddUserWizardNext = false;
        } else if ($scope.showMainConfig.showAddUserWizardHardware){
            $scope.resetView();
            $scope.showMainConfig.showAddUserWizard = true;
            $scope.showMainConfig.showAddUserWizardOverview = true;
            $scope.showUserAddWizardConfig.showAddUserWizardBack = true;
            $scope.showUserAddWizardConfig.showAddUserWizardAddUser = true;
            $scope.showUserAddWizardConfig.showAddUserWizardNext = false;
        }
    }

    $scope.prevWizardAddUser = function (){
        if ($scope.showMainConfig.showAddUserWizardHardware){
            $scope.resetView();
            $scope.showMainConfig.showAddUserWizard = true;
            $scope.showMainConfig.showAddUserWizardPermission = true;
            $scope.showUserAddWizardConfig.showAddUserWizardBack = true;
        } else if ($scope.showMainConfig.showAddUserWizardPermission){
            $scope.resetView();
            $scope.showMainConfig.showAddUserWizard = true;
            $scope.showMainConfig.showAddUserWizardInformation = true;
            $scope.showUserAddWizardConfig.showAddUserWizardNext = true;
        } else if ($scope.showMainConfig.showAddUserWizardOverview){
            $scope.resetView();
            $scope.showMainConfig.showAddUserWizard = true;
            $scope.showMainConfig.showAddUserWizardPermission = true;
            $scope.showUserAddWizardConfig.showAddUserWizardBack = true;
            $scope.showUserAddWizardConfig.showAddUserWizardNext = true;
        }
    }


    $scope.addUserWizardData = {
        duplicateFrom : '',
        user : {
            firstname : '',
            lastname : '',
            phone : '',
            mobile : '',
            department : {},
            location : {}
        },
        permission : {
            reservixIntern : false,
            reservixExtern : false,
            confluence : false,
            jira : false
        }
    };
    */
});