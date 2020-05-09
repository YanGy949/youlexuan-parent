app.controller('typeTemplateController', function ($scope, typeTemplateService, brandService, $controller) {
    //继承
    $controller('baseController', {$scope: $scope});

    $scope.delete = function () {
        typeTemplateService.delete($scope.selectIds).success(function (resp) {
            if (resp.success) {
                $scope.reloadList();
                $scope.selectIds = [];
            }else{
                alert(resp.message);
            }
        });
    }

    $scope.findOne = function (id) {
        typeTemplateService.findOne(id).success(function (resp) {
            $scope.entity = resp;
            $scope.entity.brandIds = JSON.parse($scope.entity.brandIds);
            $scope.entity.specIds = JSON.parse($scope.entity.specIds);
            $scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);
        });
    }

    $scope.save = function () {
        if ($scope.entity.id != null) {
            typeTemplateService.update($scope.entity).success(function (resp) {
                if (resp.success) {
                    $scope.reloadList();
                    $scope.entity = {customAttributeItems: []};
                } else {
                    alert(resp.message);
                }
            });
        } else {
            typeTemplateService.add($scope.entity).success(function (resp) {
                if (resp.success) {
                    $scope.reloadList();
                    $scope.entity = {customAttributeItems: []};
                } else {
                    alert(resp.message);
                }
            });
        }
    }


    $scope.entity = {customAttributeItems: []};

    $scope.addRow = function () {
        $scope.entity.customAttributeItems.push({});
    }

    $scope.deleteRow = function (index) {
        $scope.entity.customAttributeItems.splice(index, 1);
    }

    //多选下拉框
    $scope.brandList = {data: []};

    $scope.specList = {data: []};

    $scope.selectList = function () {
        typeTemplateService.selectList().success(function (resp) {
            $scope.brandList = {data: resp.brandList};
            $scope.specList = {data: resp.specList};
        });
    }


    //将Json转成String
    $scope.jsonToString = function (jsonString, key) {
        var json = JSON.parse(jsonString);//json字符串转成json数组
        var strings = [];
        for (var i = 0; i < json.length; i++) {
            strings.push(json[i][key]);
        }
        return strings.toString();
    }

    $scope.searchEntity = {};
    $scope.search = function (pageNum, pageSize) {
        typeTemplateService.findPage(pageNum, pageSize, $scope.searchEntity).success(function (resp) {
            $scope.paginationConf.totalItems = resp.total;
            $scope.list = resp.rows;
        });
    }
})