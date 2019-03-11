var app = angular.module('MainPage', ['ngMaterial','ngFileUpload']);
app.service('hexafy', function() {
    this.myFunc = function (x) {
        return x.toString(16);
    }
});