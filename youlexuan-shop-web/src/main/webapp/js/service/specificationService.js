app.service('specificationService', function ($http) {
    this.findAll = function () {
        return $http.get('../specification/findAll.do');
    }

    this.add = function (entity) {
        return $http.post('../specification/add.do', entity);
    }

    this.findPage = function (pageNum, pageSize, entity) {
        return $http.post('../specification/findPage.do?pageNum=' + pageNum + '&pageSize=' + pageSize, entity);
    }

    this.findOne = function (id) {
        return $http.get('../specification/findOne.do?id='+id);
    }

    this.update = function (entity) {
        return $http.post('../specification/update.do',entity);
    }

    this.delete = function (ids) {
        return $http.get('../specification/delete.do?ids='+ids);
    }

})