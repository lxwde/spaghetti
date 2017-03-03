/**
 * Created by Administrator on 2017/2/28.
 */

(function(){
    angular
        .module("myApp.misc")
        .controller("PeopleController", function($scope){
            $scope.people = [
                {name: "Ari", city: "San Francisco"},
                {name: "Erik", city: "Seattle"}
            ];
        });
})();