(function(angular){
    angular.module("tplatArchiver.controllers", []);
    angular.module("tplatArchiver.services", []);
    angular.module("tplatArchiver", ["ngResource", "tplatArchiver.controllers", "tplatArchiver.services"]);
}(angular));