/**
 * Created by Administrator on 2017/2/16.
 */
(function(){
    "use strict";

    angular
        .module("myApp.archive")
        .controller("ArchiveController", ["$scope", "$interval", "archiveService",
            function($scope, $interval, archiveService){
                archiveService
                    .events("dummy")
                    .success(function(data, status, headers){
                        $scope.graphLinks = data;
                    })
                    .error(function (data, status, headers, config) {
                        $scope.status = status;
                    })
            }
        ]);
})();