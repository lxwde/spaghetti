/**
 * Created by Administrator on 2017/2/27.
 */
(function(){
    "use strict";

    angular
        .module("myApp.misc")
        .controller("InterpolateController",
            function($scope, $interpolate){
                $scope.$watch("emailBody", function (body) {
                  if (body) {
                      var template = $interpolate(body);
                      $scope.previewText = template({to: $scope.to});
                  }
                });
            });
})();