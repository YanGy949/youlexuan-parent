package com.offcn.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.offcn.mapper.TbItemMapper;
import com.offcn.pojo.TbItem;
import com.offcn.pojo.TbItemExample;
import com.offcn.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 8000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void importToSolrByGoodsId(Long[] ids) {
        for (Long id : ids) {
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(id);
            List<TbItem> list = itemMapper.selectByExample(example);
            solrTemplate.saveBeans(list);
            solrTemplate.commit();
        }
    }

    @Override
    public void deleteFromSolrByGoodsId(Long[] ids) {
        for (Long id : ids) {
            SimpleQuery simpleQuery = new SimpleQuery();
            Criteria criteria = new Criteria("item_goodsid").is(id);
            simpleQuery.addCriteria(criteria);
            solrTemplate.delete(simpleQuery);
            solrTemplate.commit();
        }
    }

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        Query query = new SimpleQuery();

        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        map.put("rows", page.getContent());

        return map;
    }

    @Override
    public Map<String, Object> searchLike(Map keysMap) {
        Map<String, Object> map = new HashMap<>();
        Query query = new SimpleQuery();
//        String keywords = (String) keysMap.get("keywords");
//        String[] split = keywords.split(",");
//        for(String keys : split){
        Criteria criteria = new Criteria("item_keywords").is(keysMap.get("keywords"));
        query.addCriteria(criteria);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
//        }


        map.put("rows", page.getContent());

        return map;
    }
}
