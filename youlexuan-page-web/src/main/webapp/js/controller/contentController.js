//内容控制层
app.controller('contentController', function ($scope, $controller, contentService, contentCategoryService) {

    $controller('baseController', {$scope: $scope});//继承


    // $scope.keyList = [];

    $scope.saveKeyWords = function () {
        contentService.saveKeyWords($scope.keywords).success(function (resp) {
            $scope.keywordsMap = resp.keys;
            $scope.keyList = $scope.keywordsMap.split(',');
        })
    }

    $scope.itemCatsList = [{
        'id': '',
        'list': {
            'id': '',
            'list': {'id': '', 'list': {}, 'name': '', 'parentId': '', 'typeId': ''},
            'name': '',
            'parentId': '',
            'typeId': ''
        },
        'name': '',
        'parentId': '',
        'typeId': ''
    }];

    $scope.findItemCats = function () {
        contentService.findItemCats().success(function (resp) {
            $scope.itemCatsList = resp;
            // $scope.itemCatsList2 = resp.list;
        })
    }

    //点击搜索按钮的时候跳转至solr搜索页面
    $scope.jumpToSearch = function () {
        location.href = "http://localhost:9104/search.html#?keywords=" + $scope.keywords;
    }


    $scope.contentList = [];//广告集合【id为下标，name为值】

    $scope.findByCategoryId = function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (resp) {
            $scope.contentList[categoryId] = resp;
        })
    }

    $scope.findCategoryList = function () {
        contentCategoryService.findAll().success(function (resp) {
            $scope.contentCategoryList = resp;
        });
    }

    // $scope.uploadImg = function(){
    // 	uploadService.uploadImage().success(function (resp) {
    // 		if(resp.success){
    // 			$scope.entity.pic = resp.message;
    // 		}else{
    // 			alert(resp.message);
    // 		}
    //    });
    // }

    $scope.statusArr = ['无效', '有效'];

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        contentService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        contentService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        contentService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = contentService.update($scope.entity); //修改
        } else {
            serviceObject = contentService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    // $scope.reloadList();//重新加载
                    $scope.findAll();
                    $scope.entity = {};
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        contentService.dele($scope.selectIds).success(
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
        contentService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

});	