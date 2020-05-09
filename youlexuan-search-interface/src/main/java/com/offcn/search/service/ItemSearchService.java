package com.offcn.search.service;

import java.util.Map;

public interface ItemSearchService {

    /**
     * 根据id导入到solr中
     *
     * @param ids
     */
    public void importToSolrByGoodsId(Long[] ids);

    /**
     * 从solr中删除数据
     *
     * @param ids
     */
    public void deleteFromSolrByGoodsId(Long[] ids);

    /**
     * 搜索
     *
     * @param searchMap
     * @return
     */
    public Map<String, Object> search(Map searchMap);

    /**
     * 搜索
     *
     * @param keysMap
     * @return
     */
    public Map<String, Object> searchLike(Map keysMap);
}
