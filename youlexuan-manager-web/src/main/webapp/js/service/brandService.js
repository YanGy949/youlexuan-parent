app.service('brandService',function ($http) {
    this.delete=function (ids) {
        return $http.get('../brand/delete.do?ids=' + ids);
    };
    this.findOne = function (id) {
        return $http.get('../brand/findOne.do?id=' + id);
    };
    this.add = function (entity) {
        return $http.post('../brand/add.do', entity);
    };
    this.update = function (entity) {
        return $http.post('../brand/update.do', entity);
    };
    this.findPage = function (page,rows) {
        return $http.get('../brand/findPage.do?pageNum=' + page + '&pageSize=' + rows);
    };
    this.findAll = function () {
        return $http.get('../brand/findAll.do');
    };
    this.search= function (page,rows,entity) {
        return $http.post('../brand/search.do?page=' + page + '&rows=' + rows,entity);
    }

})