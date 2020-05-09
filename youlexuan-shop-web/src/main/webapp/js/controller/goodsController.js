//商品控制层
app.controller('goodsController', function ($scope, $controller, goodsService1, itemCatService, brandService, typeTemplateService, uploadService) {

    $controller('goodsEditController', {$scope: $scope});//继承

    $scope.marketableStates = ['未上架','已上架'];

    $scope.updateMarketable = function(status){
        goodsService1.updateMarketable($scope.selectIds, status).success(function (resp) {
            if(resp.success){
                $scope.reloadList();
                $scope.selectIds=[];
            }else{
                alert(resp.message);
            }
        });
    }

    $scope.$watch('searchEntity.auditStatus',function (newValue,oldValue) {
        if(newValue){
            goodsService1.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.searchEntity).success(
                function (response) {
                    $scope.list = response.rows;
                    $scope.paginationConf.totalItems = response.total;//更新总记录数
                }
            );
        }
    })

    $scope.itemCatList = [];//商品分类列表
    //加载商品分类列表
    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {
                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;//id作为数组下标，name作为值
                }
            }
        );
    }

    $scope.status = ['未审核', '已审核', '审核未通过', '已驳回'];//商品状态

    $scope.entity = {goods: {}, goodsDesc: {itemImages: [], specificationItems: []}};//定义页面实体结构

    $scope.dele_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    }

    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity)
    }

    $scope.uploadImage = function () {
        uploadService.uploadImage().success(function (resp) {
            if (resp.success) {
                $scope.image_entity.url = resp.message;
            } else {
                alert(resp.message);
            }
        });
    }

    $scope.findallBrand = function () {
        brandService.findAll().success(function (resp) {
            $scope.brandList = resp;
        });
    }

    $scope.categoryList1 = [];
    $scope.categoryList2 = [];
    $scope.categoryList3 = [];

    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        if (newValue) {
            itemCatService.findItemCatsByParentId(newValue).success(function (resp) {
                $scope.categoryList2 = resp;
            });
        }
    })

    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        if (newValue) {
            itemCatService.findItemCatsByParentId(newValue).success(function (resp) {
                $scope.categoryList3 = resp;
            });
        }
    })
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        if (newValue) {
            // itemCatService.findItemCatsByParentId(newValue).success(function (resp) {
            //     $scope.categoryList3 = resp;
            // });
            itemCatService.findOne(newValue).success(function (resp) {
                $scope.entity.goods.typeTemplateId = resp.typeId;
                typeTemplateService.findOne(resp.typeId).success(function (resp) {
                    $scope.brandIds = JSON.parse(resp.brandIds);
                    $scope.entity.goodsDesc.customAttributeItems = JSON.parse(resp.customAttributeItems);
                });
                //查询规格列表
                typeTemplateService.findSpecList(resp.typeId).success(
                    function (response) {
                        $scope.specList = response;
                    }
                );
            });

        }
    })

    $scope.finCategoryList1 = function () {
        itemCatService.findItemCatsByParentId('0').success(function (resp) {
            $scope.categoryList1 = resp;
        });
    }

    $scope.add = function () {
        $scope.entity.goodsDesc.introduction = editor.html();
        goodsService1.add($scope.entity).success(function (resp) {
            if (resp.success) {
                alert(resp.message);
                $scope.entity = {};
                editor.html('');
            } else {
                alert(resp.message);
            }
        });
    }

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService1.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService1.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        goodsService1.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService1.update($scope.entity); //修改
        } else {
            serviceObject = goodsService1.add($scope.entity);//增加
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
        goodsService1.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {auditStatus:'0,1,2,3'};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService1.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

});	