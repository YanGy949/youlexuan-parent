app.controller('brandController', function ($scope, $controller,$http,brandService) {

     $controller('baseController',{$scope:$scope});//继承

    $scope.searchEntity = {};//定义搜索对象
    //条件查询
    $scope.search = function (page, rows) {
        // $http.post('../brand/search.do?page=' + page + '&rows=' + rows, $scope.searchEntity).success(function (resp) {
        brandService.search(page,rows,$scope.searchEntity).success(function (resp) {
            $scope.paginationConf.totalItems = resp.total;
            $scope.list = resp.rows;
        });
    }


    // $scope.selectIds = [];//选中的id集合
    // //更新复选，每个复选框都绑定这个函数  $event.target代表点击的控件对象 选中就将id加入数组中 取消选择则将id移出数组
    // $scope.deleteSelection = function ($event, id) {
    //     if ($event.target.checked) {//如果是被选中checked=true,则将id增加到数组
    //         $scope.selectIds.push(id)
    //     } else {
    //         $scope.selectIds.splice($scope.selectIds.indexOf(id), 1);//参数1为id在数组中的位置 参数2为要删除数据的长度
    //     }
    // }

    //批量删除
    $scope.delete = function () {

        if($scope.selectIds==null || $scope.selectIds.length==0){
            alert('请先选择要删除的品牌');
            return;
        }

        // $http.get('../brand/delete.do?ids=' + $scope.selectIds).success(function (resp) {
        brandService.delete($scope.selectIds).success(function (resp) {
            if (resp.success) {
                $scope.reloadList();
            } else {
                alert(resp.message);
            }
        });
    }

    //查询实体
    $scope.findOne = function (id) {
        // $http.get('../brand/findOne.do?id=' + id).success(
        brandService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //增加、修改品牌
    $scope.save = function () {
        //当entity为空的时候，不允许发送请求
        if ($scope.entity.name == '' || $scope.entity.name == null) {
            alert('品牌名称不能为空');
            return;
        }
        if ($scope.entity.firstChar == '' || $scope.entity.firstChar == null) {
            alert('品牌首字母不能为空');
            return;
        }
        // var methodName = 'add';
        // if ($scope.entity.id != null) {//如果有ID，执行修改；没有ID，执行添加
        //     methodName = 'update';
        // }
        // $http.post('../brand/' + methodName + '.do', $scope.entity).success(function (resp) {
        //     if (resp.success) {
        //         $scope.entity = {};
        //         $scope.reloadList();//如果成功重新加载数据
        //     } else {
        //         alert(resp.message);//否则alert新建失败
        //     }
        // });
        if ($scope.entity.id != null) {//如果有ID，执行修改；没有ID，执行添加
            brandService.update($scope.entity).success(function (resp) {
                if (resp.success) {
                    $scope.entity = {};
                    $scope.reloadList();//如果成功重新加载数据
                } else {
                    alert(resp.message);//否则alert新建失败
                }
            });
        }else{
            brandService.add($scope.entity).success(function (resp) {
                if (resp.success) {
                    $scope.entity = {};
                    $scope.reloadList();//如果成功重新加载数据
                } else {
                    alert(resp.message);//否则alert新建失败
                }
            });
        }

    }
    //不带条件的分页查询
    $scope.findPage = function (page, rows) {
        brandService.findPage(page,rows).success(function (resp) {
            $scope.list = resp.rows;
            $scope.paginationConf.totalItems = resp.total;//更新总记录数
        });
    }
    // //重新加载列表 数据
    // $scope.reloadList = function () {
    //     //切换页码
    //     // $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    //     $scope.search( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    // }
    // //分页控件配置
    // $scope.paginationConf = {
    //     currentPage: 1,
    //     totalItems: 10,
    //     itemsPerPage: 8,
    //     perPageOptions: [8, 12, 14, 16, 20],
    //     onChange: function () {
    //         $scope.reloadList();//重新加载，分页组件变化后会触发该函数
    //     }
    // };
    //查询所有的品牌
    $scope.findAll = function () {
        brandService.findAll().success(function (resp) {
            $scope.list = resp;
        })
    }

})