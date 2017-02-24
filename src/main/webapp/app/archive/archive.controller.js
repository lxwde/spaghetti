/**
 * Created by Administrator on 2017/2/16.
 */
(function(){
    "use strict";

    angular
        .module("myApp.archive")
        .controller("ArchiveController", archiveController);

    archiveController.$inject = ["archiveService"];

    function archiveController($scope, $interval, archiveService) {
        // $scope.data = archiveService.getTableDependencies();

    };

}());