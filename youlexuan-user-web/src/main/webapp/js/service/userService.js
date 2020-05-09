app.service('userService', function ($http) {


    this.register = function (entity, msgCode) {
        return $http.post('../user/register.do?msgCode=' + msgCode, entity);
    }

    this.sendMsgCode = function (phone) {
        return $http.get('../user/sendMsgCode.do?phone=' + phone);
    }
})