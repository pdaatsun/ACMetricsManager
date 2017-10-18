'use strict';

myapp.controller('LogoutController', function (AuthSharedService) {
    AuthSharedService.logout();
});