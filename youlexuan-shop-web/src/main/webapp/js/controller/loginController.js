app.controller('loginController', function ($scope, loginService,sellerService1) {


    $scope.getName = function () {
        loginService.getName().success(function (resp) {
            $scope.sellerId = resp.sellerId;
            $scope.lastLoginTime = resp.lastLoginTime;

            //查询实体
            loginService.findOne(resp.sellerId).success(
                function (response) {
                    $scope.entity = response;
                }
            );
        });
    }

    $scope.update = function () {
        sellerService1.update($scope.entity).success(function (resp) {
            if(resp.success){
                $scope.getName();
                alert(resp.message);
            }else{
                alert(resp.message);
            }
        });
    }

})