$scope.showImport = function(){
    $scope.resetViews();
    $scope.dialogConfig.dialogUpload = true;
    $scope.dialogConfig.actionUploadShow = true;
    $scope.dialogConfig.actionUploadDialog = $scope.sendImport;
    $scope.showAbstractInformation("Upload","Upload.png");
}

$scope.sendImport = function(importHardwareFiles, importLizenzFiles, importMobileFiles){
    if ($scope.importHardwareFiles != undefined && $scope.importHardwareFiles.length > 0 ){
        Upload.upload({
            url: 'import/hardware',
            file: $scope.importHardwareFiles[0]
        });
    }
    if ($scope.importLizenzFiles != undefined && $scope.importLizenzFiles.length > 0){
        Upload.upload({
            url: 'import/lizenz',
            file: $scope.importLizenzData[0]
        });
    }
    if ($scope.importMobileFiles != undefined && $scope.importMobileFiles.length > 0){
        Upload.upload({
            url: 'import/mobile',
            file: $scope.importMobileData[0]
        });
    }
}