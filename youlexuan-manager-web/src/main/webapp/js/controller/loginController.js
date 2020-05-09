app.controller('loginController',function ($scope,loginService) {

    $scope.getName = function () {
        loginService.getName().success(function (resp) {
            $scope.name = resp.name;
            $scope.lastLoginTime = resp.lastLoginTime;
        });
    }
})