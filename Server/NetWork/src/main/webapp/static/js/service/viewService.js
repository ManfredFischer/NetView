app.service('viewService', function($http,$timeout) {

    this.prevPage = function (page) {
        if (1 < Object.keys(page).length && page != 1) {
            page--;
            return page;
        } else {
            return page;
        }
    };

    this.nextPage = function (page) {
        if (Object.keys(page).length > page) {
            page++;
            return page;
        } else {
            return page;
        }
    };

    var DynamicItems = function(data) {
        if (data != undefined)
            this.data = data;
        else
            this.data = [];
        this.loadedPages = {};
        this.numItems = 0;
        this.PAGE_SIZE = 50;

        this.fetchNumItems_(this.data.length);
    };

    DynamicItems.prototype.getItemAtIndex = function(index) {
        var pageNumber = Math.floor(index / this.PAGE_SIZE);
        var page = this.loadedPages[pageNumber];
        if (page) {
            return page[index % this.PAGE_SIZE];
        } else if (page !== null) {
            this.fetchPage_(pageNumber);
        }
    };

    DynamicItems.prototype.getLength = function() {
        return this.numItems;
    };

    DynamicItems.prototype.fetchPage_ = function(pageNumber) {
        this.loadedPages[pageNumber] = null;
        $timeout(angular.noop, 300).then(angular.bind(this, function() {
            this.loadedPages[pageNumber] = [];
            var pageOffset = pageNumber * this.PAGE_SIZE;
            for (var i = pageOffset; i < pageOffset + this.PAGE_SIZE; i++) {
                this.loadedPages[pageNumber].push(this.data[i]);
            }
        }));
    };

    DynamicItems.prototype.fetchNumItems_ = function(amount) {
        $timeout(angular.noop, 300).then(angular.bind(this, function() {
            this.numItems = amount;
        }));
    };

    this.getNewDynamicItems = function (data) {
        return new DynamicItems(data);
    }

});