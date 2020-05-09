app.controller('specificationController', function ($scope, specificationService, $controller) {

    $controller('baseController', {$scope: $scope});

    $scope.delete = function(){
        specificationService.delete( $scope.selectIds).success(function (resp) {
            if(resp.success){
                $scope.reloadList();
                $scope.selectIds=[];
            }else{
                alert(resp.message)
            }
        });
    }

    $scope.findOne = function(id){
        specificationService.findOne(id).success(function (resp) {
            $scope.entity=resp;
        });
    }

    $scope.save = function () {
        if($scope.entity.specification.id!=null){
            specificationService.update($scope.entity).success(function (resp) {
                if (resp.success) {
                    //添加成功，当前页面重新加载
                    $scope.reloadList();
                    $scope.entity = {};
                    //$scope.findPage();
                } else {
                    alert(resp.message)
                }
            })
        }else{
            specificationService.add($scope.entity).success(function (resp) {
                if (resp.success) {
                    //添加成功，当前页面重新加载
                    $scope.reloadList();
                    $scope.entity = {};
                    //$scope.findPage();
                } else {
                    alert(resp.message)
                }
            })
        }
    }

    $scope.findAll = function () {
        specificationService.findAll().success(function (resp) {
            $scope.list = resp;
        });
    };
    $scope.pageEntity = {};
    $scope.search = function (pageNum, pageSize) {
        specificationService.findPage(pageNum, pageSize, $scope.pageEntity).success(function (resp) {
            $scope.paginationConf.totalItems = resp.total;
            $scope.list = resp.rows;
        });
    }
    // //重新加载列表 数据
    // $scope.reloadList = function () {
    //     //切换页码
    //     // $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    //     $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    // }

    $scope.entity = {specification: {}, specificationOption: []};

    //定义方法，让数组的长度+1(push),  【新增规格选项】调用该方法
    $scope.addRow = function () {
        $scope.entity.specificationOption.push({});
    }

    //删除行,从数组中指定位置进行删除
    $scope.deleteRow = function (index) {
        $scope.entity.specificationOption.splice(index, 1);
    }

})