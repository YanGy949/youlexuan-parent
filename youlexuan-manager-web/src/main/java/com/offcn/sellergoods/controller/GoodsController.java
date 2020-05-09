package com.offcn.sellergoods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.entity.Goods;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.page.service.ItemPageService;
import com.offcn.pojo.TbGoods;
import com.offcn.search.service.ItemSearchService;
import com.offcn.sellergoods.service.GoodsService;
import com.offcn.sellergoods.service.ItemService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference(timeout = 40000)
    private ItemPageService itemPageService;

    @Reference
    private GoodsService goodsService;

    @Reference
    private ItemSearchService itemSearchService;

    @Reference
    private ItemService itemService;

    @Autowired
    private AmqpTemplate amqpTemplate;

//    @RequestMapping("/getItemHtml")
//    public void getItemHtml(Long goodsId){
//        itemPageService.getItemHtml(goodsId);
//    }

    @RequestMapping("/updateAuditStatus")
    public Result updateAuditStatus(Long[] ids, String status) {
        try {
            goodsService.updateAuditStatus(ids, status);
            /*同步方法
            //如果审核通过则将sku导入solr
            //如果审核通过则生成静态页面
            if (status.equals("1")) {
                itemSearchService.importToSolrByGoodsId(ids);
                itemPageService.getItemHtml(ids);
            }*/
            Map<String, Object> map = new HashMap<>();
            map.put("ids", ids);
            map.put("status", status);
            amqpTemplate.convertAndSend("pageQueueKey", map);
            amqpTemplate.convertAndSend("searchQueueKey", map);

            return new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }
    }

    @RequestMapping("/updateMarketable")
    public Result updateMarketable(Long[] ids, String status) {
        try {
            goodsService.updateMarketable(ids, status);
            return new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            goods.getGoods().setSellerId(name);
            goodsService.add(goods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbGoods goods) {
        try {
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbGoods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
//            itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
//		String name = SecurityContextHolder.getContext().getAuthentication().getName();
//		goods.setSellerId(name);
        goods.setIsDelete("1");
        return goodsService.findPage(goods, page, rows);
    }

    @RequestMapping("/findGoods")
    public PageResult findGoods(@RequestBody TbGoods goods, int page, int rows) {
        return goodsService.findGoods(goods, page, rows);
    }

}
