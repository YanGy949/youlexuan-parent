package com.offcn.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.offcn.mapper.TbGoodsDescMapper;
import com.offcn.mapper.TbGoodsMapper;
import com.offcn.mapper.TbItemCatMapper;
import com.offcn.mapper.TbItemMapper;
import com.offcn.page.service.ItemPageService;
import com.offcn.pojo.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 6000)
public class ItemPageServiceImpl implements ItemPageService {

    @Value("${pagedir}")
    private String pagedir;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public boolean getItemHtml(Long[] goodsIds) {

        for (Long goodsId : goodsIds) {
            try {
                Configuration configuration = freeMarkerConfig.getConfiguration();
                Template template = configuration.getTemplate("item.ftl");
                Map dataModel = new HashMap();
                //商品表数据
                TbGoods tbGoods = goodsMapper.selectByPrimaryKey(goodsId);
                //商品扩展表数据
                TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
                //商品分类表数据
                TbItemExample example = new TbItemExample();
                TbItemExample.Criteria criteria = example.createCriteria();
                criteria.andGoodsIdEqualTo(goodsId);
                List<TbItem> items = itemMapper.selectByExample(example);
                //商品的一、二、三级分类
                String itemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName();
                String itemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName();
                String itemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();
                //将数据存入map
                dataModel.put("goods", tbGoods);
                dataModel.put("goodsDesc", tbGoodsDesc);
                dataModel.put("items", items);
                dataModel.put("itemCat1", itemCat1);
                dataModel.put("itemCat2", itemCat2);
                dataModel.put("itemCat3", itemCat3);

                FileWriter fileWriter = new FileWriter(pagedir + goodsId + ".html");
                template.process(dataModel, fileWriter);
                //关闭输出流
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
