//商户控制层
app.controller('sellerController1', function ($scope, $controller, sellerService1) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.sendMail = function(){
        sellerService1.sendMail($scope.entity.mail).success(function (resp) {
            if (resp.success){
                alert(resp.message);
            } else{
                alert(resp.message);
            }
        })
    }

    $scope.resetPassword = function(){
        sellerService1.resetPassword($scope.entity).success(function (resp) {
            if(resp.success){
                alert(resp.message);
                window.location.href='shoplogin.html';
            }else{
                alert(resp.message);
            }
        });
    }

    // $scope.passwordMap = {};
    $scope.updatePassword = function () {
        // $scope.passwordMap.oldPassword = oldPassword;
        // $scope.passwordMap.newPassword = newPassword;
        if ($scope.passwordMap.newPassword != $scope.passwordMap.reNewpassword) {
            alert("两次输入的新密码不一致");
            return;
        } else {
            sellerService1.updatePassword($scope.passwordMap).success(function (resp) {
                if (resp.success) {
                    $scope.passwordMap = {};
                    // alert(resp.message);
                    window.parent.location.href='../shoplogin.html';
                } else {
                    alert(resp.message);
                }
            });
        }

    }

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        sellerService1.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        sellerService1.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        sellerService1.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    $scope.add = function () {
        sellerService1.add($scope.entity).success(function (resp) {
            if (resp.success) {
                location.href = 'shoplogin.html';
            } else {
                alert(resp.message);
            }
        });
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = sellerService1.update($scope.entity); //修改
        } else {
            serviceObject = sellerService1.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        sellerService1.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        sellerService1.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

});	