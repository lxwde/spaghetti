/**
 * Created by Administrator on 2017/3/1.
 */

(function () {
   "use strict";

   angular
       .module("myApp.misc")
       .controller("GitHubController", ["$scope", "gitHubService",
           function ($scope, gitHubService) {
                $scope.$watch("username", function(newUsername){
                    gitHubService.events(newUsername)
                        .success(function(data, status, headers){
                            $scope.events = data.data;
                        })
                });
       }]);
})();