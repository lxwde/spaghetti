/**
 * Created by Administrator on 2017/2/28.
 */
(function(){
    "use strict";

    angular
        .module("myApp.misc")
        .controller("CityController", function ($scope) {
            $scope.cities = [
                {name: "Seattle"},
                {name: "San Francisco"},
                {name: "Chicago"},
                {name: "New York"},
                {name: "Boston"}
            ];
        });
})();