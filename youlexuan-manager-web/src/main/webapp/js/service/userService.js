app.service('userService',function ($http) {
    this.delete = function (ids) {
        return $http.get('../user/delete.do?ids='+ids);
    };
    this.findOne = function (id) {
        return $http.get('../user/findOne.do?id=' + id);
    };
    this.add = function (entity) {
        return $http.post('../user/add.do', entity);
    };
    this.update = function (entity) {
        return $http.post('../user/update.do', entity);
    };
    this.search = function (pageNum, pageSize, entity) {
        return $http.post('../user/findAll.do?pageNum=' + pageNum + '&pageSize=' + pageSize, entity);
    };
})