angular.module('app').service('authService', function($http) {

    this.token = null;

    var backend_base = 'http://localhost:8080/v1/';

    this.login = function(credentials) {
        return $http({
            method: 'POST', 
            url: backend_base + 'users/login', 
            data: credentials
        });
    };    
    
    this.setToken = function(token) {
        this.token = token;
        localStorage['token'] = this.token;
    };

    this.getToken = function() {
        return localStorage['token'];
    };

    this.setUsername = function(username) {
        localStorage['username'] = username;
    };

    this.getUsername = function() {
        return localStorage['username'];
    };
    
});