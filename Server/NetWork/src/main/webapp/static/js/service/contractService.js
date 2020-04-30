$scope.resetNewContract = function(){
    $scope.newContract = {
        name : '',
        contract : '',
        model : '',
        color : '',
        store : ''
    }
}
$scope.resetNewContract();
$scope.addNewContract = function(){
    $http({
        method: 'POST',
        scope: $scope,
        url: 'contract',
        data : $scope.newContract
    }).then(function successCallback(response) {
        $scope.contractList.push(response.data);
        $scope.resetNewContract();
    });
}
this.getContractList = function(){
    if ($scope.contractList != undefined && $scope.contractList.length > 0)
        return $scope.contractList;
    else
        return [];
}
$scope.deleteContract = function(contract){
    $http({
        method: 'DELETE',
        scope: $scope,
        url: 'contract/'+ contract.cid,
    }).then(function successCallback(response) {
        $scope.removeItemFromArrayDictonariy($scope.contractList,contract.cid, 'cid');
    });
}
