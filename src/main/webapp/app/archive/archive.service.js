/**
 * Created by Administrator on 2017/2/17.
 */
(function(){
    "use strict";

    angular
        .module("myApp.archive")
        .factory("archiveService", archiveService);

    function archiveService($http) {
        var service = {
          getTableDependencies: getTableDependencyGraph
        };
        return service;

        function getTableDependencyGraph() {
            return $http.get("/api/archive-processing/graph")
                .then(getTableDependenciesComplete)
                .catch(function (message){
                    // exception.catcher("XHR Failed for getTableDependencyGraph")(message);
                    // location.url("/");
                }) ;

            function getTableDependencyGraphComplete(data, status, headers, config) {
                return data;
            }
        }
    }

})();