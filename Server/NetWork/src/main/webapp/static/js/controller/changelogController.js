app.controller('changelogController', function ($scope,changelogService, $mdDialog) {

    $scope.changelogPage = 1;
    $scope.searchChanglog = "";
    $scope.changelogRows = 14;

    this.getChangeLogList = function(){
        if ($scope.changelogRows == 'All'){
            return $scope.changelogList;
        }

        var filterChangelog = changelogService.filterChangelog($scope.changelogList,$scope.searchChanglog.toLowerCase());
        $scope.changelogPages = [];
        changelogService.setChangelogViewPages(filterChangelog, $scope.changelogPages,$scope.changelogRows);
        $scope.changelogMaxPage = Object.keys($scope.changelogPages).length;
        $scope.aktivChangelogShow = Object.keys($scope.changelogPages[$scope.changelogPage] != undefined ? $scope.changelogPages[$scope.changelogPage] : 1).length;
        return $scope.changelogPages[$scope.changelogPage];
    };

    $scope.openChangelog = function(item){
        changelogService.openChangelog(item,$scope);
    };

    $scope.prevPage = function(){
        if (1 < $scope.changelogPage && $scope.changelogPage != 1) {
            $scope.changelogPage--;
        }
    };

    $scope.nextPage = function(){
        if ($scope.changelogMaxPage > $scope.changelogPage) {
            $scope.changelogPage++;
        }
    };


});