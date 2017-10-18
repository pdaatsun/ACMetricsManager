'use strict';

myapp.controller('UsersController', function ($scope, $log, UsersService) {
    $scope.users = UsersService.getAll();
});