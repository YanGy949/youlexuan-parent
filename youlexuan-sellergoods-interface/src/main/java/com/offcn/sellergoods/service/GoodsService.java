package com.offcn.sellergoods.service;

import com.offcn.entity.Goods;
import com.offcn.entity.PageResult;
import com.offcn.pojo.TbGoods;

import java.util.List;
/**
 * 商品服务层接口
 * @author Administrator
 *
 */
public interface GoodsService {

    /**
     * 根据商品id和status查看Item列表
     * @param goodsIds
     * @param status
     * @return
     */
//	public List<TbItem> findItemListByGoodsIdandStatus(Long[] goodsIds,String status);
    /**
	 * 商品审核
	 * @param ids
	 * @param status
	 */
	public void updateAuditStatus(Long[] ids,String status);

	/**
	 * 商品的上下架
	 * @param ids
	 * @param status
	 */
	public void updateMarketable(Long[] ids,String status);

	/**
	 * 添加商品【自定义封装类Goods】
	 * @param goods
	 */
	public void add(Goods goods);

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbGoods goods);
	
	
	/**
	 * 修改
	 */
	public void update(TbGoods goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoods findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize);

    public PageResult findGoods(TbGoods goods, int pageNum, int pageSize);
}
