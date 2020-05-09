app.service('searchService', function ($http) {

    this.searchLike = function (keywords) {
        var keys = {'keywords': keywords};
        return $http.post('../itemsearch/searchLike.do', keys);
    }

    this.search = function (searchMap) {
        return $http.post('../itemsearch/search.do', searchMap);
    }
})