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

            $scope.hardwareDetails.hardware = response.data.aktivHardware;
            $scope.hardwareActivDetails.hardware = response.data.ownerHardware;

            var same = true;
            if ($scope.hardwareDetails.hardware.length == $scope.hardwareActivDetails.hardware.length){
                for (var index=0;index<$scope.hardwareDetails.hardware.length;index++){
                    for (var index=0;index<$scope.hardwareActivDetails.hardware.length;index++){
                        if ($scope.hardwareActivDetails.hardware[index].hid != $scope.hardwareDetails.hardware[index].hid){
                           same = false;
                           break;
                        }
                    }
                    if (!same){
                        break;
                    }
                }
            }

            if (same){
                if ($scope.hardwareDetails.hardware.length > 0) {
                    $scope.hardwareDetails.view = true;
                    $scope.hardwareDetails.tabTitle = "Hardware (Aktiv/Owner)";
                    $scope.hardwareDetails.title = 'Details';
                    $scope.hardwareDetails.hardware = response.data.aktivHardware;
                    $scope.hardwareDetails.scope = $scope;
                    $scope.hardwareDetails.hardwareService = hardwareService;
                    $scope.hardwareDetails.lizenz = [];
                    if (response.data.aktivHardware.length > 0) {
                        $scope.hardwareDetails.selectedHW = response.data.aktivHardware[0]
                        for (var index = 0; index < response.data.aktivHardware.length; index++) {
                            $scope.hardwareDetails.lizenz.push(viewService.getNewDynamicItems(response.data.aktivHardware[index].lizenz));
                        }
                    }
                }
            }else {
                if ($scope.hardwareDetails.hardware.length > 0) {
                    $scope.hardwareDetails.view = true;
                    $scope.hardwareDetails.tabTitle = "Aktive Hardware";
                    $scope.hardwareDetails.title = 'Details';
                    $scope.hardwareDetails.hardware = response.data.aktivHardware;
                    $scope.hardwareDetails.scope = $scope;
                    $scope.hardwareDetails.hardwareService = hardwareService;
                    $scope.hardwareDetails.lizenz = [];
                    if (response.data.aktivHardware.length > 0) {
                        $scope.hardwareDetails.selectedHW = response.data.aktivHardware[0]
                        for (var index = 0; index < response.data.aktivHardware.length; index++) {
                            $scope.hardwareDetails.lizenz.push(viewService.getNewDynamicItems(response.data.aktivHardware[index].lizenz));
                        }
                    }
                }


                if ($scope.hardwareActivDetails.hardware.length > 0) {
                    $scope.hardwareActivDetails.view = true;
                    $scope.hardwareActivDetails.tabTitle = "Owner Hardware";
                    $scope.hardwareActivDetails.title = 'Details';
                    $scope.hardwareActivDetails.scope = $scope;
                    $scope.hardwareActivDetails.hardwareService = hardwareService;
                    $scope.hardwareActivDetails.lizenz = [];
                    if (response.data.aktivHardware.length > 0) {
                        $scope.hardwareActivDetails.selectedHW = response.data.aktivHardware[0]
                        for (var index = 0; index < $scope.hardwareActivDetails.hardware.length; index++) {
                            $scope.hardwareActivDetails.lizenz.push(viewService.getNewDynamicItems($scope.hardwareActivDetails.hardware[index].lizenz));
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