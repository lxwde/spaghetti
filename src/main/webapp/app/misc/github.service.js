/**
 * Created by Administrator on 2017/3/1.
 */
(function () {
   "use strict";

   angular
       .module("myApp.misc")
       .factory("gitHubService", function ($http) {
           var githubUrl = "https://api.github.com";

           var runUserRequest = function (username, path) {
               return $http({
                   method: "JSONP",
                   url: githubUrl + "/users/" + username + "/" +
                       path + "?callback=JSON_CALLBACK"
               });
           }

           return {
               events: function (username) {
                   return runUserRequest(username, "events");
               }
           }
       });
})();