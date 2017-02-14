(function(angular){
    var AppController = function($scope, Item) {
        Item.query(function(response){
           $scope.items = response?response:[];
        });

        $scope.addItem = function(description) {
            new Item({
                description: description,
                checked: false
            }).$save(function(item){
                $scope.items.push(item);
            });
            $scope.newItem = "";
        };

        $scope.updateItem = function(item) {
          item.$update();
        };

        $scope.deleteItem = function(item) {
            item.$remove(function(){
                $scope.items.splice($scope.items.indexOf(item), 1);
            });
        };
    };

    function SimpleController($scope) {
        $scope.customers = [
            {name: 'Dave Jones', city: 'Phoenix'},
            {name: 'Jamie Riley', city: 'Atlanta'},
            {name: 'Heedy Wahlin', city: 'Chandler'},
            {name: 'Thomas Winter', city: 'Seatle'}
        ];
    }

    AppController.$inject = ['$scope', 'Item'];
    angular.module("myApp.controllers").controller("AppController", AppController);
    angular.module("myApp.controllers").controller("SimpleController", SimpleController);
}(angular));
