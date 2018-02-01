angular.module('app').service('chatService', function($http, authService) {

    this.token = null;

    var backend_base = 'http://localhost:8080/v1/';

    this.getByUsername = function() {
        return $http({
            method: 'GET', 
            url: backend_base + 'users/getByUsername/' + authService.getUsername(),
            headers: {
                'Authorization': authService.getToken()
            }
        });
    };

    this.saveUser = function(user) {
        return $http({
            method: 'POST', 
            url: backend_base + 'users', 
            data: JSON.stringify(user),
            headers: {
                'Authorization': authService.getToken()
            }
        });
    };

    this.verifyChatExistency = function(id) {
        return $http({
            method: 'POST', 
            url: backend_base + 'chat/' + id, 
            headers: {
                'Authorization': authService.getToken()
            }
        });
    };

    this.getLatestMessages = function(id) {
        return $http({
            method: 'GET', 
            url: backend_base + 'chat/getLatestMessages/' + id, 
            headers: {
                'Authorization': authService.getToken()
            }
        });
    };
    
});