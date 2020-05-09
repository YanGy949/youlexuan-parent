app.controller('userController', function ($scope, userService) {

    $scope.register = function () {
        if ($scope.entity.password != $scope.confirm_pwd) {
            alert('两次输入的密码不一致');
            return;
        }
        if ($scope.msgCode.length = 0) {
            alert('请输入验证码');
            return;
        }
        userService.register($scope.entity, $scope.msgCode).success(function (resp) {
            if (resp.code) {
                $scope.entity = {};
                $scope.confirm_pwd = '';
                $scope.msgCode = '';

                window.location.href = 'login.html';
            } else {
                alert(resp.message);
            }
        })
    }

    $scope.sendMsgCode = function () {
        if ($scope.entity.phone.length != 11) {
            alert("请输入有效的手机号");
            return;
        }
        userService.sendMsgCode($scope.entity.phone).success(function (resp) {
            // console.log(resp)
            alert(resp.message);
        })
    }
})