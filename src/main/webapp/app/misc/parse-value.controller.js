/**
 * Created by Administrator on 2017/2/24.
 */

(function () {
   "use strict";

   angular
       .module("myApp.misc")
       .controller("ParseValueController", function($scope, $parse){
           $scope.$watch("expr", function (newVal, oldVal, scope) {
               if (newVal !== oldVal) {
                   var parseFun = $parse(newVal);
                   $scope.parsedValue = parseFun(scope);
               }
           })
       });

}());