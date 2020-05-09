 //商品类目控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService1,typeTemplateService){

	$controller('baseController',{$scope:$scope});//继承


    $scope.findAllTypeTemplate = function(){
        typeTemplateService.findAll().success(function (resp) {
            $scope.typeTemplateList = resp;
        });
    }

	$scope.grade = 1;

	$scope.setGrade = function(grade){
		$scope.grade = grade;
	}

	$scope.findItemCatsByParentId = function(parentId){
		$scope.parentId = parentId;
		itemCatService1.findItemCatsByParentId(parentId).success(function (resp) {
			$scope.list = resp;
        });
	}

    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService1.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService1.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService1.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService1.update( $scope.entity ); //修改  
		}else{
			serviceObject=itemCatService1.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
                    // $scope.reloadList();//重新加载
					$scope.findItemCatsByParentId($scope.parentId);
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService1.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					// $scope.reloadList();//刷新列表
                    $scope.findItemCatsByParentId($scope.parentId);
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService1.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	