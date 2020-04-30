$scope.handyModel = {};
$scope.handyModelList = [];
$scope.resetHandyModel = function(){
    $scope.handyModel = {
        displayName : '',
        model : '',
        color : '',
        store : ''
    }
}
$scope.resetHandyModel();

$scope.addHandyModel = function(){
    $http({
        method: 'POST',
        scope: $scope,
        url: 'handyModel',
        data : $scope.handyModel
    }).then(function successCallback(response) {
        $scope.handyModelList.push(response.data);
        $scope.resetHandyModel();
    });
}

$scope.deleteHandyModel = function(handyModel){
    $http({
        method: 'DELETE',
        scope: $scope,
        url: 'handyModel/'+ handyModel.hmid,
    }).then(function successCallback(response) {
        $scope.removeItemFromArrayDictonariy($scope.handyModelList,handyModel.hmid, 'hmid');
    });
}


this.getHandyList = function(){
    if ($scope.handyModelList != undefined && $scope.handyModelList.length > 0)
        return $scope.handyModelList;
    else
        return [];
}

$scope.mobileUserData = {
    name : '',
    number : '',
    sim : '',
    pin : '',
    puk : '',
    ultraOne : '',
    ultraTwo : '',
    handy : {
        imei : '',
        deliveryDate : '',
        description : '',
        handyModel : {}
    },
    contract : undefined
};
this.getMobile= function(){
    var lowerCaseSearch = $scope.searchMobile.toLowerCase();
    var result = [];
    if (lowerCaseSearch != ''){
        var result = new Array();
        for (var i=0;i<$scope.mobileUserList.length;i++){
            for (var key in $scope.mobileUserList[i]) {
                if ($scope.mobileUserList[i][key] != null && $scope.mobileUserList[i][key].toLowerCase().indexOf(lowerCaseSearch) != -1){
                    result.push($scope.mobileUserList[i]);
                    break;
                }
            }
        }
    }else{
        result = $scope.mobileUserList;
    }

    return  result;

    if ($scope.showAllLizenz){
        $scope.lizenzMaxPage = Object.keys(result).length;
        $scope.lizenzPageAmount = Object.keys(result).length;
        return result;
    }

    $scope.setMobileViewPages(result, $scope.mobilePages,$scope.mobileRows);

    if ($scope.result != undefined && $scope.result != null){
        $scope.mobileMaxPage = Object.keys($scope.result).length;
        $scope.mobilePageAmount = Object.keys($scope.mobilePages[$scope.mobilePage]).length;
    }else{
        $scope.mobileMaxPage = 0;
        $scope.mobilePageAmount =0;
    }

    if ($scope.mobilePages != undefined && $scope.mobilePages.length > 0)
        return $scope.mobilePages[$scope.mobilePage];
    else
        return [];

}

$scope.showMobileInformation = function (mid) {

    $scope.resetHardwareView();

    $http({
        method: 'GET',
        scope: $scope,
        sync: true,
        url: 'mobile/'+mid
    }).then(function successCallback(response) {
        data.loadLizenz($scope,"0");
        $scope.mobileInformation = response.data;


        if ($scope.mobileInformation.user != undefined && $scope.hardwareInformation.ownerInformation.displayName != undefined){
            $scope.showHardwareUserOwner = true;
            $scope.hardwareOwner = $scope.hardwareInformation.ownerInformation;

        }

        if ($scope.mobileInformation.hardwareOwner != undefined){
            $scope.showHardwareUser = true;
            $scope.hardwareUser = $scope.hardwareInformation.inUseInformation;
        }

        if ($scope.mobileInformation.hardwareAktiv != undefined){
            $scope.showHardwareUser = true;
            $scope.hardwareUser = $scope.hardwareInformation.inUseInformation;
        }

        $scope.hardwareLizenzen = $scope.hardwareInformation.lizenz;

        $scope.resetViews();


        $scope.showAbstractInformation("Mobile","Hardware.png");
    });
}
$scope.setMobileViewPages = function(data, values, rows){
    if (values != undefined){
        if (data.length >= 11){
            var page = [];
            var pageAmount = rows;
            var pageSize=1;
            for (var i=0;i<data.length;i++){
                if (pageAmount > 0){
                    page.push(mobile[i]);
                    pageAmount--;
                }else{
                    data[pageSize] = page;
                    pageSize++;
                    pageAmount = rows;
                    i=i-1;
                }
            }

            if (Object.keys(page).length > 0)
                values[pageSize] = page;

        }else{
            values[1] = data;
        }
    }
}

$scope.addMobileUser = function(){
    $scope.mobileUserData.contract = JSON.parse($scope.mobileUserData.contract);
    $scope.mobileUserData.handy.handyModel = JSON.parse($scope.mobileUserData.handy.handyModel);
    $http({
        method: 'POST',
        scope: $scope,
        url: 'mobileUser',
        data : $scope.mobileUserData,
    }).then(function successCallback(response) {
        $scope.lizenz = {
            name : '',
            key : '',
            categorie : ''
        }
    });
}