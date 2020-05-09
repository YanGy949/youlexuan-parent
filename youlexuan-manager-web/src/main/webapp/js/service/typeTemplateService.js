app.service('typeTemplateService',function ($http) {

    this.findAll = function () {
        return $http.get('../typeTemplate/findAll.do');
    }

    this.findPage = function (pageNum,pageSize,entity) {
        return $http.post('../typeTemplate/findPage.do?pageNum='+pageNum+'&pageSize='+pageSize,entity);
    }

    this.selectList = function () {
        return $http.get('../typeTemplate/selectList.do');
    }

    this.add = function (entity) {
        return $http.post('../typeTemplate/add.do',entity);
    }

    this.findOne = function (id) {
        return $http.get('../typeTemplate/findOne.do?id='+id);
    }

    this.update = function (entity) {
        return $http.post('../typeTemplate/update.do',entity);
    }

    this.delete = function (ids) {
        return $http.get('../typeTemplate/delete.do?ids='+ids);
    }
})