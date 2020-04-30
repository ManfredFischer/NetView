app.service('messageService', function($http,$mdDialog) {

    this.hinweis = function (title, content, afterFunction, data) {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.querySelector('#popupContainer')))
                .clickOutsideToClose(true)
                .title(title)
                .textContent(content).ok('OK'))
            .then(function(result) {
                if (afterFunction){
                    if (data){
                        afterFunction(data);
                    }else{
                        afterFunction();
                    }
                }
            });
    }


});