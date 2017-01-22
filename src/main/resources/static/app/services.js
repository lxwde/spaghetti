(function(angular){
    var ItemFactory = function($resource){
        return $resource('/items/:id', {
            id:'@id'
        }, {
            update: {
                method: "PUT"
            },
            remove: {
                method: "DELETE"
            }
        });
    };

    ItemFactory.$inject = ['$resource'];
    angular.module("spaghetti.services").factory("Item", itemFactory);
}(angular));