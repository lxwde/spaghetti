/**
 * Created by Administrator on 2017/2/21.
 */

(function() {
    'use strict';

    angular
        .module('blocks.exception')
        .factory('exception', exception);

    /* @ngInject */
    function exception(logger) {
        var service = {
            catcher: catcher
        };
        return service;

        function catcher(message) {
            return function(reason) {
                logger.error(message, reason);
            };
        }
    }
})();
