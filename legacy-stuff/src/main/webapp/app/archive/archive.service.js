/**
 * Created by Administrator on 2017/2/17.
 */
(function(){
    "use strict";

    angular
        .module("myApp.archive")
        .factory("archiveService", function($http){
            var url = "http://localhost:8080/api/archive-processing/graph";

            var runUserRequest = function (dbName) {
                return $http({
                    method: "GET",
                    url: url
                });
            }

            return {
                events: function (dbName) {
                    return runUserRequest(dbName);
                }
            }
        });
})();