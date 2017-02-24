(function(){
    var ItemFactory = function($resource){
        return $resource('/api/items/:id', {
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
    angular.module("myApp.item").factory("Item", ItemFactory);
}());