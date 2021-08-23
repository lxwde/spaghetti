/**
 * Created by Administrator on 2017/3/1.
 */
(function(){
    "use strict";

    angular
        .module("myApp.misc")
        .controller("LotteryController", function($scope){
            $scope.generateNumber = function() {
                return Math.floor((Math.random()*10)+1);
            }
        });
})();