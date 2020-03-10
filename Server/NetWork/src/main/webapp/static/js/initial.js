var app = angular.module('MainPage', ['ngMaterial','ngFileUpload']);
app.factory('initServer', function() {
    return {
        aktiv : true
    }
});
app.factory('serverValue', function() {
    return  {
        location : {
            finish : false,
            lastUpdate : 0,
            result : []
        },
        clientsHardware : {
            finish : false,
            lastUpdate : 0,
            result : []
        },
        netzHardware : {
            finish : false,
            lastUpdate : 0,
            result : []
        },
        lizenz : {
            finish : false,
            lastUpdate : 0,
            result : []
        },
        users : {
            finish : false,
            lastUpdate : 0,
            result : []
        },
        changelog : {
            finish : false,
            lastUpdate : 0,
            result : []
        }
    };
});
app.service('initService', function(serverValue,initServer) {
    this.checkFinishLoading = function () {
        var finish = true;
        for (var key in serverValue) {
            if (!serverValue[key].finish){
                finish = false;
                break;
            }
        }

        if (finish){
            initServer.aktiv = false;
        }
    };

    this.finishTransaction = function (transaction, response) {
        serverValue[transaction].result = response.data;
        serverValue[transaction].lastUpdate = Date.now();
        serverValue[transaction].finish = true;
        this.checkFinishLoading();
    };
});
