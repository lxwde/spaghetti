/**
 * Created by Administrator on 2017/2/24.
 */

(function(){
    "use strict";

    angular
        .module("myApp.misc")
        .controller("ClockController", function($scope){
            var updateClock = function() {
              $scope.clock = new Date();
            };

            setInterval(function () {
                $scope.$apply(updateClock);
            }, 1000);

            updateClock();
        });
}());