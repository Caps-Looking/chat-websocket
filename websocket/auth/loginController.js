angular.module('app').controller('LoginController', function($scope, $window, authService) {
    
    var login = false;

    $scope.credentials = {
        username: null,
        password: null
    }

    $scope.signin = function(credentials) {
        authService.login(credentials).then(
            function successCallback(response) {
                authService.setToken(response.data.token);
                authService.setUsername(credentials.username);
                $window.location.href = '../chat/chat.html';
            }, function errorCallback(response) {
                console.log(response);
                alert("Authentication not possible");
            });
    };

});