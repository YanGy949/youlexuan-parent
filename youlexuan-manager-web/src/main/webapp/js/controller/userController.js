app.controller('userController', function ($scope,userService,$http,$controller) {

    $controller('baseController',{$scope:$scope});
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
    $scope.delete = function(){
        // $http.get('../user/delete.do?ids='+$scope.selectIds).success(function (resp) {
        userService.delete($scope.selectIds).success(function (resp) {
            if (resp.success) {
                $scope.reloadList();
            } else {
                alert(resp.message);
            }
        });
    }
    //查询实体
    $scope.findOne = function (id) {
        // $http.get('../user/findOne.do?id=' + id).success(
        userService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //增加、修改品牌
    $scope.save = function () {
        //当entity为空的时候，不允许发送请求
        if ($scope.entity.username == '' || $scope.entity.username == null) {
            alert('用户名不能为空');
            return;
        }if ($scope.entity.sex == '' || $scope.entity.sex == null) {
            alert('性别不能为空');
            return;
        }if ($scope.entity.phone == '' || $scope.entity.phone == null) {
            alert('手机号码不能为空');
            return;
        }if ($scope.entity.sourceType == '' || $scope.entity.sourceType == null) {
            alert('会员来源不能为空');
            return;
        }
        // var methodName = 'add';
        // if ($scope.entity.id != null) {//如果有ID，执行修改；没有ID，执行添加
        //     methodName = 'update';
        // }
        // $http.post('../user/' + methodName + '.do', $scope.entity).success(function (resp) {
        //     if (resp.success) {
        //         $scope.entity = {};
        //         $scope.reloadList();//如果成功重新加载数据
        //     } else {
        //         alert(resp.message);//否则alert新建失败
        //     }
        // });
        if ($scope.entity.id != null) {//如果有ID，执行修改；没有ID，执行添加
            userService.update($scope.entity).success(function (resp) {
                if (resp.success) {
                    $scope.entity = {};
                    $scope.reloadList();//如果成功重新加载数据
                } else {
                    alert(resp.message);//否则alert新建失败
                }
            });
        }else{
            userService.add($scope.entity).success(function (resp) {
                if (resp.success) {
                    $scope.entity = {};
                    $scope.reloadList();//如果成功重新加载数据
                } else {
                    alert(resp.message);//否则alert新建失败
                }
            });
        }

    }

    //带条件的的分页查询
    $scope.search = function (pageNum, pageSize, entity) {
        // $http.post('../user/findAll.do?pageNum=' + pageNum + '&pageSize=' + pageSize, entity).success(function (resp) {
        userService.search(pageNum, pageSize, entity).success(function (resp) {
            $scope.paginationConf.totalItems = resp.total;
            $scope.list = resp.rows;
        });
    }
    //重新加载列表 数据
    $scope.reloadList = function () {
        //切换页码
        // $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.entity);
    }
    //
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
})