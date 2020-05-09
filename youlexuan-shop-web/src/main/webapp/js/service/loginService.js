app.service('loginService', function ($http) {
    this.getName = function () {
        return $http.get('../login/getName.do');
    }
    //查询实体
    this.findOne = function (id) {
        return $http.get('../seller/findOne.do?sellerId=' + id);
    }

})