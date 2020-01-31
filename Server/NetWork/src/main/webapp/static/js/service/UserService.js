app.service('userService', function($http) {

    this.userList = [];

    this.getUsers = function(){
        return this.userList;
    };

    this.addUser = function(userData){
        debugger;
        $http({
            method: 'POST',
            scope: this.scopeMain,
            url: 'systemuser',
            data : userData,
        }).then(function successCallback(response) {
            debugger;
            this.userList.push(response.data);
        });
    }

    this.delUser = function (userData) {
        debugger
    }

    this.showUser = function (userData) {
        debugger
    }
});