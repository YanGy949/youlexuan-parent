app.controller('searchController', function ($scope, searchService, $location) {

    $scope.keywordsList = [];

    $scope.getKey = function () {
        $scope.keywordsList.push($scope.searchMap.keywords);
        if (Cookies.get('keywords') != null && Cookies.get('keywords') != 'undefined') {
            var key = Cookies.get('keywords').split(',');
            // alert( $scope.keywordsList+'***')
            if ($scope.keywordsList.length > 5) {
                $scope.keywordsList.splice(0, 1);
            }
        }
        Cookies.set('keywords', $scope.keywordsList.toString());
        // alert( key+'@@@@')
    }

    $scope.searchMap = {};
    //接收页面上传过来的keywords参数
    $scope.loadkeywords = function () {
        $scope.searchMap.keywords = $location.search()['keywords'];//该处的search为location的属性，并非我们自定义的方法
        $scope.search();
    }

    $scope.itemsList = [];
    $scope.itemList = [];

    $scope.search = function () {
        searchService.search($scope.searchMap).success(function (resp) {
            $scope.resultMap = resp;
            for (i = 0; i < resp.rows.length; i++) {
                // alert(resp.rows[i].category)
                $scope.itemList.push(resp.rows[i].category)
            }
            // alert($scope.itemList)
            $scope.itemsList = Array.from(new Set($scope.itemList));
            $scope.getKey();
        })
    }

    $scope.searchOfItem = function (item) {
        $scope.searchMap.keywords = item;
        searchService.search($scope.searchMap).success(function (resp) {
            $scope.resultMap = resp;
        })
    }
})