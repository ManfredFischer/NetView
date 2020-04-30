app.controller('userController', function ($scope, $http, userService,hardwareService) {

    $scope.userInformation = {};

    $scope.showUserInformation = function(uid){
        userService.showUserInformation(uid,$scope, hardwareService);
    }

    $scope.selectUpdateUser = function (searchUserText) {
        $scope.userInformation = userService.findUserDetailsByDisplayname($scope, searchUserText);

        $http.get("ldap/user/" + $scope.userInformation.uid + "/hostname", {header: {'Content-Type': 'application/json; charset=utf-8'}}).then(function (response) {
            if (response.data.hostname != '' && response.data.hostname != undefined) {
                $scope.teamviewerID = response.data.hostname;
                $scope.showConfig.user.teamviewer = true;
            }
        });

        if ($scope.userInformation != undefined && $scope.userInformation != ""&& $scope.userInformation.uid != "") {
            if ($scope.userInformation.telephoneNumber != "" || $scope.userInformation.mobile != "") {
                $scope.showConfig.user.telefon = true;
            }

            $scope.showConfig.user.details = true;
        }else{
            $scope.showConfig.user.telefon = false;
            $scope.showConfig.user.teamviewer = false;
            $scope.showConfig.user.details = false;
        }
    }
});



// @ts-ignore
