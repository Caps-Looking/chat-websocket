angular.module('app').controller('chatController', function($scope, $window, $timeout, chatService) {

    $scope.user = {
        id: null,
        username: null,
        password: null,
        admin: false
    };

    $scope.credentials = {
        username: null,
        password: null,
        admin: false
    };

    $scope.alert = {
        message: null,
        messageType: null
    };

    $scope.messages = [];
    $scope.messageToBeSend = null;
    $scope.connected = false;
    $scope.channel = null;

    var stompClient = null;    

    $scope.connect = function() {
        chatService.verifyChatExistency($scope.channel);

        var socket = new SockJS('http://localhost:8080/chat');
        stompClient = Stomp.over(socket);

        if ($scope.channel == 'all') {
            alertCard('You can\'t connect to \"all\"', 'danger');
        } else {
            chatService.getLatestMessages($scope.channel).then(
                function successCallback(response) {
                    response.data.forEach(element => {
                        $scope.messages.push(element);
                    });
                }, function errorCallback(response) {
                    console.log(response);
                }
            );
    
            connectAll();

            stompClient.connect({}, function(frame) {
                $scope.$apply($scope.connected = true);
    
                stompClient.subscribe('/topic/messages/' + $scope.channel, function(messageOutput) {
                    pushMessages(messageOutput);
                });
            });
        }
    };

    $scope.disconnect = function() {
        $scope.messages = [];
        chatService.getByUsername().then(
            function successCallback(response) {
                $scope.user = {
                    id: response.data.data.id,
                    username: response.data.data.username,
                    admin: response.data.data.admin
                }
            }, function errorCallback(response) {
                 console.log(response);
            }
        );
        if (stompClient != null) stompClient.disconnect();
        $scope.connected = false;
    };

    $scope.sendMessage = function() {
        var data = {
            "applicationUser": $scope.user.id,
            "text": $scope.messageToBeSend,
            "chat": $scope.channel
        };

        stompClient.send(
            '/app/chat/' + $scope.channel, {}, JSON.stringify(data)
        );        
    };

    $scope.sendMessageToAll = function() {
        var data = {
            "applicationUser": $scope.user.id,
            "text": $scope.messageToBeSend,
            "chat": "all",
            "chatAll": true
        };

        stompClient.send(
            '/app/chat/all', {}, JSON.stringify(data)
        );
    };

    $scope.logout = function() {
        $window.location.href = '../auth/login.html';
    };

    $scope.save = function(user) {
        chatService.saveUser(user).then(
            function successCallback(response) {
                alertCard(response.data.message, response.data.messageType);

                $scope.credentials = {
                    username: null,
                    password: null,
                    admin: false
                };
            }, function errorCallback(response) {
                console.log(response);
            });
    };

    var connectAll = function() {
        var socket = new SockJS('http://localhost:8080/chat');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            $scope.$apply($scope.connected = true);

            stompClient.subscribe('/topic/messages/all', function(messageOutput) {
                pushMessages(messageOutput);
            });
        });
    };

    var alertCard = function(alertMessage, alertMessageType) {
        $scope.alert = {
            message: alertMessage,
            messageType: alertMessageType
        }; 
        
        if ($scope.alert.message != null) {
            $timeout(function() {
                $scope.alert.message = null;
            }, 5000);
        }
    };

    var pushMessages = function(messageOutput) {
        $scope.messages.push(JSON.parse(messageOutput.body));
        $scope.$apply($scope.messages);
        $scope.messageToBeSend = null;
        $scope.$apply($scope.messageToBeSend);
    };

});