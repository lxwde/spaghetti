/**
 * Created by Administrator on 2017/2/28.
 */
(function(){
    "use strict";

    angular
        .module("myApp.misc")
        .controller("EquationController", function($scope){
            $scope.equation = {};
            $scope.change = function() {
              $scope.equation.output
                = Number($scope.equation.x) + 2;
            };
        });
})();